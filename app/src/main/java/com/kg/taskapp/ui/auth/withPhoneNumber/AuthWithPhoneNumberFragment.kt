package com.kg.taskapp.ui.auth.withPhoneNumber

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.kg.taskapp.R
import com.kg.taskapp.databinding.FragmentAuthWithPhoneNumberBinding
import com.kg.taskapp.utils.showAutoKeyboard
import java.util.concurrent.TimeUnit
import kotlin.properties.Delegates

class AuthWithPhoneNumberFragment : Fragment() {

    private lateinit var binding: FragmentAuthWithPhoneNumberBinding
    private lateinit var auth: FirebaseAuth
    private var isReadyToStart = false
    companion object {
        lateinit var verificationID: String
        val REQ_CODE_FR_RESULT = "12121"
        val KEY_FOR_BOOLEAN_RESULT = "12212"
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAuthWithPhoneNumberBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = FirebaseAuth.getInstance()
        binding.apply {
            showAutoKeyboard(requireContext(), etPhoneNumber)
            btnGenCode.setOnClickListener {
                if (etPhoneNumber.text.isNotEmpty()) {
                    sendVerificationCode(etPhoneNumber.text.toString())
                } else {
                    etPhoneNumber.error = "Field for phone nubmber is empty!!!"
                }

            }
            btnVerify.setOnClickListener {
                if (etCode.text.isNotEmpty()) {
                    verifyCode(etCode.text.toString())
                } else {
                    etCode.error = "Field for phone nubmber is empty!!!"
                }
            }
        }
    }

    fun verifyCode(code: String) {
        val credential = PhoneAuthProvider.getCredential(verificationID, code)
        signInByCredentials(credential)
    }

    private fun signInByCredentials(credential: PhoneAuthCredential) {
        val mFireBaseAuth = FirebaseAuth.getInstance()
        mFireBaseAuth.signInWithCredential(credential).addOnCompleteListener {
            if (it.isSuccessful) {
                findNavController().navigateUp()
                isReadyToStart = true
                setFragmentResult(REQ_CODE_FR_RESULT, bundleOf(KEY_FOR_BOOLEAN_RESULT to isReadyToStart ))
                isReadyToStart = false
            }
        }
    }

    private fun sendVerificationCode(phoneNumber: String) {
        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber("+996" + phoneNumber)       // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(requireActivity())                 // Activity (for callback binding)
            .setCallbacks(callbacks)          // OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    private val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        override fun onVerificationCompleted(credential: PhoneAuthCredential) {
            val code = credential.smsCode
            if (code != null) {
                AuthWithPhoneNumberFragment().verifyCode(code)
            }
        }

        override fun onVerificationFailed(e: FirebaseException) {
            Toast.makeText(requireContext(), "Verification failed", Toast.LENGTH_SHORT).show()
            // Show a message and update the UI
        }

        override fun onCodeSent(
            verificationId: String, token: PhoneAuthProvider.ForceResendingToken
        ) {
            super.onCodeSent(verificationId, token)
            verificationID = verificationId
            Toast.makeText(requireContext(), "Checking was successfully completed", Toast.LENGTH_SHORT ).show()

        }
    }


}