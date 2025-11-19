package com.jin.xlog

import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter


/**
 * @author yongjin.guo
 * @date 2025/6/16 9:54
 * @description
 */

internal class SimpleWriter() {

    /**
     * The opened log file.
     */
    private var logFile: File? = null

    /**
     * The buffered writer to write log.
     */
    private var bufferedWriter: BufferedWriter? = null

    fun open(file: File): Boolean {
        logFile = file

        // Create log file if not exists.
        if (logFile?.exists() == false) {
            try {
                val parent = logFile?.parentFile
                if (parent?.exists() == false) {
                    parent.mkdirs()
                }
                logFile?.createNewFile()
            } catch (e: Exception) {
                e.printStackTrace()
                close()
                return false
            }
        }

        // Create buffered writer.
        try {
            bufferedWriter = BufferedWriter(FileWriter(logFile, true))
        } catch (e: Exception) {
            e.printStackTrace()
            close()
            return false
        }
        return true
    }

    fun isOpened(): Boolean {
        return bufferedWriter != null && logFile?.exists() == true
    }

    fun getOpenedFileName(): String? {
        return logFile?.name
    }

    fun appendLog(log: String) {
        try {
            bufferedWriter?.append(log)
            bufferedWriter?.appendLine()
            bufferedWriter?.flush()
        } catch (e: Exception) {
            Platform.get().warn("append log failed: " + e.message)
        }
    }

    fun close(): Boolean {
        try {
            bufferedWriter?.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        bufferedWriter = null
        logFile = null
        return true
    }
}