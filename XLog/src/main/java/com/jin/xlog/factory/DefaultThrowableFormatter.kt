package com.jin.xlog.factory

import com.jin.xlog.utils.StackTraceUtil


/**
 * @author yongjin.guo
 * @date 2025/6/16 14:13
 * @description
 */
internal class DefaultThrowableFormatter {

	fun format(tr: Throwable?): String {
		return StackTraceUtil.getStackTraceString(tr) ?: ""
	}
}