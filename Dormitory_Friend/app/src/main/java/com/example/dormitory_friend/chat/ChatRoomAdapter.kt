package com.example.dormitory_friend.chat

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.dormitory_friend.R
import kotlinx.android.synthetic.main.mymessage_item.view.*

class ChatRoomAdapter (val context : Context, val chatList : MutableList<chatData>) : RecyclerView.Adapter<ChatRoomAdapter.ChatViewHolder>(){

    private val dataList = chatList
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.mymessage_item, parent, false)

        return ChatViewHolder(view, parent)
    }

    override fun getItemCount(): Int {
        return chatList.size
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {

        val data = dataList[position]

        if(position / 2 == 0)
        {
            holder.text_message.setText(data.getMsg())
            holder.time_txt.setText(data.getTimestamp())
        }
        else{
            holder.text_message.setText(data.getMsg())
            holder.time_txt.setText(data.getTimestamp())
        }



    }


    class ChatViewHolder(view : View, parent: ViewGroup) : RecyclerView.ViewHolder(view){
        var text_message = view.findViewById(R.id.textView_message_text) as TextView
        var time_txt = view.findViewById(R.id.textView_message_time) as TextView
    }
}