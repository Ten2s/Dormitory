package com.example.dormitory_friend.firstfragments.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.dormitory_friend.R
import com.example.dormitory_friend.chat.ChatRoomActivity
import com.example.dormitory_friend.firstfragments.NoticeListModel
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.android.synthetic.main.listview_noticefragment.view.*
import java.time.LocalDate

class NoticeFragmentAdapter(val context: Context, val dataArray : ArrayList<NoticeListModel>): BaseAdapter() {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = LayoutInflater.from(context).inflate(R.layout.listview_noticefragment, null)
        val now = LocalDate.now()
        view.lv_title.setText(dataArray[position].title)
        view.lv_id.setText(dataArray[position].nickname)
        view.lv_time.setText("$now")
        //view.comment_count.setText()




        return view
    }

    override fun getItem(position: Int): Any {
        return 0
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return dataArray.size
    }

}