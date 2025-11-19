package com.jin.xlog.printer

import android.util.Log
import com.jin.xlog.bean.LogItem
import com.jin.xlog.factory.DefaultFlattener
import com.jin.xlog.inter.Printer

/**
 * @author yongjin.guo
 * @date 2025/6/13 16:18
 * @description
 */
internal class AndroidPrinter : Printer {

    override fun println(
        logItem: LogItem
    ) {
        Log.println(
            logItem.level,
            "XLog",
            DefaultFlattener().flatten(logItem)
        )
    }
}