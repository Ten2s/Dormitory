package com.example.dormitory_friend.chat

class chatData(val username : String, val message : String) {
    var userName: String = username
    var userMessage: String = message


    fun getName() : String
    {
        return userName
    }

    fun getMsg() : String
    {
        return userMessage
    }

}