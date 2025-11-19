package com.jin.xlog.factory

import com.jin.xlog.Platform
import org.json.JSONArray
import org.json.JSONObject


/**
 * @author yongjin.guo
 * @date 2025/6/16 9:47
 * @description
 */

internal class DefaultJsonFormatter {
	fun format(json: String?): String? {
		var formattedString: String?
        if (json == null || json.trim { it <= ' ' }.isEmpty()) {
			Platform.get().warn("JSON empty.")
			return ""
		}
		try {
			if (json.startsWith("{")) {
				val jsonObject = JSONObject(json)
				formattedString = jsonObject.toString(JSON_INDENT)
			} else if (json.startsWith("[")) {
				val jsonArray = JSONArray(json)
				formattedString = jsonArray.toString(JSON_INDENT)
			} else {
				Platform.get().warn("JSON should start with { or [")
				return json
			}
		} catch (e: Exception) {
			Platform.get().warn(e.message)
			return json
		}
		return formattedString
	}

	companion object {
		private const val JSON_INDENT = 4
	}
}