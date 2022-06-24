package com.byron.lab2ui.util

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import com.byron.lab2ui.constant.Constant
import com.byron.lab2ui.db.MyDbHelper

/**
 * 统一使用MyDbHelper单例
 * getDbHelper方便在不同的活动里使用
 * @author Byron
 * @date 220530
 */
object DbUtil {
    private var dbHelper: MyDbHelper? = null
    private var db:SQLiteDatabase ?= null

    fun init(context: Context){
        val dbVersion = 1
        dbHelper = MyDbHelper(context, Constant.DB_NAME, null, dbVersion)
        db = dbHelper?.writableDatabase
    }

    fun insertData(contentValues: ContentValues){
        try {
            db?.insert(Constant.DB_USER_TABLE, null, contentValues)
        }catch (e : Exception){
            e.stackTrace
        }
        Log.d("DbUtil", "insertData Success")
    }

    fun searchUserPawByName(userName: String?):String{
        var paw:String ?= null
        var name:Int ?= null
        val cursor = db!!.query(Constant.DB_USER_TABLE, null, null, null, null, null, null)

        if (cursor.moveToFirst()){
            do {
                name = cursor.getInt(cursor.getColumnIndex("name"))
                paw = cursor.getString(cursor.getColumnIndex("paw"))
                Log.d("DbUtil", "name: $name pass: $paw")
                if (name.toString().equals(userName)){
                    paw = cursor.getString(cursor.getColumnIndex("paw"))
                    break
                }
            }while (cursor.moveToNext())
        }
        return paw.toString()
    }
}