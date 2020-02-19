package com.example.dormitory_friend.firstfragments


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.dormitory_friend.FirebaseUtils

import com.example.dormitory_friend.R
import com.example.dormitory_friend.notice.NoticeRegisterActivity
import com.example.dormitory_friend.firstfragments.adapters.NoticeFragmentAdapter
import kotlinx.android.synthetic.main.fragment_notice.view.*

/**
 * A simple [Fragment] subclass.
 */
class NoticeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view : View = inflater.inflate(R.layout.fragment_notice, container, false)

        FirebaseUtils.db.collection("notice").get().addOnSuccessListener {
            val querysnapshot = it
            val adapter =
                NoticeFragmentAdapter(
                    requireContext(),
                    querysnapshot
                )
            view.list_notice.adapter = adapter
        }
        view.button_register_notice.setOnClickListener{

            val intent = Intent(requireContext(), NoticeRegisterActivity::class.java)
            startActivity(intent)

        }




        return view

    }


}
