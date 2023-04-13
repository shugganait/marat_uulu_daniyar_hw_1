package com.kg.taskapp.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.kg.taskapp.databinding.FragmentDashboardBinding
import com.kg.taskapp.model.Car
import com.kg.taskapp.utils.showToast

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var db: FirebaseFirestore

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        db = FirebaseFirestore.getInstance()
        binding.btnSave.setOnClickListener {
            saveCar()
        }
    }

    private fun saveCar() {
        val name = binding.etCarName.text.toString()
        val model = binding.etCarModel.text.toString()
        val car = Car(name, model)

        db.collection(FirebaseAuth.getInstance().currentUser?.uid.toString()).document()
            .set(car)
            .addOnSuccessListener {
                showToast("Data saved")
                binding.etCarModel.text?.clear()
                binding.etCarName.text?.clear()
            }
            .addOnFailureListener {
                showToast("Data is not saved")
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}