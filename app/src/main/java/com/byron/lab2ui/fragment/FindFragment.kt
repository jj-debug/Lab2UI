package com.byron.lab2ui.fragment

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.byron.lab2ui.R
import kotlinx.android.synthetic.main.frag_find.*
import kotlinx.android.synthetic.main.frag_find.textView
import kotlinx.android.synthetic.main.frag_find.view.*
import kotlinx.android.synthetic.main.frag_find.view.button
import kotlinx.android.synthetic.main.frag_find.view.button2
import kotlinx.android.synthetic.main.frag_mine.*
import kotlinx.android.synthetic.main.frag_mine.view.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.util.jar.Manifest

class FindFragment: Fragment() {
    private var callNum:String ?= null
    private lateinit var mContext:Context
    private lateinit var mAdapter:ArrayAdapter<String>
    private val contactList = ArrayList<String>()

//    获取天气信息
    private fun sendRequestWithHttpURLConnection() {
        Thread {
            var connection: HttpURLConnection? = null
            var reader: BufferedReader? = null
            try {

                val city = weather_edit.text.toString()
                val u = "http://api.qingyunke.com/api.php?key=free&appid=0&msg=" + city + "天气"
                val url = URL(u)
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

    //当日天气解析
    private fun pareJSON1(jsonData: String) {
        try {
            val jsonObject = JSONObject(jsonData)
            val result = jsonObject.getString("result")
            val content = jsonObject.getString("content").subSequence(0,14)
            Log.d("MainActivity", "今天的天气是：\n$content")
            activity?.runOnUiThread {
                textView!!.text = ""
                textView!!.append(content)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    //未来天气信息解析
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
            activity?.runOnUiThread {
                textView!!.text = ""
                textView!!.append(weather)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?,savedInstanceState: Bundle?): View? {

        var view = inflater.inflate(R.layout.frag_find, container, false)
        mContext = view.context

//        当天天气按钮绑定时间
        view.button?.setOnClickListener{
            sendRequestWithHttpURLConnection()

//        未来天气按钮绑定时间
        }
        view.button2?.setOnClickListener{
            sendRequestWithHttpURLConnection2()
        }





        view.frag_call_btn.setOnClickListener {
            callNum = view.frag_call_edit.text.toString()
            //进行权限判断
            if (ContextCompat.checkSelfPermission(view.context, android.Manifest.permission.CALL_PHONE)!=
                    PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(mContext as Activity, arrayOf(android.Manifest.permission.CALL_PHONE), 1)
            }else{
                call()
            }
        }
        mAdapter = ArrayAdapter(mContext, android.R.layout.simple_list_item_1, contactList)
//        view.frag_contact_list.adapter = mAdapter

//        view.frag_get_info_btn.setOnClickListener {
//            if (ContextCompat.checkSelfPermission(mContext, android.Manifest.permission.READ_CONTACTS)!=
//                PackageManager.PERMISSION_GRANTED){
//                ActivityCompat.requestPermissions(mContext as Activity, arrayOf(android.Manifest.permission.READ_CONTACTS), 2)
//            }else{
//                readContact()
//            }
//        }

        return view
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            1 -> {
                if (grantResults.isEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    call()
                }else{
                    Toast.makeText(mContext, "你拒绝了权限申请", Toast.LENGTH_SHORT).show()
                }
            }
            2 -> {
                if (grantResults.isEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    call()
                }else{
                    Toast.makeText(mContext, "你拒绝了权限申请", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    //跳转到拨号界面
    private fun call(){
        try {
            val intent = Intent(Intent.ACTION_CALL)
            intent.data = Uri.parse("tel:$callNum")
            startActivity(intent)
        }catch (e:SecurityException){
            e.printStackTrace()
        }
    }

    @SuppressLint("Range")
    private fun readContact(){
        mContext.contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            null, null, null, null)?.apply {
                while (moveToNext()){
                    val displayName = getString(getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
                    val number = getString(getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                    Log.d("frag: ", "name: $displayName num: $number")
                    contactList.add("$displayName\n$number")
                }
            mAdapter.notifyDataSetChanged()
            close()
        }
    }

}