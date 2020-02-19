package com.example.dormitory_friend

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_joinin.*

class JoininActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_joinin)


        joinin_button.setOnClickListener {
            FirebaseUtils.auth.createUserWithEmailAndPassword(join_email.text.toString(), join_password.text.toString()).addOnCompleteListener(this){
                    task ->

                if(task.isSuccessful)
                {
                    val intent = Intent(this, CharicteristicActivity::class.java)
                    startActivity(intent)
                }
                else
                {
                    Toast.makeText(this, "fail", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}
