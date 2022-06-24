package com.byron.lab2ui.util

import android.content.Context
import com.byron.lab2ui.constant.Constant

object SpUtil {

    fun spEditor(editorName:String, context: Context){
        val editor = context.getSharedPreferences(editorName, Context.MODE_PRIVATE).edit()
    }
}