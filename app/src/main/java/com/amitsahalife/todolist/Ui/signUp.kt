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
import com.amitsahalife.todolist.databinding.FragmentSignUpBinding
import com.amitsahalife.todolist.util.Util
import com.google.firebase.auth.FirebaseAuth
import kotlin.math.min


class signUp : Fragment() {
private lateinit var auth:FirebaseAuth
private lateinit var navControl : NavController
private lateinit var binding: FragmentSignUpBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSignUpBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init(view)
        Util.statusBarUtil(this, R.color.signup)

//        binding.signUp.setOnClickListener {
//            navControl.navigate(R.id.action_signIn_to_signUp)
//        }
        binding.nextBtn.setOnClickListener {
            Util.hapticFeedback(requireContext())
            val email = binding.emailEt.text.toString()
            val pass = binding.passEt.text.toString()
            val verfifyPass = binding.verifyPassEt.text.toString()

            if (email.isNotEmpty() && pass.isNotEmpty() && verfifyPass.isNotEmpty()) {
                if (pass == verfifyPass) {
                    registerUser(email, pass)
                }
                else{
                    Toast.makeText(context, "Password is not same", Toast.LENGTH_SHORT).show()
                }

            }
            else
                Toast.makeText(context, "Empty fields are not allowed", Toast.LENGTH_SHORT).show()
        }
    }

    private fun registerUser(email: String, pass: String) {
        binding.progressBarTwo.visibility= View.VISIBLE
        auth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener {

            if (it.isSuccessful){
                navControl.navigate(R.id.action_signUp_to_homeFragment)
            }
            else{
               // val a : String = it.exception.toString()
                val MAX_LENGTH = 100

                val errorM = it.exception?.message?.substring(0, min(it.exception?.message?.length?:0, MAX_LENGTH))

                Toast.makeText(context, it.exception?.localizedMessage, Toast.LENGTH_LONG).show()
            }
            binding.progressBarTwo.visibility= View.GONE
        }

    }

    private fun init(view: View) {
        navControl = Navigation.findNavController(view)
        auth = FirebaseAuth.getInstance()

    }

}