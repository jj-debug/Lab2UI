package com.byron.lab2ui

import android.app.Application
import com.byron.lab2ui.db.MyDbHelper
import com.byron.lab2ui.util.DbUtil

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        //初始化db
        DbUtil.init(applicationContext)

    }
}