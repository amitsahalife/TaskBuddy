package com.amitsahalife.todolist.Ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.amitsahalife.todolist.R
import com.amitsahalife.todolist.databinding.FragmentScreenOneBinding
import com.google.firebase.auth.FirebaseAuth




class screenOneFragment : Fragment() {

    private lateinit var navController: NavController
    private lateinit var binding: FragmentScreenOneBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentScreenOneBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
init(view) // most importent linee
        binding.screenOneNext.setOnClickListener {
            navController.navigate(R.id.action_screenOneFragment_to_screenTwoFragment)
        }
    }

    private fun init(view: View) {
        navController = Navigation.findNavController(view)

    }

}