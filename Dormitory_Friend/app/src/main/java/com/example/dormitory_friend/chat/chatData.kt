package com.example.dormitory_friend.chat

class chatData() {
    lateinit var userName: String
    lateinit var uid : String
    lateinit var userMessage: String

    fun getName() : String
    {
        return userName
    }
    fun getUId() : String
    {
        return uid
    }

    fun getMsg() : String
    {
        return userMessage
    }

    fun setUId(uid : String)
    {
        this.uid = uid
    }
    fun setMsg(message : String)
    {
        this.userMessage = message
    }

    fun setName(name : String)
    {
        this.userName = name
    }


}