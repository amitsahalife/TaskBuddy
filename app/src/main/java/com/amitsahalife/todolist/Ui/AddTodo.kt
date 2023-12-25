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
import com.amitsahalife.todolist.util.ToDoData
import com.google.android.material.textfield.TextInputEditText


class AddTodo : DialogFragment() {

private lateinit var binding:FragmentAddTodoBinding
    private var listener : OnDialogNextBtnClickListener? = null
private var todOdATA : ToDoData? = null

    fun setListener(listener: OnDialogNextBtnClickListener) {
        this.listener = listener
    }
    companion object {
        const val TAG = "AddTodoPopUp"
        @JvmStatic
        fun newInstance(taskId: String, task: String) =
            AddTodo().apply {
                arguments = Bundle().apply {
                    putString("taskId", taskId)
                    putString("task", task)
                }
            }
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

        if (arguments!=null){
            todOdATA= ToDoData(arguments?.getString("taskId").toString() ,arguments?.getString("task").toString())
            binding.todoEt.setText(todOdATA?.task)
        }
        binding.todoClose.setOnClickListener {
            dismiss()
        }
        registerEvents()

    }

    private fun registerEvents() {
        binding.todoNextBtn.setOnClickListener {

            val todoTask = binding.todoEt.text.toString()
            if (todoTask.isNotEmpty()){
                if (todOdATA== null){
                    listener?.onSave(todoTask , binding.todoEt)
                }else{
                    todOdATA!!.task = todoTask
                    listener?.updateTask(todOdATA!!, binding.todoEt)
                }

            }
        }
    }

    interface OnDialogNextBtnClickListener{
        fun onSave(todo:String,todoEt:TextInputEditText){

        }
        fun updateTask(toDoData: ToDoData , todoEdit:TextInputEditText)
    }
}