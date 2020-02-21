package com.example.dormitory_friend.notice.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.example.dormitory_friend.R
import com.example.dormitory_friend.firstfragments.adapters.NoticeFragmentAdapter
import com.example.dormitory_friend.notice.CommentModel
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.android.synthetic.main.listview_noticecomment.view.*
import kotlinx.android.synthetic.main.listview_noticefragment.view.*

class NoticeCommentAdapter(val context: Context,val commentArray: ArrayList<CommentModel>):BaseAdapter() {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {


        val view : View = LayoutInflater.from(context).inflate(R.layout.listview_noticecomment,null)

        var intent = Intent(context, NoticeFragmentAdapter::class.java)
        intent.putExtra("comment_count",commentArray.size)

        view.comment_content.setText(commentArray[position].comment)
        view.comment_nickname.setText(commentArray[position].nickname)




        return view
    }

    override fun getItem(position: Int): Any {
        return 0
    }

    override fun getItemId(position: Int): Long {

        return 0
    }

    override fun getCount(): Int {
        return commentArray.size
    }

}