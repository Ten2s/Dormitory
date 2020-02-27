package com.example.dormitory_friend.firstfragments


import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.dormitory_friend.*
import com.github.dhaval2404.imagepicker.ImagePicker
import kotlinx.android.synthetic.main.fragment_my.*
import kotlinx.android.synthetic.main.fragment_my.view.*
import java.io.FileInputStream

/**
 * A simple [Fragment] subclass.
 */
class MyFragment : Fragment() {

    val profileRef = FirebaseUtils.firestorage.child("profileImg").child(FirebaseUtils.getUid())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_my, container, false)

        FirebaseUtils.db.collection("users").document(FirebaseUtils.getUid())
            .addSnapshotListener { documentSnapshot, firebaseFirestoreException ->
                FirebaseUtils.db.collection((documentSnapshot?.get("university").toString())).document(documentSnapshot?.get("sex").toString())
                    .collection("users").document(FirebaseUtils.getUid())
                    .addSnapshotListener { documentSnapshot, firebaseFirestoreException ->
                        profile_age.setText("나이 : " + documentSnapshot?.get("age").toString())
                        profile_grade.setText("학년 : " + documentSnapshot?.get("grade").toString())
                        profile_country.setText(documentSnapshot?.get("country").toString())
                        profile_nickname.setText(documentSnapshot?.get("nickname").toString())
                        profile_major.setText(documentSnapshot?.get("major").toString())
                    }
            }



        //change profile image
        view.profile_img.setOnClickListener{
            ImagePicker.with(this)
                .galleryOnly()
                .crop(1f, 1f)               //Crop Square image(Optional)
                .compress(1024)         //Final image size will be less than 1 MB(Optional)
                .maxResultSize(1080, 1080)  //Final image resolution will be less than 1080 x 1080(Optional)
                .start()
        }


        //modify info
        view.modify_txt.setOnClickListener {
            val intent = Intent(context, CharicteristicActivity::class.java)
            startActivity(intent)
        }

        //log out
        view.logout_txt.setOnClickListener {
            FirebaseUtils.auth.signOut()
            val intent = Intent(context, MainActivity::class.java)
            startActivity(intent)
        }

        //고객센터
        view.serveice_txt.setOnClickListener {
            val intent = Intent(context, CustomerServiceActivity::class.java)
            startActivity(intent)
        }

        return view

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            //Image Uri will not be null for RESULT_OK
            val fileUri = data?.data
            val file = ImagePicker.getFile(data)
            val uploadTask = profileRef.putStream(FileInputStream(file))
            uploadTask.addOnSuccessListener {
                Glide.with(this).load(profileRef).into(this.profile_img)
                Toast.makeText(requireContext(), "Success", Toast.LENGTH_SHORT).show()
            }

        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            Toast.makeText(requireContext(), ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(requireContext(), "Task Cancelled", Toast.LENGTH_SHORT).show()
        }
    }
}
