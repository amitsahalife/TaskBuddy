package com.amitsahalife.todolist.Ui

import android.app.AlertDialog
import android.content.ContentValues.TAG
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.amitsahalife.todolist.R
import com.amitsahalife.todolist.databinding.FragmentHomeBinding
import com.amitsahalife.todolist.util.TaskAdapter
import com.amitsahalife.todolist.util.ToDoData
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class homeFragment : Fragment(), AddTodo.OnDialogNextBtnClickListener,
    TaskAdapter.TaskAdapterInterface {
private lateinit var auth : FirebaseAuth
    private var backPressedOnce = false
private lateinit var databaseReference: DatabaseReference
private lateinit var navControl:NavController
private  var popUpFragment : AddTodo?=null
private lateinit var binding: FragmentHomeBinding

    private lateinit var taskAdapter: TaskAdapter
    private lateinit var toDoItemList: MutableList<ToDoData>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // init block
        init (view)
        // fectch from the server
        getTaskFromFirebase()
        registerEvent()
        logOut()

onBackPressedHandle()
    }

private fun onBackPressedHandle (){
    val onBackPressedCallback = object : OnBackPressedCallback(true){
        override fun handleOnBackPressed() {

            if (backPressedOnce){
                requireActivity().finish()
            }
            else {
                Toast.makeText(requireContext(),"Press Again To Exit From The App",Toast.LENGTH_LONG).show()
                backPressedOnce = true

                view?.postDelayed({backPressedOnce = false},2000)
            }

        }
    }
    requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner,onBackPressedCallback)
}
    private fun getTaskFromFirebase() {
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                toDoItemList.clear()
                for (taskSnapshot in snapshot.children) {
                    val todoTask =
                        taskSnapshot.key?.let { ToDoData(it, taskSnapshot.value.toString()) }

                    if (todoTask != null) {
                        toDoItemList.add(todoTask)
                    }

                }
                Log.d(TAG, "onDataChange: " + toDoItemList)
                taskAdapter.notifyDataSetChanged()

            }
            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show()
            }


        })
    }
private fun logOut(){
    binding.logout.setOnClickListener{
       // auth.signOut()

val builder = AlertDialog.Builder(context)
        builder.setTitle("Logout of your account?")
        builder.setMessage("Are you sure, You want to logout? You can always logback whenever you want")
        builder.setPositiveButton("Han ji")
        {
            dialog,which ->
            FirebaseAuth.getInstance().signOut()
            Toast.makeText(context,"Logout Successfully",Toast.LENGTH_SHORT).show()
            navControl.navigate(R.id.action_homeFragment_to_screenTwoFragment)
        }
        builder.setNegativeButton("Nahi"){
            dialog, which ->
            dialog.dismiss()
        }
        val dialog:AlertDialog = builder.create()

        dialog.show()


    }
}
    private fun registerEvent() {

        binding.addTaskBtn.setOnClickListener {
            // follow on

if (popUpFragment!=null){
    childFragmentManager.beginTransaction().remove(popUpFragment!!).commit()
}
            popUpFragment = AddTodo()
            popUpFragment!!.setListener(this)
            popUpFragment!!.show(
                childFragmentManager,
                "AddTodoPopUp"
            )
        }
    }

    private fun init(view: View) {
        navControl = Navigation.findNavController(view)
        auth = FirebaseAuth.getInstance()
        databaseReference = FirebaseDatabase.getInstance().reference.child("Tasks")
            .child(auth.currentUser?.uid.toString())
        binding.mainRecyclerView.setHasFixedSize(true)
        binding.mainRecyclerView.layoutManager = LinearLayoutManager(context)
        toDoItemList = mutableListOf()
        taskAdapter = TaskAdapter(toDoItemList)
        taskAdapter.setListener(this)
        statusBarColor()
        binding.mainRecyclerView.adapter = taskAdapter
    }

    private fun statusBarColor(){

            requireActivity().window.statusBarColor = Color.parseColor("#cdbe32")
          //  window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            //window.statusBarColor = Color.BLUE

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
            popUpFragment!!.dismiss()
        }
    }

    override fun updateTask(toDoData: ToDoData, todoEdit: TextInputEditText) {
val map  = HashMap<String, Any>()
        map[toDoData.taskId]=toDoData.task
        // possible solution
        // map [todoData.status] = tododata.status
        databaseReference.updateChildren(map).addOnCompleteListener {
            if (it.isSuccessful){
                Toast.makeText(context, "Updated Successfully", Toast.LENGTH_SHORT).show()
            }
            else{
                Toast.makeText(context, it.exception.toString(), Toast.LENGTH_SHORT).show()
            }

            popUpFragment?.dismiss()
        }
    }

    override fun onDeleteItemClicked(toDoData: ToDoData, position: Int) {
       databaseReference.child(toDoData.taskId).removeValue().addOnCompleteListener {
           if (it.isSuccessful){
               Toast.makeText(context, "Deleted Successfully", Toast.LENGTH_SHORT).show()
           }
           else{
               Toast.makeText(context, it.exception.toString(), Toast.LENGTH_SHORT).show()
           }
       }
    }

    override fun onEditItemClicked(toDoData: ToDoData, position: Int) {
        if (popUpFragment != null)
            childFragmentManager.beginTransaction().remove(popUpFragment!!).commit()

        popUpFragment = AddTodo.newInstance(toDoData.taskId,toDoData.task)
        popUpFragment!!.setListener(this)
        popUpFragment!!.show(
            childFragmentManager,
            AddTodo.TAG
        )
    }


}