package com.byron.lab2ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.byron.lab2ui.LeftViewHolder
import com.byron.lab2ui.MessageViewHolder
import com.byron.lab2ui.R
import com.byron.lab2ui.RightViewHolder
import com.byron.lab2ui.constant.Message


class MessageAdapter(val msgList: List<Message>) : RecyclerView.Adapter<MessageViewHolder>() {

    override fun getItemViewType(position: Int): Int {
        val msg = msgList[position]
        return msg.type
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = if (viewType == Message.TYPE_RECEIVED) {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.message_left_item, parent, false)
        LeftViewHolder(view)
    } else {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.message_right_item, parent, false)
        RightViewHolder(view)
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val msg = msgList[position]
        when (holder) {
            is LeftViewHolder -> holder.leftMsg.text = msg.content
            is RightViewHolder -> holder.rightMsg.text = msg.content
         }
    }

    override fun getItemCount() = msgList.size

}