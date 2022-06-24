package com.byron.lab2ui.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.byron.lab2ui.R
import com.byron.lab2ui.bean.MsgBean
/**
 * 消息fragment的listView适配器
 * @author Byron
 * @date220509
 */
class MsgAdapter( activity: Activity, val resourceId:Int, data:List<MsgBean>):
    ArrayAdapter<MsgBean>(activity, resourceId, data) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view:View = LayoutInflater.from(context).inflate(resourceId, parent, false)
        val msgIv: ImageView = view.findViewById(R.id.msg_item_iv)
        val msgName: TextView = view.findViewById(R.id.msg_item_name)
        val msgMsg: TextView = view.findViewById(R.id.msg_item_msg)
        val msg = getItem(position)
        if (msg != null){
            msgIv.setImageResource(msg.imgId)
            msgName.text = msg.name
            msgMsg.text = msg.msg
        }
        return view
    }
}