package com.example.dormitory_friend.notice

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.BaseAdapter
import com.example.dormitory_friend.FirebaseUtils
import com.example.dormitory_friend.R
import com.example.dormitory_friend.notice.adapters.NoticeCommentAdapter
import kotlinx.android.synthetic.main.activity_notice_detail.*

class NoticeDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notice_detail)

        var commentArray = ArrayList<CommentModel>()
        lateinit var adapter : BaseAdapter

        //위치값 받아옴  NoticeFragment에서 intent로 전달해주는거임 ㅋ
        val position = intent.getIntExtra("position", 0)
        notice_detail_subject.setText(intent.getStringExtra("title"))
        notice_detail_content.setText(intent.getStringExtra("content"))

        FirebaseUtils.db.collection("notice").addSnapshotListener {
                querySnapshot, firebaseFirestoreException ->
            val id = querySnapshot!!.documents.get(position).id

            FirebaseUtils.db.collection("notice").document(id).collection("comment")
                .addSnapshotListener { querySnapshot, firebaseFirestoreException ->
                    if(firebaseFirestoreException != null)
                    {
                        Log.w(ContentValues.TAG, "Listen failed.", firebaseFirestoreException)
                        return@addSnapshotListener
                    }

                    commentArray.clear()

                    for(document in querySnapshot!!)
                    {
                        val data = CommentModel(document.get("nickname").toString(),
                            document.get("comment").toString())

                        commentArray.add(data)
                    }

                    adapter.notifyDataSetChanged()
                }
        }

        adapter = NoticeCommentAdapter(this, commentArray)
        comment_list.adapter = adapter

        register_commit_button.setOnClickListener{
            FirebaseUtils.db.collection("notice").get().addOnSuccessListener {
                querySnapshot ->

                val id = querySnapshot.documents.get(position).id
                var data : HashMap<String, String>

                FirebaseUtils.db.collection("users").document(FirebaseUtils.getUid()).get()
                    .addOnSuccessListener {
                        FirebaseUtils.db.collection(it.get("university").toString())
                            .document(it.get("sex").toString()).collection("users")
                            .document(FirebaseUtils.getUid()).get()
                            .addOnSuccessListener {
                                documentSnapshot ->

                                //데이터 만들고
                                data = hashMapOf(
                                    "nickname" to documentSnapshot.get("nickname").toString(),
                                    "comment" to comment.text.toString()
                                )


                                //comment라는 컬랙션 생
                                FirebaseUtils.db.collection("notice").document(id)
                                    .collection("comment")
                                    .add(data)

                                comment.setText("")
                            }
                    }

            }
        }


    }
}
