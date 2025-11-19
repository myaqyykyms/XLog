package com.jin.xlog.printer

import com.jin.xlog.Platform
import com.jin.xlog.SimpleWriter
import com.jin.xlog.bean.LogItem
import com.jin.xlog.ext.dayToMillis
import com.jin.xlog.factory.DefaultFlattener
import com.jin.xlog.inter.Printer
import com.jin.xlog.utils.CommonUtils
import com.jin.xlog.utils.FileUtils
import java.io.File
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.concurrent.BlockingQueue
import java.util.concurrent.LinkedBlockingQueue


/**
 * @author yongjin.guo
 * @date 2025/6/13 16:20
 * @description
 */
internal class FilePrinter(
    private val appName: String,
    private val saveDays: Int,
    private val savePath: String
) : Printer {

    /**
     * Log writer.
     */
    private var writer: SimpleWriter? = null

    @Volatile
    private var worker: Worker? = null

    private val formatter = DateTimeFormatter.ofPattern("yyyyMMdd")

    private var lastFileName: String? = null
    private var isWriterClosed: Boolean = false
    private var newFileName: String = ""

    init {
        val directory = FileUtils.getFileByPath(savePath)
        FileUtils.createOrExistsDir(directory)
        deleteOldFiles(directory)
        writer = SimpleWriter()
        worker = Worker()
    }

    override fun println(
        logItem: LogItem
    ) {
        if (worker?.isStarted() == false) {
            worker?.start()
        }
        worker?.enqueue(logItem)
    }

    /**
     * Return name of file
     */
    private fun getFileName(): String {
        return "${appName}_${LocalDateTime.now().format(formatter)}.log"
    }

    private fun deleteOldFiles(directory: File?) {
        if (directory?.exists() == false) return
        if (directory?.isDirectory == false) return
        directory?.listFiles { shouldClean(it) }?.forEach {
            it.delete()
        }
    }

    /**
     * Whether is the file be cleaned
     */
    private fun shouldClean(file: File): Boolean {
        val currentTimeMillis = System.currentTimeMillis()
        val lastModified = file.lastModified()
        return (currentTimeMillis - lastModified > saveDays.dayToMillis())
    }

    /**
     * Do the real job of writing log to file.
     */
    private fun doPrintln(logItem: LogItem) {
        lastFileName = writer?.getOpenedFileName()
        isWriterClosed = writer?.isOpened() == false
        newFileName = getFileName()
        if (lastFileName == null || isWriterClosed || lastFileName != newFileName) {
            if (newFileName.trim().isEmpty()) {
                Platform.get().error("File name should not be empty, ignore log: " + logItem.msg)
                return
            }
            writer?.close()
            if (writer?.open(File("$savePath/$newFileName")) == false) return
            lastFileName = newFileName
        }
        writer?.appendLog(DefaultFlattener().flatten(logItem))
    }

    /**
     * Export log from file system
     */
    internal fun export(path: String): Boolean {
        return try {
            val formatter = DateTimeFormatter.ofPattern("yyyy_MM_dd_HH_mm_ss")
            val now = LocalDateTime.now()
            val zipPath = "$path/${appName}_${now.format(formatter)}.zip"
            val pathList = mutableListOf<String>()
            File(savePath).walk().maxDepth(1)
                .filter { it.isFile }
                .forEach {
                    pathList.add(it.path)
                }
            CommonUtils.zipFile(pathList, zipPath)
            true
        } catch (e: Exception) {
            false
        }
    }

    /**
     * Work in background, we can enqueue the logs, and the worker will dispatch them.
     */
    private inner class Worker : Runnable {
        private val logs: BlockingQueue<LogItem> = LinkedBlockingQueue()

        @kotlin.concurrent.Volatile
        private var started = false

        /**
         * Enqueue the log.
         *
         * @param log the log to be written to file
         */
        fun enqueue(log: LogItem) {
            try {
                logs.put(log)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        }

        /**
         * Whether the worker is started.
         *
         * @return true if started, false otherwise
         */
        fun isStarted(): Boolean {
            synchronized(this) {
                return started
            }
        }

        /**
         * Start the worker.
         */
        fun start() {
            synchronized(this) {
                if (started) {
                    return
                }
                Thread(this).start()
                started = true
            }
        }

        override fun run() {
            try {
                while (true) {
                    logs.take()?.let {
                        doPrintln(it)
                    }
                }
            } catch (e: InterruptedException) {
                e.printStackTrace()
                synchronized(this) {
                    started = false
                }
            }
        }
    }
}