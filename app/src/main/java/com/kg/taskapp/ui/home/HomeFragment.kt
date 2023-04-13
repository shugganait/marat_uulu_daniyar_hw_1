package com.kg.taskapp.ui.home

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.kg.taskapp.App
import com.kg.taskapp.R
import com.kg.taskapp.databinding.FragmentHomeBinding
import com.kg.taskapp.ui.home.adapter.TaskAdapter
import com.kg.taskapp.utils.showToast


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val adapter = TaskAdapter()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val data = App.db.taskDao().getAll()
        adapter.addTasks(data)
        binding.recyclerView.adapter = adapter
        binding.fab.setOnClickListener {
            findNavController().navigate(R.id.task_fragment)
        }
        initListeners()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun initListeners() {
        adapter.onItemClick = {
            findNavController().navigate(R.id.action_navigation_home_to_task_fragment, bundleOf(
                KEY_FOR_EDIT to it, KEY_FOR_FUN to true))
        }
        adapter.onItemLongClick = {
            AlertDialog.Builder(requireContext())
              .setMessage("Are you sure, that you want to delete this note?")
                .setNegativeButton("Cancel") { d, i ->
                    d.dismiss()
                }.setPositiveButton("Yes") { d, i ->
                    App.db.taskDao().deleteById(it.id!!)
                    showToast("${it.title}, ${it.desc} was deleted")
                    adapter.delete(TaskAdapter.getAdapterPosition)
                    d.dismiss()
                }.show()
        }
    }

    companion object {
        const val KEY_FOR_EDIT = "KeyForEdit"
        const val KEY_FOR_FUN = "KeyFoeOper"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}