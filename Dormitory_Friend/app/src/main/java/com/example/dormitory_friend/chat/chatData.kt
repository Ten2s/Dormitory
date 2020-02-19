package com.example.dormitory_friend.chat

class chatData(val username : String, val message : String, val timeStamp : String) {
    var userName: String = username
    var userMessage: String = message
    var time : String = timeStamp

    fun getName() : String
    {
        return userName
    }

    fun getTimestamp() : String
    {
        return this.time
    }
    fun getMsg() : String
    {
        return userMessage
    }

    fun setMsg(message : String)
    {
        this.userMessage = message
    }

    fun setName(name : String)
    {
        this.userName = name
    }

    fun setTimeStamp(time : String)
    {
        this.time = time
    }

}