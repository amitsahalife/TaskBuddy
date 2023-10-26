package com.amitsahalife.todolist.Ui

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.amitsahalife.todolist.R
import com.google.firebase.auth.FirebaseAuth


class splash : Fragment() {

    private lateinit var mAuth: FirebaseAuth
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init(view)
        Handler(Looper.myLooper()!!).postDelayed(Runnable {
if (mAuth.currentUser!=null){
navController.navigate(R.id.action_splash_to_homeFragment)
}
            else{
navController.navigate(R.id.action_splash_to_signIn)
            }
        },2000)
    }
    private fun init(view: View) {
        mAuth = FirebaseAuth.getInstance()
        navController = Navigation.findNavController(view)
    }
}

