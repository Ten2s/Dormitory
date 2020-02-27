package com.example.dormitory_friend

import android.widget.BaseAdapter
import com.example.dormitory_friend.chat.chatData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage


class FirebaseUtils
{
    companion object {
        var auth = FirebaseAuth.getInstance()
        val db = FirebaseFirestore.getInstance()
        val realtime = FirebaseDatabase.getInstance().reference.child("chatRoom")
        val firestorage = FirebaseStorage.getInstance().reference

        fun getUid(): String {
            return auth.currentUser?.uid.toString()
        }

    }
}