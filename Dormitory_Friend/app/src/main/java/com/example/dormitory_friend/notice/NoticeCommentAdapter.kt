package com.example.dormitory_friend.notice

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.example.dormitory_friend.R
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.android.synthetic.main.listview_noticecomment.view.*

class NoticeCommentAdapter(val context: Context,val querySnapshot: QuerySnapshot):BaseAdapter() {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {


        val view : View = LayoutInflater.from(context).inflate(R.layout.listview_noticecomment,null)

        view.comment_content.setText(querySnapshot.documents.get(position).get("comment").toString())
        view.comment_nickname.setText(querySnapshot.documents.get(position).get("nickname").toString())


        return view
    }

    override fun getItem(position: Int): Any {
        return 0
    }

    override fun getItemId(position: Int): Long {

        return 0
    }

    override fun getCount(): Int {
        return querySnapshot.size()
    }

}