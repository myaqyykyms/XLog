package com.jin.xlog.factory

import com.jin.xlog.SystemCompat


/**
 * @author yongjin.guo
 * @date 2025/6/16 9:41
 * @description
 */
internal class DefaultStackTraceFormatter {
    fun format(stackTrace: Array<StackTraceElement?>?): String {
        val sb = StringBuilder(256)
        if (stackTrace == null || stackTrace.isEmpty()) {
            return ""
        } else if (stackTrace.size == 1) {
            return "\t─ " + stackTrace[0].toString()
        } else {
            var i = 0
            val N = stackTrace.size
            while (i < N) {
                if (i != N - 1) {
                    sb.append("\t├ ")
                    sb.append(stackTrace[i].toString())
                    sb.append(SystemCompat.lineSeparator)
                } else {
                    sb.append("\t└ ")
                    sb.append(stackTrace[i].toString())
                }
                i++
            }
            return sb.toString()
        }
    }
}