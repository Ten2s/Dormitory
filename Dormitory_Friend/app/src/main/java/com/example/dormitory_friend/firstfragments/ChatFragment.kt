package com.example.dormitory_friend.firstfragments


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.dormitory_friend.R
import com.example.dormitory_friend.chat.ChatRoomActivity
import kotlinx.android.synthetic.main.fragment_chat.view.*

/**
 * A simple [Fragment] subclass.
 */
class ChatFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view = inflater.inflate(R.layout.fragment_chat, container, false)

        view.test.setOnClickListener{
            val intent = Intent(requireContext(), ChatRoomActivity::class.java)
            startActivity(intent)
        }


        return view
    }


}
