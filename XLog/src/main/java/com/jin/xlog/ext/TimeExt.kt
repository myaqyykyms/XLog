package com.jin.xlog.ext

/**
 * @author: guoyongjin
 * @date: 2025/11/19 21:02
 * @description:
 */

/**
 * Days to milliseconds
 */
internal fun Int.dayToMillis(): Long {
    return this * 24 * 60 * 60 * 1000L
}
