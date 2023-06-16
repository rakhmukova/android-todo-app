package com.example.todoapp.ui.additem

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.todoapp.R
import com.example.todoapp.databinding.FragmentAddItemBinding

class AddTodoItemFragment: Fragment(R.layout.fragment_add_item) {

    private lateinit var binding: FragmentAddItemBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = FragmentAddItemBinding.inflate(inflater, container, false)
        return binding.root
    }
}
