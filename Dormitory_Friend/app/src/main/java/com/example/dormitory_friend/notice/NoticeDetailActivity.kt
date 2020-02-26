package com.example.dormitory_friend.notice

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.BaseAdapter
import androidx.core.view.isInvisible
import com.example.dormitory_friend.FirebaseUtils
import com.example.dormitory_friend.MainActivity
import com.example.dormitory_friend.R
import com.example.dormitory_friend.notice.adapters.NoticeCommentAdapter
import kotlinx.android.synthetic.main.activity_notice_detail.*
import kotlinx.android.synthetic.main.activity_notice_register.*
import kotlinx.android.synthetic.main.listview_noticecomment.*
import kotlinx.android.synthetic.main.undertab.*

class NoticeDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notice_detail)

        var commentArray = ArrayList<CommentModel>()
        lateinit var adapter : BaseAdapter
        val title = intent.getStringExtra("title")
        val content = intent.getStringExtra("content")
        val modifyFlag = "1"

        //위치값 받아옴  NoticeFragment에서 intent로 전달해주는거임 ㅋ
        val position = intent.getIntExtra("position", 0)
        notice_detail_subject.setText(title)
        notice_detail_content.setText(content)

        val registerName = "작성자: "+ intent.getStringExtra("nickname")
        val time = intent.getStringExtra("time")

        register_name.setText(registerName)
        register_time.setText(time)

        if(intent.getStringExtra("uid")==FirebaseUtils.getUid()){
            register_modify.visibility = View.VISIBLE
            register_delete.visibility = View.VISIBLE
        }

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
        //수정 버튼 클릭
        register_modify.setOnClickListener{
            val intent = Intent(this, NoticeRegisterActivity::class.java)
            intent.putExtra("title", title )
            intent.putExtra("content", content)
            intent.putExtra("isModify", modifyFlag)
            startActivity(intent)
        }

        //삭제 버튼 클릭
        register_delete.setOnClickListener{
            FirebaseUtils.db.collection("notice").get().addOnSuccessListener {
                querySnapshot ->

                val id = querySnapshot.documents.get(position).id
                FirebaseUtils.db.collection("notice").document(id).delete()

                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("name","notice")

                startActivity(intent)

            }
        }


//        //답글 작성 버튼 클릭
//        comment_reply.setOnClickListener{
//
//        }
    }
}
