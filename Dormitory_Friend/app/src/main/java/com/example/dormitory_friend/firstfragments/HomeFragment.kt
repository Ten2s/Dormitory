package com.example.dormitory_friend.firstfragments


import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.example.dormitory_friend.FirebaseUtils
import com.example.dormitory_friend.R
import com.example.dormitory_friend.chat.ChatRoomActivity
import com.example.dormitory_friend.firstfragments.adapters.FirstFragmentAdapter
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.android.synthetic.main.forchatbottomdialog.*
import kotlinx.android.synthetic.main.fragment_home.view.*

/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : Fragment() {

    var dataArray = ArrayList<UserModel>()
    lateinit var adapter : BaseAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        val Ref = FirebaseUtils.db.collection("users").document(FirebaseUtils.getUid())
        val intent = Intent(requireContext(), ChatRoomActivity::class.java)

        Ref.addSnapshotListener { documentSnapshot, firebaseFirestoreException ->
            val university = documentSnapshot?.get("university").toString()
            val sex = documentSnapshot?.get("sex").toString()
            FirebaseUtils.db.collection(university)
                .document(sex).collection("users")
                .addSnapshotListener { querySnapshot, firebaseFirestoreException ->
                    if (firebaseFirestoreException != null) {
                        Log.w(TAG, "Listen failed.", firebaseFirestoreException)
                        return@addSnapshotListener
                    }

                    dataArray.clear()
                    for(document in querySnapshot!!)
                    {
                        val data = UserModel(document.get("uid").toString(),
                            document.get("nickname").toString(),
                            document.get("age").toString(),
                            document.get("major").toString(),
                            document.get("country").toString(),
                            document.get("grade").toString())

                        dataArray.add(data)
                    }
                    adapter.notifyDataSetChanged()
                }
        }

        adapter = FirstFragmentAdapter(requireContext(), dataArray)
        view.list_FirstFragment.adapter = adapter

        view.list_FirstFragment.setOnItemClickListener { parent, view, position, id ->
            val dialog = BottomSheetDialog(requireContext())
            dialog.setContentView(R.layout.forchatbottomdialog)
            dialog.show()
            dialog.startchat.setOnClickListener {
                intent.putExtra("username", dataArray[position].nickname)
                intent.putExtra("uid", dataArray[position].uid)
                startActivity(intent)
            }
        }

        return view
    }


}
