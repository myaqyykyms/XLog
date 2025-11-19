package com.jin.xlog

import android.annotation.SuppressLint
import android.content.Context
import com.jin.xlog.XLog.json
import com.jin.xlog.XLog.xml
import com.jin.xlog.bean.LogConfiguration
import com.jin.xlog.bean.LogItem
import com.jin.xlog.bean.LogLevel
import com.jin.xlog.factory.DefaultJsonFormatter
import com.jin.xlog.factory.DefaultStackTraceFormatter
import com.jin.xlog.factory.DefaultThrowableFormatter
import com.jin.xlog.factory.DefaultXmlFormatter
import com.jin.xlog.inter.Printer
import com.jin.xlog.printer.AndroidPrinter
import com.jin.xlog.printer.FilePrinter
import com.jin.xlog.utils.CommonUtils
import com.jin.xlog.utils.StackTraceUtil
import java.nio.file.Files
import java.nio.file.Paths


/**
 * @author yongjin.guo
 * @date 2025/6/13 15:41
 * @description
 */
@SuppressLint("StaticFieldLeak")
object XLog {

    /**
     * Log configuration.
     */
    private var sLogConfiguration: LogConfiguration? = null

    /**
     * Global log printer.
     */
    private var sPrinter: Printer? = null

    /**
     * Whether XLog is initialized.
     */
    private var sIsInitialized: Boolean = false

    /**
     * File printer.
     */
    private var sFilePrinter: FilePrinter? = null

    /**
     * Whether is exporting log.
     */
    private var sIsExportLog: Boolean = false

    /**
     * Initialize log system, should be called only once.
     *
     * @param context the context of application
     */
    fun init(context: Context) {
        init(context, LogConfiguration())
    }

    /**
     * Initialize log system, should be called only once.
     *
     * @param context           the context of application
     * @param configuration     the configuration of log
     */
    fun init(context: Context, configuration: LogConfiguration) {
        if (sIsInitialized) {
            Platform.get().warn("XLog is already initialized, do not initialize again")
        }
        sIsInitialized = true
        sLogConfiguration = configuration
        val appName = CommonUtils.getAppDisplayName(context)
        val path =
            if (configuration.sPath.trim().isEmpty())
                CommonUtils.getExternalAppDataPath(context).plus("/log")
            else
                configuration.sPath
        sFilePrinter = FilePrinter(appName, configuration.sDay, path)
        setPrinter(AndroidPrinter(), sFilePrinter)
    }

    /**
     * Log an object with level [LogLevel.VERBOSE].
     *
     * @param object the object to log
     */
    fun v(`object`: Any?) {
        assertInitialization()
        println<Any?>(LogLevel.VERBOSE, `object`)
    }

    /**
     * Log a message with level [LogLevel.VERBOSE].
     *
     * @param format the format of the message to log
     * @param args   the arguments of the message to log
     */
    fun v(format: String, vararg args: Any?) {
        assertInitialization()
        println(LogLevel.VERBOSE, format(format, *args))
    }

    /**
     * Log a message and a throwable with level [LogLevel.VERBOSE].
     *
     * @param msg the message to log
     * @param tr  the throwable to be log
     */
    fun vt(msg: String?, tr: Throwable?) {
        assertInitialization()
        println(LogLevel.VERBOSE, msg, tr)
    }

    /**
     * Log an object with level [LogLevel.DEBUG].
     *
     * @param object the object to log
     */
    fun d(`object`: Any?) {
        assertInitialization()
        println<Any?>(LogLevel.DEBUG, `object`)
    }

    /**
     * Log a message with level [LogLevel.DEBUG].
     *
     * @param format the format of the message to log
     * @param args   the arguments of the message to log
     */
    fun d(format: String, vararg args: Any?) {
        assertInitialization()
        println(LogLevel.DEBUG, format(format, *args))
    }

    /**
     * Log a message and a throwable with level [LogLevel.DEBUG].
     *
     * @param msg the message to log
     * @param tr  the throwable to be log
     */
    fun dt(msg: String?, tr: Throwable?) {
        assertInitialization()
        println(LogLevel.DEBUG, msg, tr)
    }

    /**
     * Log an object with level [LogLevel.INFO].
     *
     * @param object the object to log
     */
    fun i(`object`: Any?) {
        assertInitialization()
        println<Any?>(LogLevel.INFO, `object`)
    }

    /**
     * Log a message with level [LogLevel.INFO].
     *
     * @param format the format of the message to log
     * @param args   the arguments of the message to log
     */
    fun i(format: String, vararg args: Any?) {
        assertInitialization()
        println(LogLevel.INFO, format(format, *args))
    }

    /**
     * Log a message and a throwable with level [LogLevel.INFO].
     *
     * @param msg the message to log
     * @param tr  the throwable to be log
     */
    fun it(msg: String?, tr: Throwable?) {
        assertInitialization()
        println(LogLevel.INFO, msg, tr)
    }

    /**
     * Log an object with level [LogLevel.WARN].
     *
     * @param object the object to log
     */
    fun w(`object`: Any?) {
        assertInitialization()
        println<Any?>(LogLevel.WARN, `object`)
    }

    /**
     * Log a message with level [LogLevel.WARN].
     *
     * @param format the format of the message to log
     * @param args   the arguments of the message to log
     */
    fun w(format: String, vararg args: Any?) {
        assertInitialization()
        println(LogLevel.WARN, format(format, *args))
    }

