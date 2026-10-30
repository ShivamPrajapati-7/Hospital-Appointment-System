package com.shivaminteractive.hospital_appointment_system

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class doctor_dashboard_Fragment : Fragment() {

    private lateinit var docname: TextView
    private lateinit var speclization: TextView
    private lateinit var todayCount: TextView
    private lateinit var pendingCount: TextView
    private lateinit var totalCount: TextView
    private lateinit var recyclerview: RecyclerView

    private val db = FirebaseFirestore.getInstance()
    private var doctorUid: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_doctor_dashboard, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        doctorUid = arguments?.getString("uid")
            ?: requireActivity().intent.getStringExtra("uid")
            ?: FirebaseAuth.getInstance().currentUser?.uid ?: ""

        docname = view.findViewById(R.id.docname)
        speclization = view.findViewById(R.id.speclization)
        todayCount = view.findViewById(R.id.today_appointments_count)
        pendingCount = view.findViewById(R.id.pending_appointments_count)
        totalCount = view.findViewById(R.id.total_appointments_count)
        recyclerview = view.findViewById(R.id.doctor_appointments_recyclerview)

        recyclerview.layoutManager = LinearLayoutManager(requireContext())

        if (doctorUid.isNotEmpty()) {
            setdata(doctorUid)
            loadDoctorAppointments(doctorUid)
        }
    }

    private fun setdata(uid: String) {
        db.collection("users").document(uid).get()
            .addOnSuccessListener { success ->
                val doctorName = success.getString("name") ?: success.getString("fullname") ?: "Doctor"
                val spec = success.getString("spec") ?: success.getString("specialization") ?: ""

                docname.text = "Dr. $doctorName"
                speclization.text = spec
            }
    }

    private fun loadDoctorAppointments(uid: String) {
        db.collection("appointments")
            .whereEqualTo("doctorUid", uid)
            .get()
            .addOnSuccessListener { querySnapshot ->
                val total = querySnapshot.size()
                totalCount.text = total.toString()

                val todayDateStr = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
                var todayCountVal = 0
                var pendingCountVal = 0

                val appointmentList = mutableListOf<patientData>()

                for (doc in querySnapshot) {
                    val date = doc.getString("date") ?: ""
                    val time = doc.getString("time") ?: ""
                    val status = doc.getString("status") ?: "Confirmed"
                    val patientName = doc.getString("patient_Name") ?: "Patient"

                    if (date == todayDateStr) {
                        todayCountVal++
                    }
                    if (status.lowercase() == "pending" || status.lowercase() == "confirmed") {
                        pendingCountVal++
                    }

                    appointmentList.add(
                        patientData(
                            name = patientName,
                            time = "$date $time".trim(),
                            status = status
                        )
                    )
                }

                todayCount.text = todayCountVal.toString()
                pendingCount.text = pendingCountVal.toString()

                recyclerview.adapter = Doctor_Appointment(appointmentList) { selectedPatient ->

                }
            }
            .addOnFailureListener { e ->
                Toast.makeText(requireContext(), "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }
}
