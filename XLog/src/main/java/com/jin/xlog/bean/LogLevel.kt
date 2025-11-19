package com.jin.xlog.bean

/**
 * @author yongjin.guo
 * @date 2025/6/13 15:31
 *
 * Log level indicate how important the log is.
 * <p>
 * Usually when we log a message, we also specify the log level explicitly or implicitly,
 * so if we setup a log level using <code>XLog.init(...)</code>, all the logs which is with
 * a log level smaller than the setup one would not be printed.
 * <p>
 * The priority of log levels is: {@link #VERBOSE} &lt; {@link #DEBUG} &lt; {@link #INFO} &lt;
 * {@link #WARN} &lt; {@link #ERROR}.
 * <br>And there are two special log levels which are usually used for Log#init:
 *
 * @see #VERBOSE
 * @see #DEBUG
 * @see #INFO
 * @see #WARN
 * @see #ERROR
 */
object LogLevel {
    /**
     * Log level for XLog.v.
     */
    const val VERBOSE: Int = 2

    /**
     * Log level for XLog.d.
     */
    const val DEBUG: Int = 3

    /**
     * Log level for XLog.i.
     */
    const val INFO: Int = 4

    /**
     * Log level for XLog.w.
     */
    const val WARN: Int = 5

    /**
     * Log level for XLog.e.
     */
    const val ERROR: Int = 6

    /**
     * Get a name representing the specified log level.
     *
     *
     * The returned name may be<br></br>
     * Level less than [LogLevel.VERBOSE]: "VERBOSE-N", N means levels below
     * [LogLevel.VERBOSE]<br></br>
     * [LogLevel.VERBOSE]: "VERBOSE"<br></br>
     * [LogLevel.DEBUG]: "DEBUG"<br></br>
     * [LogLevel.INFO]: "INFO"<br></br>
     * [LogLevel.WARN]: "WARN"<br></br>
     * [LogLevel.ERROR]: "ERROR"<br></br>
     * Level greater than [LogLevel.ERROR]: "ERROR+N", N means levels above
     * [LogLevel.ERROR]
     *
     * @param logLevel the log level to get name for
     * @return the name
     */
    fun getLevelName(logLevel: Int): String {
        val levelName: String = when (logLevel) {
            VERBOSE -> "VERBOSE"
            DEBUG -> "DEBUG"
            INFO -> "INFO"
            WARN -> "WARN"
            ERROR -> "ERROR"
            else -> if (logLevel < VERBOSE) {
                "VERBOSE-" + (VERBOSE - logLevel)
            } else {
                "ERROR+" + (logLevel - ERROR)
            }
        }
        return levelName
    }

    /**
     * Get a short name representing the specified log level.
     *
     *
     * The returned name may be<br></br>
     * Level less than [LogLevel.VERBOSE]: "V-N", N means levels below
     * [LogLevel.VERBOSE]<br></br>
     * [LogLevel.VERBOSE]: "V"<br></br>
     * [LogLevel.DEBUG]: "D"<br></br>
     * [LogLevel.INFO]: "I"<br></br>
     * [LogLevel.WARN]: "W"<br></br>
     * [LogLevel.ERROR]: "E"<br></br>
     * Level greater than [LogLevel.ERROR]: "E+N", N means levels above
     * [LogLevel.ERROR]
     *
     * @param logLevel the log level to get short name for
     * @return the short name
     */
    fun getShortLevelName(logLevel: Int): String {
        val levelName: String = when (logLevel) {
            VERBOSE -> "V"
            DEBUG -> "D"
            INFO -> "I"
            WARN -> "W"
            ERROR -> "E"
            else -> if (logLevel < VERBOSE) {
                "V-" + (VERBOSE - logLevel)
            } else {
                "E+" + (logLevel - ERROR)
            }
        }
        return levelName
    }
}