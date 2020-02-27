package com.example.dormitory_friend.notice

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.dormitory_friend.FirebaseUtils
import com.example.dormitory_friend.MainActivity
import com.example.dormitory_friend.R
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.activity_notice_register.*
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class NoticeRegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notice_register)



        val isModify = intent.getStringExtra("isModify")
        val position = intent.getIntExtra("position", 0)

        if(isModify == "1"){
            notice_subject.setText(intent.getStringExtra("title"))
            notice_content.setText(intent.getStringExtra("content"))

            register_button.setText("수정하기")

        register_button.setOnClickListener{
            FirebaseUtils.db.collection("notice").orderBy("time", Query.Direction.DESCENDING)
                .get().addOnSuccessListener {
                        querySnapshot ->

                    val data = mapOf(
                        "title" to notice_subject.text.toString(),
                        "content" to notice_content.text.toString()
                    )

                    val id = querySnapshot.documents.get(position).id

                    FirebaseUtils.db.collection("notice").document(id).update(data)
                        .addOnSuccessListener {
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
                                val date = LocalDateTime.now()
                                val formatter = DateTimeFormatter.ofPattern("MM/dd hh:mm:ss a")
                                val formatted = date.format(formatter)
                                var data = hashMapOf(
                                    "title" to notice_subject.text.toString(),
                                    "content" to notice_content.text.toString(),
                                    "nickname" to documentSnapshot?.get("nickname").toString(),
                                    "uid" to documentSnapshot?.get("uid").toString(),
                                    "time" to formatted
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
