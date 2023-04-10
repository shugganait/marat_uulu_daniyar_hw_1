package com.kg.taskapp.ui.onBoard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.kg.taskapp.R
import com.kg.taskapp.data.local.Pref
import com.kg.taskapp.databinding.FragmentOnBoardBinding
import com.kg.taskapp.ui.onBoard.adapter.OnBoardAdapter


class OnBoardFragment : Fragment() {

    private lateinit var binding: FragmentOnBoardBinding
    private val adapter = OnBoardAdapter(this::onStartClick, this::onNextClick)
    private lateinit var pref: Pref

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOnBoardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pref = Pref(requireContext())
        binding.viewPager.adapter = adapter
        binding.indicator.attachTo(binding.viewPager)

    }

    private fun onNextClick(){
        binding.viewPager.setCurrentItem(getItem(+1), true)
    }
    private fun onStartClick() {
        pref.saveSeen()
        findNavController().navigateUp()
    }

    private fun getItem(i: Int): Int {
        return binding.viewPager.currentItem + i
    }
}