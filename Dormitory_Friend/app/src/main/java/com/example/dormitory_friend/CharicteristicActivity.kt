package com.example.dormitory_friend

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_charicteristic.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

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

        Ref.get().addOnSuccessListener {
            if(it != null){
                university_area.setText(it.get("university").toString())
                if(it.get("sex").toString().equals("man")){
                    man.isChecked = true
                }
                else{
                    woman.isChecked = true
                }
                FirebaseUtils.db.collection(it.get("university").toString())
                    .document(it.get("sex").toString()).collection("users")
                    .document(FirebaseUtils.getUid()).get().addOnSuccessListener { document ->
                        major_area.setText(document.get("major").toString())
                        grade_area.setText(document.get("grade").toString())
                        nickname_area.setText(document.get("nickname").toString())
                        name_area.setText(document.get("name").toString())
                        age_area.setText(document.get("age").toString())
                        country_area.setText(document.get("country").toString())
                    }
            }
        }
        save_button.setOnClickListener {
            //기존 데이터 삭제
            Ref.get().addOnSuccessListener {
                FirebaseUtils.db.collection(it.get("university").toString())
                    .document(it.get("sex").toString()).collection("users")
                    .document(FirebaseUtils.getUid()).delete()

                //새로운 데이터 추가
                val user_info = hashMapOf(
                    "nickname" to nickname_area.text.toString(),
                    "name" to name_area.text.toString(),
                    "age" to age_area.text.toString(),
                    "grade" to grade_area.text.toString(),
                    "major" to major_area.text.toString(),
                    "country" to country_area.text.toString(),
                    "uid" to FirebaseUtils.getUid()
                )

                val user = hashMapOf(
                    "university" to university_area.text.toString(),
                    "sex" to checked_sex
                )

                Ref.set(user)

                Ref.get().addOnSuccessListener {
                    FirebaseUtils.db.collection(it.get("university").toString()).document(it.get("sex").toString()).collection("users")
                        .document(FirebaseUtils.getUid()).set(user_info)
                        .addOnSuccessListener {
                            val intent = Intent(this, PropensityActivity1::class.java)
                            startActivity(intent)
                        }
                }
            }
        }
    }
}
