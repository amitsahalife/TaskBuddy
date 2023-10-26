package com.amitsahalife.todolist.Ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.amitsahalife.todolist.R
import com.amitsahalife.todolist.databinding.FragmentAddTodoBinding
import com.google.android.material.textfield.TextInputEditText


class AddTodo : DialogFragment() {

private lateinit var binding:FragmentAddTodoBinding
    private var listener : OnDialogNextBtnClickListener? = null


    fun setListener(listener: OnDialogNextBtnClickListener) {
        this.listener = listener
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       binding = FragmentAddTodoBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerEvents()

    }

    private fun registerEvents() {
binding.todoNextBtn.setOnClickListener {
    val todoTask = binding.todoEt.text.toString()
    if (todoTask.isNotEmpty()){
        listener?.onSave(todoTask,binding.todoEt)
    }
    else
    {
        Toast.makeText(context,"empty not allw",Toast.LENGTH_SHORT)
    }
}
    }

    interface OnDialogNextBtnClickListener{
        fun onSave(todo:String,todoEt:TextInputEditText){

        }
    }
}