package com.jin.xlog

import android.annotation.SuppressLint
import android.os.Build
import com.jin.xlog.inter.Printer
import com.jin.xlog.printer.AndroidPrinter
import com.jin.xlog.printer.ConsolePrinter


/**
 * @author yongjin.guo
 * @date 2025/6/13 16:15
 * @description
 */

internal open class Platform {
	@SuppressLint("NewApi")
	open fun lineSeparator(): String? {
		return System.lineSeparator()
	}

	open fun defaultPrinter(): Printer? {
		return ConsolePrinter()
	}

	fun warn(msg: String?) {
		println(msg)
	}

	fun error(msg: String?) {
		println(msg)
	}

	internal class Android : Platform() {
		override fun lineSeparator(): String? {
			return System.lineSeparator()
		}

		override fun defaultPrinter(): Printer? {
			return AndroidPrinter()
		}

//		fun warn(msg: String) {
//			Log.w(Config.LOG_TAG_DEFAULT, msg)
//		}
//
//		fun error(msg: String) {
//			Log.e(Config.LOG_TAG_DEFAULT, msg)
//		}
	}

	companion object {
		private val PLATFORM = findPlatform()

		fun get(): Platform {
			return PLATFORM
		}

		private fun findPlatform(): Platform {
			try {
				Class.forName("android.os.Build")
				if (Build.VERSION.SDK_INT != 0) {
					return Android()
				}
			} catch (ignored: ClassNotFoundException) {
			}
			return Platform()
		}
	}
}