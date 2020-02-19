package com.example.dormitory_friend.chat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.FrameLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dormitory_friend.R
import kotlinx.android.synthetic.main.activity_chat_room.*

class ChatRoomActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_room)

        val data = chatData("채현욱", "안녕하세용", "2/19 15:06")
        val data2 = chatData("김경민", "네 안녕하세요", "2/19 15:07")
        val dataList = arrayListOf<chatData>()

        dataList.add(data)
        dataList.add(data2)

        val adpater = ChatRoomAdapter(this, dataList)
        content_chat.adapter = adpater
        content_chat.layoutManager = LinearLayoutManager(this)
    }
}
