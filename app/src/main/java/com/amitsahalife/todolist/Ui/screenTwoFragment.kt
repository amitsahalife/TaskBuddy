package com.amitsahalife.todolist.Ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.amitsahalife.todolist.R
import com.amitsahalife.todolist.databinding.FragmentScreenTwo2Binding
import com.amitsahalife.todolist.util.Util.hapticFeedback
import com.amitsahalife.todolist.util.Util.statusBarUtil


class screenTwoFragment : Fragment() {

    private lateinit var navController : NavController
    private lateinit var binding: FragmentScreenTwo2Binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentScreenTwo2Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
init(view)
        statusBarUtil(this,R.color.screentwo)
        binding.login.setOnClickListener {
            navController.navigate(R.id.action_screenTwoFragment_to_signIn2)
            hapticFeedback(requireContext())

        }

        binding.signUp.setOnClickListener {
            navController.navigate(R.id.action_screenTwoFragment_to_signUp2)
            hapticFeedback(requireContext())
        }
    }

    private fun init(view: View) {
        navController = Navigation.findNavController(view)

    }

}

