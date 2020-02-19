package com.example.dormitory_friend.firstfragments


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.dormitory_friend.CharicteristicActivity
import com.example.dormitory_friend.FirebaseUtils
import com.example.dormitory_friend.MainActivity

import com.example.dormitory_friend.R
import com.google.firebase.firestore.DocumentSnapshot
import kotlinx.android.synthetic.main.activity_charicteristic.view.*
import kotlinx.android.synthetic.main.fragment_my.*
import kotlinx.android.synthetic.main.fragment_my.view.*

/**
 * A simple [Fragment] subclass.
 */
class MyFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_my, container, false)

        FirebaseUtils.db.collection("users").document(FirebaseUtils.getUid())
            .get().addOnSuccessListener {
                documentSnapshot ->
                FirebaseUtils.db.collection((documentSnapshot.get("university").toString())).document(documentSnapshot.get("sex").toString())
                    .collection("users").document(FirebaseUtils.getUid())
                    .get().addOnSuccessListener {
                            documentSnapshot ->
                        profile_age.setText("나이 : " + documentSnapshot.get("age").toString())
                        profile_grade.setText("학년 : " + documentSnapshot.get("grade").toString())
                        profile_country.setText(documentSnapshot.get("country").toString())
                        profile_nickname.setText(documentSnapshot.get("nickname").toString())
                        profile_major.setText(documentSnapshot.get("major").toString())

                    }
            }


        view.modify_txt.setOnClickListener {
            val intent = Intent(context, CharicteristicActivity::class.java)
            startActivity(intent)
        }

        view.logout_txt.setOnClickListener {
            FirebaseUtils.auth.signOut()
            val intent = Intent(context, MainActivity::class.java)
            startActivity(intent)
        }

        return view

    }


}
