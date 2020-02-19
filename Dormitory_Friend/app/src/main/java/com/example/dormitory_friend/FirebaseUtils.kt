package com.example.dormitory_friend

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase



class FirebaseUtils
{
    companion object {

        var auth = FirebaseAuth.getInstance()
        var db = FirebaseFirestore.getInstance()
        private val firebaseDatabase = FirebaseDatabase.getInstance()
        private val databaseReference = firebaseDatabase.reference

        fun getUid() : String
        {
            return auth.currentUser?.uid.toString()
        }
    }
}