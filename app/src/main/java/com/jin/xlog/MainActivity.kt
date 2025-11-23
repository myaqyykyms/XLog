package com.jin.xlog

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.jin.xlog.bean.LogConfiguration
import com.jin.xlog.bean.LogLevel
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        XLog.init(
            this, LogConfiguration(
                sLevel = LogLevel.VERBOSE,
                sDay = 30
            )
        )
        testString()
    }

    private fun testString() {
        XLog.v("MainActivity onCreate")
        XLog.d("MainActivity onCreate")
        XLog.i("MainActivity onCreate")
        XLog.w("MainActivity onCreate")
        XLog.e("MainActivity onCreate")
    }

    private fun testArray() {
        val list = mutableListOf(1f, 2f, 3f, 4.6f, 5f)
        XLog.v("MainActivity onCreate:$list")
        XLog.d("MainActivity onCreate:$list")
        XLog.i("MainActivity onCreate:$list")
        XLog.w("MainActivity onCreate:$list")
        XLog.e("MainActivity onCreate:$list")
    }

    private fun testStringPattern() {
        XLog.v("MainActivity onCreate %d", LogLevel.VERBOSE)
        XLog.d("MainActivity onCreate %d", LogLevel.DEBUG)
        XLog.i("MainActivity onCreate %d", LogLevel.INFO)
        XLog.w("MainActivity onCreate %d", LogLevel.WARN)
        XLog.e("MainActivity onCreate %d", LogLevel.ERROR)
    }

    private fun testThrowable() {
        try {
            1 / 0
        } catch (e: Exception) {
            XLog.vt("MainActivity onCreate", e)
            XLog.dt("MainActivity onCreate", e)
            XLog.it("MainActivity onCreate", e)
            XLog.wt("MainActivity onCreate", e)
            XLog.et("MainActivity onCreate", e)
        }
    }

    private fun testJson() {
        val jsonObject = JSONObject()
        jsonObject.put("name", "张三")
        jsonObject.put("age", "21")
        jsonObject.put("sex", "女")
        XLog.json(jsonObject.toString(2))
    }

    private fun testXml() {
        val xmlString = "<team>" +
                "<member name='Elvis'/>" +
                "<member name='Leon'/>" +
                "</team>"
        XLog.xml(xmlString)
    }
}