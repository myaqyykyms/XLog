package com.jin.xlog.factory

import com.jin.xlog.Platform
import com.jin.xlog.SystemCompat
import java.io.StringReader
import java.io.StringWriter
import javax.xml.transform.OutputKeys
import javax.xml.transform.Source
import javax.xml.transform.TransformerFactory
import javax.xml.transform.stream.StreamResult
import javax.xml.transform.stream.StreamSource


/**
 * @author yongjin.guo
 * @date 2025/6/16 13:40
 * @description
 */
internal class DefaultXmlFormatter {

	private val mXmlIndent: Int = 4

	fun format(xml: String?): String {
		val formattedString: String
		if (xml == null || xml.trim { it <= ' ' }.isEmpty()) {
			Platform.get().warn("XML empty.")
			return ""
		}
		try {
			val xmlInput: Source = StreamSource(StringReader(xml))
			val xmlOutput = StreamResult(StringWriter())
			val transformer = TransformerFactory.newInstance().newTransformer()
			transformer.setOutputProperty(OutputKeys.INDENT, "yes")
			transformer.setOutputProperty(
				"{http://xml.apache.org/xslt}indent-amount",
				mXmlIndent.toString()
			)
			transformer.transform(xmlInput, xmlOutput)
			formattedString = xmlOutput.writer.toString().replaceFirst(
				">".toRegex(), ">"
						+ SystemCompat.lineSeparator
			)
		} catch (e: Exception) {
			Platform.get().warn(e.message)
			return xml
		}
		return formattedString
	}
}