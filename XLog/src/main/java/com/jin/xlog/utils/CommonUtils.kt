package com.jin.xlog.utils

import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.os.Environment
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths
import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream


/**
 * @author yongjin.guo
 * @date 2025/6/16 9:33
 * @description
 */
internal object CommonUtils {

    /**
     * Return name of application
     */
    fun getAppDisplayName(context: Context): String {
        val packageManager = context.packageManager
        var applicationInfo: ApplicationInfo? = null
        try {
            applicationInfo = packageManager.getApplicationInfo(context.packageName, 0)
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        if (applicationInfo != null) {
            return packageManager.getApplicationLabel(applicationInfo).toString()
        }
        return ""
    }

    /**
     * Return the path of /storage/emulated/0/Android/data/package.
     */
    fun getExternalAppDataPath(context: Context): String {
        if (Environment.MEDIA_MOUNTED != Environment.getExternalStorageState()) return ""
        val externalCacheDir: File = context.externalCacheDir ?: return ""
        return getAbsolutePath(externalCacheDir.parentFile)
    }

    /**
     * Return the absolute path of file
     */
    private fun getAbsolutePath(file: File?): String {
        if (file == null) return ""
        return file.absolutePath
    }

    /**
     * Compress the file into a zip file
     */
    fun zipFile(source: MutableList<String>, zipPath: String) {
        val zipFile = Paths.get(zipPath)
        ZipOutputStream(Files.newOutputStream(zipFile)).use { zos ->
            for (path in source) {
                val filePath = Paths.get(path)
                if (!Files.exists(filePath)) continue
                val entry = ZipEntry(filePath.fileName.toString())
                zos.putNextEntry(entry)
                Files.copy(filePath, zos)
                zos.closeEntry()
            }
        }
    }

}