package com.example.dormitory_friend

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.database.FirebaseDatabase



class FirebaseUtils
{
    companion object {

        var auth = FirebaseAuth.getInstance()
        val db = FirebaseFirestore.getInstance()
        val realtime = FirebaseDatabase.getInstance().getReference("chatRoom")

        fun getUid() : String
        {
            return auth.currentUser?.uid.toString()
        }
    }
}