package com.jin.xlog.printer

import com.jin.xlog.factory.DefaultFlattener
import com.jin.xlog.bean.LogItem
import com.jin.xlog.inter.Printer

/**
 * @author yongjin.guo
 * @date 2025/6/13 16:17
 * @description
 */
internal class ConsolePrinter : Printer {
    override fun println(
        logItem: LogItem
    ) {
        println(DefaultFlattener().flatten(logItem))
    }
}