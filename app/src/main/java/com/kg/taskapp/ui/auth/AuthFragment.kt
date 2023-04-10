package com.kg.taskapp.ui.auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.IntentSenderRequest
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.fragment.findNavController
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.kg.taskapp.R
import com.kg.taskapp.databinding.FragmentAuthBinding
import com.kg.taskapp.ui.auth.withPhoneNumber.AuthWithPhoneNumberFragment
import java.util.concurrent.TimeUnit


class AuthFragment : Fragment() {

    private lateinit var binding: FragmentAuthBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var signInRequest: BeginSignInRequest
    private lateinit var oneTapClient: SignInClient
    private val REQ_ONE_TAP = 2

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAuthBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = FirebaseAuth.getInstance()
        oneTapClient = Identity.getSignInClient(requireActivity())
        signInRequest = BeginSignInRequest.builder()
            .setGoogleIdTokenRequestOptions(
                BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)
                    .setServerClientId(getString(R.string.default_web_client_id))
                    // Only show accounts previously used to sign in.
                    .setFilterByAuthorizedAccounts(false)
                    .build())
            .build()
        binding.btnGoogleAuth.setOnClickListener {
            authWithGoogle()
        }
        binding.btnPhoneNumberAuth.setOnClickListener {
            findNavController().navigate(R.id.authWithPhoneNumberFragment)
            setFragmentResultListener(AuthWithPhoneNumberFragment.REQ_CODE_FR_RESULT) { key, bundle ->
                val result = bundle.getBoolean(AuthWithPhoneNumberFragment.KEY_FOR_BOOLEAN_RESULT)
                if (result){
                    findNavController().navigateUp()
                }
            }
        }
    }

    private fun authWithGoogle() {
        oneTapClient.beginSignIn(signInRequest)
        .addOnSuccessListener {
            startIntentSenderForResult(
                it.pendingIntent.intentSender, REQ_ONE_TAP,
                null, 0, 0, 0, null)
        }
        .addOnFailureListener {
            Log.e("shug", "auth: " + it.message )
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            REQ_ONE_TAP -> {
                try {
                    val credential = oneTapClient.getSignInCredentialFromIntent(data)
                    val idToken = credential.googleIdToken
                    when {
                        idToken != null -> {
                            val firebaseCredential = GoogleAuthProvider.getCredential(idToken, null)
                            auth.signInWithCredential(firebaseCredential)
                                .addOnSuccessListener {
                                 findNavController().navigateUp()
                                }
                        }
                        else -> {

                        }
                    }
                } catch (e: ApiException) {

                }
            }
        }
    }
}