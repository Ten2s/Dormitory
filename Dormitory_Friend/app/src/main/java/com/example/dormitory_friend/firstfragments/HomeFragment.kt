package com.example.dormitory_friend.firstfragments


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.example.dormitory_friend.FirebaseUtils
import com.example.dormitory_friend.MainActivity
import com.example.dormitory_friend.R
import com.example.dormitory_friend.chat.ChatRoomActivity
import com.example.dormitory_friend.chat.ChatRoomAdapter
import com.example.dormitory_friend.firstfragments.adapters.FirstFragmentAdapter
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.android.synthetic.main.forchatbottomdialog.*
import kotlinx.android.synthetic.main.fragment_home.view.*

/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        val Ref = FirebaseUtils.db.collection("users").document(FirebaseUtils.getUid())
        lateinit var adapter : BaseAdapter
        lateinit var dataArray : QuerySnapshot

        Ref.get().addOnSuccessListener {
                documentSnapshot ->
            FirebaseUtils.db.collection(documentSnapshot.get("university").toString())
                .document(documentSnapshot.get("sex").toString())
                .collection("users").get()
                .addOnSuccessListener {
                        querySnapshot ->
                    dataArray = querySnapshot
                    adapter =
                        FirstFragmentAdapter(
                            requireContext(),
                            dataArray
                        )

                    view.list_FirstFragment.adapter = adapter
                }
        }

        view.list_FirstFragment.setOnItemClickListener { parent, view, position, id ->
            val dialog = BottomSheetDialog(requireContext())
            dialog.setContentView(R.layout.forchatbottomdialog)
            dialog.show()
            dialog.startchat.setOnClickListener {
                val intent = Intent(requireContext(), ChatRoomActivity::class.java)
                intent.putExtra("username", dataArray.documents.get(position).get("nickname").toString())
                intent.putExtra("uid", dataArray.documents.get(position).get("uid").toString())
                startActivity(intent)
            }
        }


        return view
    }


}
