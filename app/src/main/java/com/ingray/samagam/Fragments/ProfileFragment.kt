package com.ingray.samagam.Fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.protobuf.Value
import com.ingray.samagam.DataClass.Users
import com.ingray.samagam.R
import de.hdodenhof.circleimageview.CircleImageView

class ProfileFragment : Fragment() {
    private lateinit var profileImage : CircleImageView
    private lateinit var profileName : TextView
    private lateinit var profileBranch:TextView
    var deRef = FirebaseDatabase.getInstance().getReference("Users")
    private lateinit var view :View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
       view = inflater.inflate(R.layout.fragment_profile, container, false)
        callById()
        deRef.child(FirebaseAuth.getInstance().currentUser?.uid.toString()).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
             var us : Users = Users()
                if(snapshot.exists()){
                    us = snapshot.getValue(Users::class.java)!!
                    profileName.setText(us.name)
                    Glide.with(view.context).load(us.purl).into(profileImage)
                    profileBranch.setText(us.username)
                }

            }

                override fun onCancelled(error: DatabaseError) {
            // Handle errors here
            Log.e("Firebase", "Error fetching events: ${error.message}")
        }
    })




            return view

    }

    private fun callById() {
        profileImage = view.findViewById(R.id.profileImage)
        profileName = view.findViewById(R.id.profileName)
        profileBranch = view.findViewById(R.id.profileBranch)
    }


}