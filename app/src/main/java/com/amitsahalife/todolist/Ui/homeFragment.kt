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
import com.amitsahalife.todolist.databinding.FragmentHomeBinding
import com.amitsahalife.todolist.databinding.FragmentSignInBinding
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class homeFragment : Fragment(), AddTodo.OnDialogNextBtnClickListener {
private lateinit var auth : FirebaseAuth
private lateinit var databaseReference: DatabaseReference
private lateinit var navControl:NavController
private lateinit var popUpFragment : AddTodo
private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init (view)
        registerEvent()

    }

    private fun registerEvent() {

        binding.addTaskBtn.setOnClickListener {


            popUpFragment = AddTodo()
            popUpFragment!!.setListener(this)
            popUpFragment.show(
                childFragmentManager,
                "AddTodo"
            )
        }
    }

    private fun init(view: View) {
        navControl = Navigation.findNavController(view)
        auth = FirebaseAuth.getInstance()
        databaseReference = FirebaseDatabase.getInstance().reference.child("Tasks")
            .child(auth.currentUser?.uid.toString())
    }

    override fun onSave(todo: String, todoEt: TextInputEditText) {
        databaseReference.push().setValue(todo).addOnCompleteListener {
            if (it.isSuccessful){
                Toast.makeText(context,"done",Toast.LENGTH_SHORT).show()
                todoEt.text = null
            }
            else
            {
                Toast.makeText(context,it.exception?.message.toString(),Toast.LENGTH_SHORT).show()
            }
            popUpFragment.dismiss()
        }
    }
}