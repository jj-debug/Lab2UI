package com.byron.lab2ui

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

sealed class MessageViewHolder(view: View) : RecyclerView.ViewHolder(view)

class LeftViewHolder(view: View) : MessageViewHolder(view) {
    val leftMsg: TextView = view.findViewById(R.id.leftMsg)
}

class RightViewHolder(view: View) : MessageViewHolder(view) {
    val rightMsg: TextView = view.findViewById(R.id.rightMsg)
}