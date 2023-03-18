package com.kg.taskapp.ui.task

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import com.kg.taskapp.R
import com.kg.taskapp.databinding.FragmentTaskBinding
import com.kg.taskapp.model.Task

class TaskFragment : Fragment() {

    private lateinit var binding: FragmentTaskBinding

    companion object{
        const val TASK_REQUEST = "task718"
        const val TASK_KEY = "taskKey"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTaskBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnSave.setOnClickListener {
            val data  = Task( title = binding.etTitle.text.toString(), desc = binding.etDesc.text.toString())
            setFragmentResult(TASK_REQUEST, bundleOf(TASK_KEY to data))
            findNavController().navigateUp()
        }
    }

}