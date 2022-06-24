package com.byron.lab2ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.byron.lab2ui.R
import com.byron.lab2ui.adapter.MessageAdapter
import com.byron.lab2ui.constant.Message
import kotlinx.android.synthetic.main.activity_message.*

class MessageActivity : AppCompatActivity(), View.OnClickListener {

    private val messageList = ArrayList<Message>()
    private lateinit var messageAdapter:MessageAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_message)
        val name = intent.getStringExtra("name")

        initMsg()
        val layoutManager = LinearLayoutManager(this)
        message_recyclerView.layoutManager = layoutManager
        if (!::messageAdapter.isInitialized) {
            messageAdapter = MessageAdapter(messageList)
        }
        message_recyclerView.adapter = messageAdapter
        message_send.setOnClickListener(this)

    }
    //初始化模拟信息
    private fun initMsg() {
        val msg1 = Message("Naruto~.", Message.TYPE_RECEIVED)
        messageList.add(msg1)
        val msg2 = Message("Sasuke!!~~~~~~", Message.TYPE_SENT)
        messageList.add(msg2)
        val msg3 = Message("Yo，Hoooo~", Message.TYPE_RECEIVED)
        messageList.add(msg3)
    }

    override fun onClick(v: View?) {
        when (v) {
            message_send -> {
                val content = message_inputText.text.toString()
                if (content.isNotEmpty()) {
                    val msg = Message(content, Message.TYPE_SENT)
                    messageList.add(msg)
                    messageAdapter.notifyItemInserted(messageList.size - 1) // 当有新消息时，刷新RecyclerView中的显示
                    message_recyclerView.scrollToPosition(messageList.size - 1)  // 将 RecyclerView定位到最后一行
                    message_inputText.setText("") // 清空输入框中的内容
                }
            }
        }
    }
}
