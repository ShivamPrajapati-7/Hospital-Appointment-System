package com.shivaminteractive.hospital_appointment_system

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class all_Appointment_Fragment : Fragment() {

    private lateinit var recyclerview: RecyclerView
    private lateinit var adapter: appointmentAdaptor
    private val appointmentList = mutableListOf<appointment>()
    private val db = FirebaseFirestore.getInstance()
    private var patientUid: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_all__appointment_, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        patientUid = arguments?.getString("uid")
            ?: requireActivity().intent.getStringExtra("uid")
            ?: FirebaseAuth.getInstance().currentUser?.uid ?: ""

        recyclerview = view.findViewById(R.id.all_appointments_recyclerview)
        recyclerview.layoutManager = LinearLayoutManager(requireContext())

        adapter = appointmentAdaptor(appointmentList) { selectedAppointment ->
            // Handle appointment item click if needed
        }
        recyclerview.adapter = adapter

        if (patientUid.isNotEmpty()) {
            appointments(patientUid)
        }
    }

    fun appointments(uid: String) {
        db.collection("appointments")
            .whereEqualTo("patientUid", uid)
            .get()
            .addOnSuccessListener { querySnapshot ->
                appointmentList.clear()
                for (document in querySnapshot) {
                    val doctorUid = document.getString("doctorUid") ?: ""
                    val date = document.getString("date") ?: ""
                    val time = document.getString("time") ?: ""

                    if (doctorUid.isNotEmpty()) {
                        db.collection("users").document(doctorUid).get()
                            .addOnSuccessListener { doc ->
                                val doctorName = doc.getString("name") ?: doc.getString("fullname") ?: "Doctor"
                                val doctorSpec = doc.getString("spec") ?: doc.getString("specialization") ?: ""

                                val item = appointment(
                                    appointmentuid = document.id,
                                    docter_name = doctorName,
                                    docter_spec = doctorSpec,
                                    time = "$date $time",
                                    hospital = "Health Care+"
                                )
                                appointmentList.add(item)
                                adapter.notifyDataSetChanged()
                            }
                    }
                }
            }
            .addOnFailureListener { e ->
                Toast.makeText(requireContext(), "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }
}
