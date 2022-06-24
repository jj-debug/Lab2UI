package com.byron.lab2ui.util

import android.content.Context
import java.io.*
import java.lang.StringBuilder

//文件存储操作
object FileUtil {

    fun save(saveText:String, context: Context){
        try {
            val output = context.openFileOutput("data", Context.MODE_PRIVATE)
            val write = BufferedWriter(OutputStreamWriter(output))
            write.use {
                it.write(saveText)
            }
        }catch (e: IOException){
            e.printStackTrace()
        }
    }

    fun loadName(context: Context):String{
        val content = StringBuilder()
        try {
            val input = context.openFileInput("data")
            val reader = BufferedReader(InputStreamReader(input))
            reader.use {
                reader.forEachLine {
                    content.append(it)
                }
            }
        }catch (e: IOException){
            e.printStackTrace()
        }
        return content.toString()
    }
}