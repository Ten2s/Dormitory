package com.example.dormitory_friend

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*
import android.content.pm.PackageManager
import android.content.pm.PackageInfo
import android.util.Base64
import android.util.Log
import com.example.dormitory_friend.firstfragments.ChatFragment
import com.example.dormitory_friend.firstfragments.HomeFragment
import com.example.dormitory_friend.firstfragments.MyFragment
import com.example.dormitory_friend.firstfragments.NoticeFragment
import kotlinx.android.synthetic.main.fragment_my.*
import kotlinx.android.synthetic.main.undertab.*
import java.security.MessageDigest


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var getIntent : String = ""

        if(intent.getStringExtra("name") != null){
            getIntent = intent.getStringExtra("name")
        }

        if(getIntent != "" && getIntent.equals("notice"))
        {
            supportFragmentManager.beginTransaction().replace(R.id.fragment_area, NoticeFragment()).commit()
            home.setAlpha(0.5f)
            notice.setAlpha(1.0f)
            chat.setAlpha(0.5f)
            my_page.setAlpha(0.5f)
            main_title.text = "게시판"
        }
        else{
            supportFragmentManager.beginTransaction().replace(R.id.fragment_area, HomeFragment()).commit()
            home.setAlpha(1.0f)
            notice.setAlpha(0.5f)
            chat.setAlpha(0.5f)
            my_page.setAlpha(0.5f)
        }

        if(FirebaseUtils.auth.currentUser == null) {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }


        home.setOnClickListener {
            supportFragmentManager.beginTransaction().replace(R.id.fragment_area, HomeFragment()).commit()
            home.setAlpha(1.0f)
            notice.setAlpha(0.5f)
            chat.setAlpha(0.5f)
            my_page.setAlpha(0.5f)
            main_title.text = "추천 친구"
        }

        chat.setOnClickListener {
            supportFragmentManager.beginTransaction().replace(R.id.fragment_area, ChatFragment()).commit()
            home.setAlpha(0.5f)
            notice.setAlpha(0.5f)
            chat.setAlpha(1.0f)
            my_page.setAlpha(0.5f)
            main_title.text = "채팅 목록"

        }


        notice.setOnClickListener {
            supportFragmentManager.beginTransaction().replace(R.id.fragment_area, NoticeFragment()).commit()
            home.setAlpha(0.5f)
            notice.setAlpha(1.0f)
            chat.setAlpha(0.5f)
            my_page.setAlpha(0.5f)
            main_title.text = "게시판"

        }

        my_page.setOnClickListener {
            supportFragmentManager.beginTransaction().replace(R.id.fragment_area, MyFragment()).commit()
            home.setAlpha(0.5f)
            notice.setAlpha(0.5f)
            chat.setAlpha(0.5f)
            my_page.setAlpha(1.0f)
            main_title.text = "설정"
        }

    }
}

