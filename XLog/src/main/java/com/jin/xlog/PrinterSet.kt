package com.jin.xlog

import com.jin.xlog.bean.LogItem
import com.jin.xlog.inter.Printer


/**
 * @author yongjin.guo
 * @date 2025/6/13 15:57
 *
 * Represents a group of Printers that should used to print logs in the same time, each printer
 * may probably print the log to different place.
 */
internal class PrinterSet(private val printers: Array<out Printer?>) : Printer {

    override fun println(logItem: LogItem) {
        for (printer in printers) {
            printer?.println(logItem)
        }
    }
}
