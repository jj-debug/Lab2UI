package com.byron.lab2ui.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ListView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.byron.lab2ui.R
import com.byron.lab2ui.activity.MessageActivity
import com.byron.lab2ui.adapter.MsgAdapter
import com.byron.lab2ui.bean.MsgBean
import com.byron.lab2ui.util.FileUtil

class MsgFragment: Fragment() {

    private val msgList = ArrayList<MsgBean>()

    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?,savedInstanceState: Bundle?): View? {
        var view:View = inflater.inflate(R.layout.frag_msg, container, false)
        var msgLv:ListView = view.findViewById(R.id.msg_lv)
        initMsg()
        val msgAdapter:MsgAdapter = activity?.let { MsgAdapter(it, R.layout.frag_msg_item, msgList) }!!
        msgLv.adapter = msgAdapter
        //listViewItem的点击事件
        msgLv.setOnItemClickListener { parent,view,position,id ->
            val msg=msgList[position]
            Log.d("main", msg.name)
            //保存上次聊天人的名字到文件里
            FileUtil.save(msg.name, view.context)
            val intent = Intent(context, MessageActivity::class.java)
            intent.putExtra("name", msg.name)
            startActivity(intent)

        }
        return view
    }

    //private val msgList = ArrayList<MsgBean>()
    fun initMsg(){
        repeat(2){
            msgList.add(MsgBean(R.drawable.s3_shenjilan,"Brook", "Yo，Hoooo~"))
            msgList.add(MsgBean(R.drawable.s4_sunjiadong,"Tony", "Hello World"))
            msgList.add(MsgBean(R.drawable.s5_liyannian,"Liu", ""))
            msgList.add(MsgBean(R.drawable.s6_zhangfuqing,"Monkey", ""))
            msgList.add(MsgBean(R.drawable.s7_yuanlongping,"Hentai", ""))
            msgList.add(MsgBean(R.drawable.s8_huangxuhua,"Robin", ""))
            msgList.add(MsgBean(R.drawable.s9_tuyouyou,"Lie", ""))
            msgList.add(MsgBean(R.drawable.s10ys_zhuoxingming,"Naruto", ""))
            msgList.add(MsgBean(R.drawable.s11ys_yepeijian,"Hashirama", ""))
            msgList.add(MsgBean(R.drawable.s12_ys_wangchi,"Tobirama", ""))
            msgList.add(MsgBean(R.drawable.s13_ys_daiyongjiu,"Kaguya", ""))
            msgList.add(MsgBean(R.drawable.s14_ys_liushaojun,"Minato", ""))
        }
    }


}