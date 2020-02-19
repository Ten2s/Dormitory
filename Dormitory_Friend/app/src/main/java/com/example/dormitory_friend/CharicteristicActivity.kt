package com.example.dormitory_friend

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_charicteristic.*

class CharicteristicActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_charicteristic)
        val Ref = FirebaseUtils.db.collection("users").document(FirebaseUtils.getUid())
       var checked_sex = ""


        sex_group.setOnCheckedChangeListener { group, checkedId ->
            if(checkedId == R.id.woman)
            {
                checked_sex = "woman"
            }
            else{
                checked_sex = "man"
            }
        }



        save_button.setOnClickListener {
            //기존 데이터 삭제
            Ref.get().addOnSuccessListener {
                FirebaseUtils.db.collection(it.get("university").toString())
                    .document(it.get("sex").toString()).collection("users")
                    .document(FirebaseUtils.getUid()).delete()
            }

            //새로운 데이터 추가
            val user_info = hashMapOf(
                "nickname" to nickname_area.text.toString(),
                "name" to name_area.text.toString(),
                "age" to age_area.text.toString(),
                "grade" to grade_area.text.toString(),
                "major" to major_area.text.toString(),
                "country" to country_area.text.toString()
            )

            val user = hashMapOf(
                "university" to university_area.text.toString(),
                "sex" to checked_sex
            )

            Ref.set(user)

            FirebaseUtils.db.collection(university_area.text.toString()).document(checked_sex).collection("users")
                .document(FirebaseUtils.getUid()).set(user_info)
                .addOnSuccessListener {
                    Log.e("CharicteristicActivity", "성공")
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                }
                .addOnFailureListener { Log.e("CharicteristicActivity", "실패") }
        }
    }
}
