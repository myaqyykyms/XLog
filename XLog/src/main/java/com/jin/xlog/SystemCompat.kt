package com.jin.xlog


/**
 * @author yongjin.guo
 * @date 2025/6/13 16:22
 *
 * System environment.
 */
internal object SystemCompat {
	/**
	 * The line separator of system.
	 */
	var lineSeparator: String? = Platform.get().lineSeparator()
}