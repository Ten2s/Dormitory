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

            FirebaseUtils.db.collection("users").document(FirebaseUtils.getUid()).get().addOnSuccessListener {
                FirebaseUtils.db.collection(it.get("university").toString()).document(it.get("sex").toString())
                    .collection("users").document(FirebaseUtils.getUid()).get()
                    .addOnSuccessListener {
                        document ->
                        var data = hashMapOf(
                            "title" to notice_subject.text.toString(),
                            "content" to notice_content.text.toString(),
                            "nickname" to document.get("nickname").toString()
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
