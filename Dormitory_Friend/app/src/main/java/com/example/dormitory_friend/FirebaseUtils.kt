package com.example.dormitory_friend

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot


class FirebaseUtils
{
    companion object {

        var auth = FirebaseAuth.getInstance()
        val db = FirebaseFirestore.getInstance()
        lateinit var querySnapshot : QuerySnapshot
        val realtime = FirebaseDatabase.getInstance().reference.child("chatRoom")


        fun getUid() : String
        {
            return auth.currentUser?.uid.toString()
        }

    }
}