    /**
     * Log a message and a throwable with level [LogLevel.WARN].
     *
     * @param msg the message to log
     * @param tr  the throwable to be log
     */
    fun wt(msg: String?, tr: Throwable?) {
        assertInitialization()
        println(LogLevel.WARN, msg, tr)
    }

    /**
     * Log an object with level [LogLevel.ERROR].
     *
     * @param object the object to log
     */
    fun e(`object`: Any?) {
        assertInitialization()
        println<Any?>(LogLevel.ERROR, `object`)
    }

    /**
     * Log a message with level [LogLevel.ERROR].
     *
     * @param format the format of the message to log
     * @param args   the arguments of the message to log
     */
    fun e(format: String, vararg args: Any?) {
        assertInitialization()
        println(LogLevel.ERROR, format(format, *args))
    }

    /**
     * Log a message and a throwable with level [LogLevel.ERROR].
     *
     * @param msg the message to log
     * @param tr  the throwable to be log
     */
    fun et(msg: String?, tr: Throwable?) {
        assertInitialization()
        println(LogLevel.ERROR, msg, tr)
    }

    /**
     * Log a JSON string, with level [LogLevel.DEBUG] by default.
     *
     * @param json the JSON string to log
     */
    fun json(json: String?) {
        assertInitialization()
        if (LogLevel.DEBUG < getLogFilterLevel()) return
        println(LogLevel.DEBUG, SystemCompat.lineSeparator + DefaultJsonFormatter().format(json))
    }

    /**
     * Log a XML string, with level [LogLevel.DEBUG] by default.
     *
     * @param xml the XML string to log
     */
    fun xml(xml: String?) {
        assertInitialization()
        if (LogLevel.DEBUG < getLogFilterLevel()) return
        println(LogLevel.DEBUG, SystemCompat.lineSeparator + DefaultXmlFormatter().format(xml))
    }

    /**
     * Export log from file system or database
     *
     * @param exportPath The export target path
     */
    fun exportLog(exportPath: String): Boolean {
        check(!sIsExportLog) { "Currently exporting logs" }
        val directoryPath = Paths.get(exportPath)
        check(Files.exists(directoryPath)) { "Path is not exist!" }
        sIsExportLog = true
        return try {
            sFilePrinter?.export(exportPath) == true
        } catch (e: Exception) {
            false
        } finally {
            sIsExportLog = false
        }
    }

    /**
     * Set Printer
     *
     * @param printer the printers, each log would be printed by all of the printers
     */
    private fun setPrinter(vararg printer: Printer?) {
        sPrinter = PrinterSet(printer)
    }

    /**
     * Throw an IllegalStateException if not initialized.
     */
    private fun assertInitialization() {
        check(sIsInitialized) { "Do you forget to initialize XLog?" }
    }

    /**
     * Print an object in a new line.
     *
     * @param logLevel the log level of the printing object
     * @param object   the object to print
     */
    private fun <T> println(logLevel: Int, `object`: T?) {
        val objectString = if (`object` is Array<*>) {
            `object`.contentDeepToString()
        } else {
            `object`?.toString() ?: "null"
        }
        printlnInternal(logLevel, objectString)
    }

    /**
     * Print a log in a new line.
     *
     * @param logLevel the log level of the printing log
     * @param msg      the message you would like to log
     * @param tr       a throwable object to log
     */
    private fun println(logLevel: Int, msg: String?, tr: Throwable?) {
        printlnInternal(
            logLevel, (if (msg == null || msg.isEmpty()) "" else (msg + SystemCompat.lineSeparator))
                    + DefaultThrowableFormatter().format(tr), printStackInfo = false
        )
    }

    /**
     * Print a log in a new line internally.
     *
     * @param logLevel the log level of the printing log
     * @param msg      the message you would like to log
     */
    private fun printlnInternal(logLevel: Int, msg: String?, printStackInfo: Boolean = true) {
        if (logLevel < getLogFilterLevel()) return
        val currentThread = Thread.currentThread()
        val stackTrace: String = if (logLevel >= LogLevel.ERROR)
            DefaultStackTraceFormatter().format(
                StackTraceUtil.getRealStackTrack(
                    Throwable().stackTrace,
                    null
                )
            )
        else ""

        var fileName = ""
        var line = -1
        if (currentThread.stackTrace.size >= 7) {
            val stackTraceElement = currentThread.stackTrace[6]
            fileName = stackTraceElement.fileName
            line = stackTraceElement.lineNumber
        }

        val log = LogItem(
            level = logLevel,
            msg = msg ?: "",
            threadName = currentThread.name,
            stackTraceInfo = if (printStackInfo) stackTrace else "",
            fileName = fileName,
            line = line
        )
        sPrinter?.println(log)
    }

    /**
     * Format strings to prevent unexpected crashes
     */
    private fun format(format: String, vararg args: Any?): String {
        return try {
            String.format(format, *args)
        } catch (_: Exception) {
            format
        }
    }

    /**
     * Get log filtering level, default [LogLevel.DEBUG]
     */
    private fun getLogFilterLevel(): Int {
        return sLogConfiguration?.sLevel ?: LogLevel.DEBUG
    }
}

