package com.example.dormitory_friend.chat

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.dormitory_friend.FirebaseUtils
import com.example.dormitory_friend.R
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class ChatRoomAdapter (val context : Context, val chatList : MutableList<chatData?>) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    private val dataList = chatList
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        lateinit var view : View

        return when(viewType){
            1-> {
                view = LayoutInflater.from(parent.context).inflate(R.layout.mymessage_item, parent, false)
                ChatViewHolder(view)
            }
            2->{
                view = LayoutInflater.from(parent.context).inflate(R.layout.mymessage_item2, parent, false)
                ChatViewHolder_other(view)
            }
            else -> throw RuntimeException("알 수 없는 뷰 타입 에러")
        }
    }

    override fun getItemCount(): Int {
        return chatList.size
    }

    override fun getItemViewType(position: Int): Int {
        var item = 0
        if(dataList[position]?.useruid.equals(FirebaseUtils.getUid()))
        {
            item = 1
        }
        else{
            item = 2
        }

        return item
    }
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val data = dataList[position]
        val date = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("hh:mm a")
        val formatted = date.format(formatter)

        when (data?.useruid) {
            FirebaseUtils.getUid() -> {
                (holder as ChatViewHolder).text_message.setText(data?.message)
                holder.time_txt.setText("$formatted")
            }

            else -> {
                (holder as ChatViewHolder_other).text_message.setText(data?.message)
                holder.time_txt.setText("$formatted")
            }
        }
    }


    inner class ChatViewHolder(view : View) : RecyclerView.ViewHolder(view){
        var text_message = view.findViewById(R.id.textView_message_text) as TextView
        var time_txt = view.findViewById(R.id.textView_message_time) as TextView
    }

    inner class ChatViewHolder_other(view : View) : RecyclerView.ViewHolder(view){
        var text_message = view.findViewById(R.id.textView_message_text_other) as TextView
        var time_txt = view.findViewById(R.id.textView_message_time_other) as TextView
    }

}