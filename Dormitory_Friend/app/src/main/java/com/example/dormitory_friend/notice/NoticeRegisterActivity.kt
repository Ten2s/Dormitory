package com.example.dormitory_friend.notice

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.dormitory_friend.FirebaseUtils
import com.example.dormitory_friend.MainActivity
import com.example.dormitory_friend.R
import kotlinx.android.synthetic.main.activity_notice_detail.*
import kotlinx.android.synthetic.main.activity_notice_register.*
import java.time.LocalDate

class NoticeRegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notice_register)

        notice_subject.setText(intent.getStringExtra("title"))
        notice_content.setText(intent.getStringExtra("content"))

        val isModify = intent.getStringExtra(("isModify"))

        if(isModify == "1"){
        register_button.setOnClickListener{
            FirebaseUtils.db.collection("users").document(FirebaseUtils.getUid())
                .addSnapshotListener { documentSnapshot, firebaseFirestoreException ->
                    FirebaseUtils.db.collection(documentSnapshot?.get("university").toString())
                        .document(documentSnapshot?.get("sex").toString())
                        .collection("users").document(FirebaseUtils.getUid())
                        .addSnapshotListener { documentSnapshot, firebaseFirestoreException ->

                            var data = mapOf(
                                "title" to notice_subject.text.toString(),
                                "content" to notice_content.text.toString(),
                                "nickname" to documentSnapshot?.get("nickname").toString(),
                                "uid" to documentSnapshot?.get("uid").toString()
                            )
                            FirebaseUtils.db.collection("notice").document(FirebaseUtils.getUid()).update(data)

                            val intent = Intent(this, MainActivity::class.java)
                            intent.putExtra("name", "notice")
                            startActivity(intent)
                        }
                }
             }
        }

        else {
            register_button.setOnClickListener {
                FirebaseUtils.db.collection("users").document(FirebaseUtils.getUid())
                    .addSnapshotListener { documentSnapshot, firebaseFirestoreException ->
                        FirebaseUtils.db.collection(documentSnapshot?.get("university").toString())
                            .document(documentSnapshot?.get("sex").toString())
                            .collection("users").document(FirebaseUtils.getUid())
                            .addSnapshotListener { documentSnapshot, firebaseFirestoreException ->
                                val time = LocalDate.now()
                                var data = hashMapOf(
                                    "title" to notice_subject.text.toString(),
                                    "content" to notice_content.text.toString(),
                                    "nickname" to documentSnapshot?.get("nickname").toString(),
                                    "uid" to documentSnapshot?.get("uid").toString(),
                                    "time" to "$time"
                                )

                                FirebaseUtils.db.collection("notice").add(data)
                                    .addOnSuccessListener {
                                        val intent = Intent(this, MainActivity::class.java)
                                        intent.putExtra("name", "notice")
                                        startActivity(intent)
                                    }
                            }
                    }
            }
        }
    }
}
