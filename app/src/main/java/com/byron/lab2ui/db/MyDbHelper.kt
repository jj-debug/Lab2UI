package com.byron.lab2ui.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

/**
 * 用户数据库
 * @author Byron
 * @date 220530
 */
class MyDbHelper(context: Context?, name: String?, factory: SQLiteDatabase.CursorFactory?, version: Int)
    : SQLiteOpenHelper(context, name, factory, version) {

    private val TAG = MyDbHelper::class.simpleName

    private val createUser:String = "create table user (" +
            "name integer primary key autoincrement, " +
            "paw text, " +
            "sex text, " +
            "city text, " +
            "u_birth text)"

    override fun onCreate(db: SQLiteDatabase?) {
        //创建表
        db?.execSQL(createUser)
        Log.d(TAG, createUser)

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }

}