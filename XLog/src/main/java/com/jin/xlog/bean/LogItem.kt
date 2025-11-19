package com.jin.xlog.bean

/**
 * @author yongjin.guo
 * @date 2025/6/13 15:34
 * @description
 */
internal data class LogItem(

    /**
     * Level of the log.
     *
     * @see LogLevel
     */
    var level: Int = LogLevel.DEBUG,

    /**
     * The timeMillis, should not be null.
     */
    var timeMillis: Long = System.currentTimeMillis(),

    /**
     * The formatted message, should not be null.
     */
    var msg: String = "",

    /**
     * The formatted thread name, null if thread info is disabled.
     */
    var threadName: String = "",

    /**
     * The formatted stack trace info, null if stack trace info is disabled.
     */
    var stackTraceInfo: String = "",

    /**
     * The file name of the log
     */
    var fileName: String = "",

    /**
     * The number of line
     */
    var line: Int = 0
) {
    override fun toString(): String {
        return "level:${LogLevel.getLevelName(level)} timeMillis:$timeMillis threadName:$threadName fileName:$fileName line:$line msg:$msg"
    }
}