package com.kg.taskapp.ui.task

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.kg.taskapp.App
import com.kg.taskapp.databinding.FragmentTaskBinding
import com.kg.taskapp.model.Task
import com.kg.taskapp.ui.home.HomeFragment
import com.kg.taskapp.utils.showAutoKeyboard

class TaskFragment : Fragment() {

    private lateinit var binding: FragmentTaskBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTaskBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showAutoKeyboard(requireContext(), binding.etTitle)
        if (arguments?.getBoolean(HomeFragment.KEY_FOR_FUN) == true){
            binding.btnSave.setText("Update")
            val args = arguments?.getSerializable(HomeFragment.KEY_FOR_EDIT) as Task
            binding.apply {
                etTitle.setText(args.title)
                etDesc.setText(args.desc)
                btnSave.setOnClickListener {
                    if (binding.etTitle.text.isNotEmpty()) {
                        changeSavedItem(args.id!!)
                    } else if (binding.etDesc.text.isNotEmpty()) {
                        changeSavedItem(args.id!!)
                    } else binding.etDesc.error = "Error: Empty field"
                }
            }

        } else {
            binding.btnSave.setOnClickListener {
                if (binding.etTitle.text.isNotEmpty()) {
                    save()
                } else if (binding.etDesc.text.isNotEmpty()) {
                    save()
                } else binding.etDesc.error = "Error: Empty field"

            }
        }

    }

    private fun changeSavedItem(id: Int) {
        val data  = Task( id = id, title = binding.etTitle.text.toString(), desc = binding.etDesc.text.toString())
        App.db.taskDao().update(data)
        findNavController().navigateUp()
    }

    private fun save() {
        val data  = Task( title = binding.etTitle.text.toString(), desc = binding.etDesc.text.toString())
        App.db.taskDao().insert(data)
        findNavController().navigateUp()
    }

}