package com.kg.taskapp.ui.notifications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.kg.taskapp.databinding.FragmentNotificationsBinding
import com.kg.taskapp.model.Car
import com.kg.taskapp.ui.notifications.adapter.CarAdapter
import com.kg.taskapp.utils.showToast

class NotificationsFragment : Fragment() {

    private var _binding: FragmentNotificationsBinding? = null
    private val binding get() = _binding!!

    private lateinit var db: FirebaseFirestore
    private val adapter = CarAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        db = FirebaseFirestore.getInstance()
        binding.recyclerView.adapter = adapter
        db.collection(FirebaseAuth.getInstance().currentUser?.uid.toString()).get()
            .addOnSuccessListener {
                val list = it.toObjects(Car::class.java)
                adapter.addCars(list)
            }
            .addOnFailureListener {
                showToast("Error in adapter!!!")
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}