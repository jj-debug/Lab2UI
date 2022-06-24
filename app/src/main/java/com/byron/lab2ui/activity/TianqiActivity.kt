package com.byron.lab2ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import com.byron.lab2ui.R
import kotlinx.android.synthetic.main.activity_tianqi.*
import org.json.JSONObject
import org.json.JSONArray
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.lang.Exception
import java.lang.StringBuilder
import java.net.HttpURLConnection
import java.net.URL

//import android.support.v7.app.AppCompatActivity;
class TianqiActivity : AppCompatActivity() {
    private var btn1: Button? = null
    private var btn2: Button? = null
    private var textView: TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tianqi)
        textView = findViewById(R.id.textView)
        btn1 = findViewById(R.id.button)
        btn2 = findViewById(R.id.button2)
        button.setOnClickListener{ sendRequestWithHttpURLConnection() }
        button2.setOnClickListener{ sendRequestWithHttpURLConnection2() }
    }

    private fun sendRequestWithHttpURLConnection() {
        Thread {
            var connection: HttpURLConnection? = null
            var reader: BufferedReader? = null
            try {
                val url = URL("http://api.qingyunke.com/api.php?key=free&appid=0&msg=佛山天气")
                connection = url.openConnection() as HttpURLConnection
                connection.requestMethod = "GET"
                connection!!.connectTimeout = 8000
                connection.readTimeout = 8000
                if (connection.responseCode == 200) {
                    val `in` = connection.inputStream
                    reader = BufferedReader(InputStreamReader(`in`))
                    val response = StringBuilder()
                    var line: String?
                    while (reader.readLine().also { line = it } != null) {
                        response.append(line)
                    }
                    pareJSON1(response.toString())
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                if (reader != null) {
                    try {
                        reader.close()
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }
                connection?.disconnect()
            }
        }.start()
    }

    private fun sendRequestWithHttpURLConnection2() {
        Thread {
            var connection: HttpURLConnection? = null
            var reader: BufferedReader? = null
            try {
                val url = URL("http://t.weather.itboy.net/api/weather/city/101280800")
                connection = url.openConnection() as HttpURLConnection
                connection!!.requestMethod = "GET"
                connection.connectTimeout = 8000
                connection.readTimeout = 8000
                if (connection.responseCode == 200) {
                    val `in` = connection.inputStream
                    reader = BufferedReader(InputStreamReader(`in`))
                    val response = StringBuilder()
                    var line: String?
                    while (reader.readLine().also { line = it } != null) {
                        response.append(line)
                    }
                    pareJSON2(response.toString())
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                if (reader != null) {
                    try {
                        reader.close()
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }
                connection?.disconnect()
            }
        }.start()
    }

    //解析 1
    private fun pareJSON1(jsonData: String) {
        try {
            val jsonObject = JSONObject(jsonData)
            val result = jsonObject.getString("result")
            val content = jsonObject.getString("content")
            Log.d("MainActivity", "今天的天气是：\n$content")
            runOnUiThread {
                textView!!.text = ""
                textView!!.append(content)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    //解析 2
    private fun pareJSON2(jsonData: String) {
        try {
            val jsonObject = JSONObject(jsonData)
            val date = jsonObject.getString("date")
            val weather = StringBuilder()
            weather.append("日期:     $date\n")
            val cityInfo = jsonObject.getString("cityInfo")
            val cityinfoObject = JSONObject(cityInfo)
            val city = cityinfoObject.getString("city")
            weather.append("城市:     $city\n")
            val data = jsonObject.getString("data")
            val dataObj = JSONObject(data)
            weather.append(
                """
                    温度:     ${dataObj.getString("wendu")}
                    
                    """.trimIndent()
            )
            weather.append(
                """
                    湿度:     ${dataObj.getString("shidu")}
                    
                    """.trimIndent()
            )
            val forecast = dataObj.getString("forecast")
            val forecast_arr = JSONArray(forecast)
            for (i in 0 until forecast_arr.length()) {
                val jo = forecast_arr.getJSONObject(i)
                val next_date = jo.getString("ymd")
                val high = jo.getString("high")
                val week = jo.getString("week")
                Log.d("MainActivity", "预报日期: $next_date")
                Log.d("MainActivity", "高温: $high")
                Log.d("MainActivity", "星期: $week")
                weather.append("$next_date | $high | $week\n")
            }
            runOnUiThread {
                textView!!.text = ""
                textView!!.append(weather)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}