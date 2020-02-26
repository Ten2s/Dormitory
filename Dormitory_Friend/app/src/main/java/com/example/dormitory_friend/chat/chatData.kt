package com.example.dormitory_friend.chat

import com.google.firebase.firestore.IgnoreExtraProperties


@IgnoreExtraProperties
data class chatData(
    val username : String = "",
    val useruid : String = "",
    val message : String ="",
    val timestamp : String = ""
)
