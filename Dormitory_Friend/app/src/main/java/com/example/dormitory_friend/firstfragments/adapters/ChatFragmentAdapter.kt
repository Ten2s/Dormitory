package com.example.dormitory_friend.firstfragments.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.example.dormitory_friend.R
import com.example.dormitory_friend.chat.chatData
import kotlinx.android.synthetic.main.chatlistitem.view.*

class ChatFragmentAdapter(val context: Context, val dataList : ArrayList<chatData>) :BaseAdapter()
{
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = LayoutInflater.from(context).inflate(R.layout.chatlistitem, null)

        view.chat_list_nickname.setText((dataList[position].username))
        view.chat_list_recent.setText(dataList[position].message)
        return view
    }

    override fun getItem(position: Int): Any {
        return 0
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return dataList.size
    }

}