package com.example.dormitory_friend.firstfragments.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.example.dormitory_friend.R
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.android.synthetic.main.listview_firstfragment.view.*

class FirstFragmentAdapter (var context : Context, var result : QuerySnapshot) : BaseAdapter(){

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View{

        val view = LayoutInflater.from(context).inflate(R.layout.listview_firstfragment, null)

        val document = result.documents.get(p0)

        view.lv_nickname.setText(document.get("nickname").toString())
        view.lv_age.setText("나이 : " + document.get("age").toString())
        view.lv_grade.setText("학년 : " + document.get("grade").toString())
        view.lv_country.setText(document.get("country").toString())
        view.lv_major.setText(document.get("major").toString())

        return view
    }

    override fun getItem(p0: Int): Any {
        return 0
    }

    override fun getItemId(p0: Int): Long {
        return 0
    }

    override fun getCount(): Int {

        return result.size()
    }
}