package com.byron.lab2ui.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.byron.lab2ui.R
import com.byron.lab2ui.activity.TianqiActivity
import kotlinx.android.synthetic.main.frag_mine.*
import kotlinx.android.synthetic.main.frag_mine.view.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class MineFragment: Fragment() {


    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?,savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.frag_mine, container, false)


        view.forceOffline.setOnClickListener {
            val intent = Intent("com.example.broadcastbestpractice.FORCE_OFFLINE")
            context?.sendBroadcast(intent)
        }


        return view
    }
}