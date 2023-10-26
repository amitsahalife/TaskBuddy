package com.amitsahalife.todolist.Ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.amitsahalife.todolist.R
import com.amitsahalife.todolist.databinding.FragmentSignInBinding
import com.amitsahalife.todolist.databinding.FragmentSignUpBinding
import com.google.firebase.auth.FirebaseAuth
import kotlin.math.min


class signIn : Fragment() {

    private lateinit var auth: FirebaseAuth
    private lateinit var navControl : NavController
    private lateinit var binding: FragmentSignInBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSignInBinding.inflate(inflater,container,false)
       return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        init(view)
        binding.textViewSignUp.setOnClickListener {
            navControl.navigate(R.id.action_signIn_to_signUp)
        }

        binding.nextBtn.setOnClickListener {
            val email = binding.emailEt.text.toString()
            val pass = binding.passEt.text.toString()

            if (email.isNotEmpty() && pass.isNotEmpty()) {
                loginUser(email, pass)
            }
            else{
                Toast.makeText(context, "Empty fields are not allowed", Toast.LENGTH_SHORT).show()
            }

        }
        
    }

    private fun loginUser(email: String, pass: String) {

auth.signInWithEmailAndPassword(email,pass).addOnCompleteListener {
    if (it.isSuccessful) {
        navControl.navigate(R.id.action_signIn_to_homeFragment)
    } else {
        val MAX_LENGTH = 100

        val errorM = it.exception?.message?.substring(0, min(it.exception?.message?.length?:0, MAX_LENGTH))

        Toast.makeText(context, errorM, Toast.LENGTH_LONG).show()
    }
}
    }

    private fun init(view: View) {
        navControl = Navigation.findNavController(view)
        auth = FirebaseAuth.getInstance()
    }
}