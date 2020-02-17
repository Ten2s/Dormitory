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

        var checked_sex : String = "man"
        sex_group.setOnCheckedChangeListener { group, checkedId ->
            if(checkedId == R.id.woman)
            {
                checked_sex = "woman"
            }
            else{
                checked_sex = "man"
            }
        }
        val docRef = FirebaseUtils.db.collection("users").document(FirebaseUtils.getUid())

        var changed_sex: String = "null"


        if(docRef != null) {
            docRef.get().addOnSuccessListener { documentSnapshot ->
                changed_sex = documentSnapshot.get("sex").toString()
                name_area.setText(documentSnapshot.get("name").toString())
                age_area.setText(documentSnapshot.get("age").toString())
                grade_area.setText(documentSnapshot.get("grade").toString())
                major_area.setText(documentSnapshot.get("major").toString())
                country_area.setText(documentSnapshot.get("country").toString())
                nickname_area.setText(documentSnapshot.get("nickname").toString())
                university_area.setText(documentSnapshot.get("university").toString())
                if(documentSnapshot.get("sex").toString() == "man")
                {
                    man.isChecked = true
                    woman.isChecked = false
                }
                else{
                    woman.isChecked = true
                    man.isChecked = false
                }
            }
        }

        save_button.setOnClickListener {

            if(FirebaseUtils.db.collection(university_area.text.toString()).document(changed_sex).collection("users")
                    .document(FirebaseUtils.getUid()) != null) {
                FirebaseUtils.db.collection(university_area.text.toString()).document(changed_sex)
                    .collection("users")
                    .document(FirebaseUtils.getUid()).delete()
            }

            val user = hashMapOf(
                "nickname" to nickname_area.text.toString(),
                "name" to name_area.text.toString(),
                "age" to age_area.text.toString(),
                "grade" to grade_area.text.toString(),
                "major" to major_area.text.toString(),
                "country" to country_area.text.toString(),
                "university" to university_area.text.toString(),
                "sex" to checked_sex
            )

            FirebaseUtils.db.collection("users").document(FirebaseUtils.getUid()).set(user)

            FirebaseUtils.db.collection(university_area.text.toString()).document(checked_sex).collection("users")
                .document(FirebaseUtils.getUid()).set(user)
                .addOnSuccessListener {
                    Log.e("CharicteristicActivity", "성공")
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                }
                .addOnFailureListener { Log.e("CharicteristicActivity", "실패") }
        }
    }
}
