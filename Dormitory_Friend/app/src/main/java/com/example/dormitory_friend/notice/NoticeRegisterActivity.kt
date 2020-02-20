package com.example.dormitory_friend.notice

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.dormitory_friend.FirebaseUtils
import com.example.dormitory_friend.MainActivity
import com.example.dormitory_friend.R
import kotlinx.android.synthetic.main.activity_notice_register.*

class NoticeRegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notice_register)


        register_button.setOnClickListener{

            FirebaseUtils.db.collection("users").document(FirebaseUtils.getUid())
                .addSnapshotListener { documentSnapshot, firebaseFirestoreException ->
                FirebaseUtils.db.collection(documentSnapshot?.get("university").toString())
                    .document(documentSnapshot?.get("sex").toString())
                    .collection("users").document(FirebaseUtils.getUid())
                    .addSnapshotListener { documentSnapshot, firebaseFirestoreException ->
                        var data = hashMapOf(
                            "title" to notice_subject.text.toString(),
                            "content" to notice_content.text.toString(),
                            "nickname" to documentSnapshot?.get("nickname").toString()
                        )
                        FirebaseUtils.db.collection("notice").add(data).addOnSuccessListener {
                            val intent = Intent(this, MainActivity::class.java)
                            intent.putExtra("name", "notice")
                            startActivity(intent)
                        }
                    }
            }
        }
    }
}
