package com.jin.xlog.factory

import com.jin.xlog.bean.LogItem
import com.jin.xlog.bean.LogLevel
import com.jin.xlog.SystemCompat
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter


/**
 * @author yongjin.guo
 * @date 2025/6/16 9:18
 * @description
 */
internal class DefaultFlattener {

    fun flatten(item: LogItem): String {
        try {
            val builder = StringBuilder()
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")
            val dateTime =
                LocalDateTime.ofInstant(
                    Instant.ofEpochMilli(item.timeMillis),
                    ZoneId.systemDefault()
                )
            builder.append("[")
                .append(dateTime.format(formatter))
                .append("]")
            if (item.threadName.isNotEmpty()) {
                builder.append(" [").append(item.threadName).append("]")
            }
            builder.append(" ").append(LogLevel.getLevelName(item.level))
            if (item.fileName.isNotEmpty()) {
                builder.append(" [").append(item.fileName).append("]")
            }
            builder.append(" [").append(item.line).append(" line]")
            if (item.msg.isNotEmpty()) {
                builder.append(" ").append(item.msg)
            }
            if (item.stackTraceInfo.isNotEmpty()) {
                builder.append(SystemCompat.lineSeparator).append(item.stackTraceInfo)
            }
            return builder.toString()
        } catch (e: Exception) {
            return "DefaultFlattener flatten error:${e.message}"
        }
    }
}