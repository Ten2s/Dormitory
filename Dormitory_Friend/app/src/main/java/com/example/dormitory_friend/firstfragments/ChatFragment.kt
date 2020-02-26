package com.example.dormitory_friend.firstfragments


import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.dormitory_friend.FirebaseUtils
import com.example.dormitory_friend.MainActivity

import com.example.dormitory_friend.R
import com.example.dormitory_friend.chat.ChatRoomActivity
import com.example.dormitory_friend.chat.chatData
import com.example.dormitory_friend.firstfragments.adapters.ChatFragmentAdapter
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_chat_room.*
import kotlinx.android.synthetic.main.fragment_chat.*
import kotlinx.android.synthetic.main.fragment_chat.view.*
import kotlinx.android.synthetic.main.fragment_chat.view.chatroomlist
import java.util.*
import kotlin.Comparator
import kotlin.collections.ArrayList

/**
 * A simple [Fragment] subclass.
 */
class ChatFragment : Fragment() {

    var flag = true
    var chatRoomList = ArrayList<chatData>()
    var myRef = FirebaseUtils.realtime.child(FirebaseUtils.getUid())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view = inflater.inflate(R.layout.fragment_chat, container, false)
        val adapter = ChatFragmentAdapter(requireContext(), chatRoomList)

        val valueEventListener = object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
            override fun onDataChange(p0: DataSnapshot) {
                chatRoomList.clear()
                for (data in p0.children) {
                    val id = data.key.toString()
                    val room = myRef.child(id).limitToLast(1)
                    val valueEventListner = object : ValueEventListener {
                        override fun onCancelled(p0: DatabaseError) {
                            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                        }
                        override fun onDataChange(p0: DataSnapshot) {
                            for (messagenode in p0.children) {

                                FirebaseUtils.db.collection("users").document(id)
                                    .addSnapshotListener { documentSnapshot, firebaseFirestoreException ->
                                        val message = messagenode.child("message").getValue().toString()
                                        val time = messagenode.child("timestamp").getValue().toString()
                                        val messagemodel = chatData(
                                            documentSnapshot!!.get("nickname").toString(),
                                            id,
                                            message,
                                            time
                                            )
                                        chatRoomList.add(messagemodel)
                                        chatRoomList.sortBy { it.timestamp }
                                        adapter.notifyDataSetChanged()
                                    }
                            }
                        }
                    }
                    room.addListenerForSingleValueEvent(valueEventListner)
                }
            }
        }


        myRef.addListenerForSingleValueEvent(valueEventListener)
        view.chatroomlist.adapter = adapter

        view.chatroomlist.setOnItemClickListener { parent, view, position, id ->
            val intent = Intent(requireContext(), ChatRoomActivity::class.java)
            intent.putExtra("uid", chatRoomList[position].useruid)
            intent.putExtra("username", chatRoomList[position].username)
            startActivity(intent)
        }
        return view
    }


}
