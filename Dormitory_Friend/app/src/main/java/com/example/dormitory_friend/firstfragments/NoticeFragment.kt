package com.example.dormitory_friend.firstfragments


import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.dormitory_friend.FirebaseUtils

import com.example.dormitory_friend.R
import com.example.dormitory_friend.notice.NoticeRegisterActivity
import com.example.dormitory_friend.firstfragments.adapters.NoticeFragmentAdapter
import com.example.dormitory_friend.notice.NoticeDetailActivity
import kotlinx.android.synthetic.main.fragment_notice.view.*
import kotlinx.android.synthetic.main.undertab.*

/**
 * A simple [Fragment] subclass.
 */
class NoticeFragment : Fragment() {

    var noticeArray = ArrayList<NoticeListModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view : View = inflater.inflate(R.layout.fragment_notice, container, false)
        val adapter = NoticeFragmentAdapter(requireContext(), noticeArray)


        //새로운 글 등록될 시 뷰업데이트
        FirebaseUtils.db.collection("notice").addSnapshotListener {
                querySnapshot, firebaseFirestoreException ->
            if(firebaseFirestoreException != null){
                Log.w(ContentValues.TAG, "Listen failed.", firebaseFirestoreException)
                return@addSnapshotListener
            }

            noticeArray.clear()

            for(document in querySnapshot!!)
            {
                document.reference.collection("comment").addSnapshotListener {
                        querySnapshot, firebaseFirestoreException ->

                    if(firebaseFirestoreException != null){
                        Log.w(ContentValues.TAG, "Listen failed.", firebaseFirestoreException)
                        return@addSnapshotListener
                    }
                    val commentcount = querySnapshot!!.size().toString()
                    val data = NoticeListModel(document.get("content").toString(),
                        document.get("title").toString(),
                        document.get("nickname").toString(),
                        commentcount)
                    noticeArray.add(data)
                    //리스트 변화감지
                    adapter.notifyDataSetChanged()
                }
            }

        }
        //어댑터 설정
        view.list_notice.adapter = adapter



        //등록하기 버튼 누를 시 등록화면 전환
        view.button_register_notice.setOnClickListener{
            val intent = Intent(requireContext(), NoticeRegisterActivity::class.java)
            startActivity(intent)
        }

        //게시판 작성된 목록 클릭 시 notice_detail로 넘어감
        view.list_notice.setOnItemClickListener { parent, view, position, id ->
            FirebaseUtils.db.collection("notice").get()
                .addOnSuccessListener {
                    querySnapshot ->
                    val intent = Intent(requireContext(), NoticeDetailActivity::class.java)

                    intent.putExtra("content", querySnapshot.documents.get(position).get("content").toString())
                    intent.putExtra("nickname", querySnapshot.documents.get(position).get("nickname").toString())
                    intent.putExtra("title",querySnapshot.documents.get(position).get("title").toString())
                    intent.putExtra("position", position)
                    
                    startActivity(intent)

                }
        }






        return view
    }


}
