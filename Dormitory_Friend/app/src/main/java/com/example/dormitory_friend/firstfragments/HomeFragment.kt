package com.example.dormitory_friend.firstfragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.dormitory_friend.FirebaseUtils
import com.example.dormitory_friend.R
import com.example.dormitory_friend.firstfragments.adapters.FirstFragmentAdapter
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

        Ref.get().addOnSuccessListener {
                documentSnapshot ->
            FirebaseUtils.db.collection(documentSnapshot.get("university").toString())
                .document(documentSnapshot.get("sex").toString())
                .collection("users").get()
                .addOnSuccessListener {
                        querySnapshot ->
                    val adapter =
                        FirstFragmentAdapter(
                            requireContext(),
                            querySnapshot
                        )
                    view.list_FirstFragment.adapter = adapter
                }
        }


        return view
    }


}
