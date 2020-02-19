package com.example.dormitory_friend.notice.adapters

import android.content.Context
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.example.dormitory_friend.R
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.android.synthetic.main.listview_noticefragment.view.*
import java.time.LocalDate

class NoticeFragmentAdapter(val context: Context, val querysnapshot : QuerySnapshot): BaseAdapter() {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = LayoutInflater.from(context).inflate(R.layout.listview_noticefragment, null)

        val now = LocalDate.now()
        view.lv_title.setText(querysnapshot.documents.get(position).get("title").toString())
        view.lv_id.setText(querysnapshot.documents.get(position).get("nickname").toString())
        view.lv_time.setText("$now")
        return view
    }

    override fun getItem(position: Int): Any {
        return 0
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return querysnapshot.size()
    }

}