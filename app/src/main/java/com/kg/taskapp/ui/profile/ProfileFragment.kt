package com.kg.taskapp.ui.profile

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import com.kg.taskapp.data.local.Pref
import com.kg.taskapp.databinding.FragmentProfileBinding
import com.kg.taskapp.utils.loadImage


class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private lateinit var pref: Pref
    private val launcher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK && it.data != null) {
                val uri: Uri? = it.data?.data
                pref.saveProfilePicture(uri.toString())
                binding.imgProfile.loadImage(uri.toString())
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pref = Pref(requireContext())
        saveNickName()
        saveImage()
    }

    private fun saveNickName() {
        binding.etUserName.setText(pref.getNickName())
        binding.etUserName.addTextChangedListener {
            pref.saveNickName(binding.etUserName.text.toString())
        }
    }

    private fun saveImage() {
        binding.imgProfile.loadImage(pref.getProfilePicture())
        binding.imgProfile.setOnClickListener {
            val pickImageIntent = Intent()
            pickImageIntent.type = "image/*"
            pickImageIntent.action = Intent.ACTION_PICK
            launcher.launch(pickImageIntent)
        }
    }
}