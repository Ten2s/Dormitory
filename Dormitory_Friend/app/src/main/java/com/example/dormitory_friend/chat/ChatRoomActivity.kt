package com.example.dormitory_friend.chat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.FrameLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dormitory_friend.FirebaseUtils
import com.example.dormitory_friend.MainActivity
import com.example.dormitory_friend.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import kotlinx.android.synthetic.main.activity_chat_room.*

class ChatRoomActivity : AppCompatActivity() {


    val dataList = arrayListOf<chatData?>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_room)
        val uid = intent.getStringExtra("uid")
        val name = intent.getStringExtra("username")
        var adpater = ChatRoomAdapter(this, dataList)

        username.setText(name)
        val myRef = FirebaseUtils.realtime.child(FirebaseUtils.getUid()).child(uid)
        val otherRef = FirebaseUtils.realtime.child(uid).child(FirebaseUtils.getUid())

        val childEventListener = object : ChildEventListener{
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
            override fun onChildMoved(p0: DataSnapshot, p1: String?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
            override fun onChildChanged(p0: DataSnapshot, p1: String?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
            override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                val data = p0.getValue(chatData::class.java)
                dataList.add(data)
                adpater.notifyDataSetChanged()
                content_chat.scrollToPosition(dataList.size - 1)
            }
            override fun onChildRemoved(p0: DataSnapshot) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        }


        myRef.addChildEventListener(childEventListener)

        send_button.setOnClickListener {
            if(!message_txt.text.toString().equals("")) {
                val data = chatData(name, FirebaseUtils.getUid(), message_txt.text.toString())
                myRef.push().setValue(data)
                otherRef.push().setValue(data)
                message_txt.setText("")
            }
        }
        content_chat.adapter = adpater
        content_chat.layoutManager = LinearLayoutManager(this)
    }
}
