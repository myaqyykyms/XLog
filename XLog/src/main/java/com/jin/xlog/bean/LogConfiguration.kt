package com.jin.xlog.bean

/**
 * @author yongjin.guo
 * @date 2025/6/20 9:35
 * @description Log management configuration class, used to configure parameters such as print level, save path, and save days for logs
 */
data class LogConfiguration(

    /**
     * The log level, the logs below of which would not be printed
     */
    var sLevel: Int = LogLevel.DEBUG,

    /**
     * The log save days, the logs would be deleted after this days
     */
    var sDay: Int = 30,

    /**
     * The log save path, the logs would be saved in this path
     */
    var sPath: String = ""
)