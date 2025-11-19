package com.jin.xlog.utils

import java.io.File
import java.io.IOException


/**
 * @author yongjin.guo
 * @date 2025/6/16 9:33
 * @description
 */
internal object FileUtils {

	fun createFileByDeleteOldFile(filePath: String) {
		createFileByDeleteOldFile(getFileByPath(filePath))
	}

	fun getFileByPath(filePath: String): File? {
		return if (filePath.isEmpty()) null else File(filePath)
	}

	fun createFileByDeleteOldFile(file: File?) {
		if (file == null) return
		// file exists and unsuccessfully delete then return false
		if (file.exists() && !file.delete()) return
		if (!createOrExistsDir(file.parentFile)) return
		try {
			file.createNewFile()
		} catch (e: IOException) {
			throw RuntimeException(e)
		}
	}

	fun createOrExistsDir(file: File?): Boolean {
		return file != null && (if (file.exists()) file.isDirectory else file.mkdirs())
	}
}