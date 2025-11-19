package com.jin.xlog.inter

import com.jin.xlog.bean.LogItem

/**
 * @author yongjin.guo
 * @date 2025/6/13 15:33
 *
 * A printer is used for printing the log to somewhere, like android shell, terminal
 * or file system.
 * <p>
 * There are 4 main implementation of Printer.
 * <br>{@link AndroidPrinter}, print log to android shell terminal.
 * <br>{@link ConsolePrinter}, print log to console via System.out.
 * <br>{@link FilePrinter}, print log to file system.
 * <br>{@link RemotePrinter}, print log to remote server, this is empty implementation yet.
 */
internal interface Printer {

	/**
	 * Print log in new line.
	 *
	 * @param logItem the log
	 */
	fun println(logItem: LogItem)
}