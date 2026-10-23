package com.shivaminteractive.hospital_appointment_system

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class patient_dashboard_Fragment : Fragment() {

    private lateinit var patient_name: TextView
    private lateinit var patient_profile_image: ImageView
    private lateinit var book_appointment: Button
    private lateinit var book_appointment_layout: LinearLayout
    private lateinit var medical_records: LinearLayout
    private lateinit var prescription: LinearLayout
    private lateinit var doctor_profile_photo: ImageView
    private lateinit var doctor_name: TextView
    private lateinit var doctor_specilization: TextView
    private lateinit var appointment_time: TextView
    private lateinit var hospital_name: TextView
    private lateinit var all_appointments: TextView
    private lateinit var health_tips: LinearLayout
    private lateinit var precautions: LinearLayout
    private lateinit var feedback: LinearLayout

    private val db = FirebaseFirestore.getInstance()
    private var patientUid: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_patient_dashboard, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        patientUid = arguments?.getString("uid")
            ?: requireActivity().intent.getStringExtra("uid")
            ?: FirebaseAuth.getInstance().currentUser?.uid ?: ""

        // Bind views
        patient_name = view.findViewById(R.id.patient_name)
        patient_profile_image = view.findViewById(R.id.patient_profile_image)
        book_appointment = view.findViewById(R.id.book_appointment)
        book_appointment_layout = view.findViewById(R.id.book_appointment_layout)
        medical_records = view.findViewById(R.id.medical_records)
        prescription = view.findViewById(R.id.prescription)
        doctor_profile_photo = view.findViewById(R.id.doctor_profile_photo)
        doctor_name = view.findViewById(R.id.doctor_name)
        doctor_specilization = view.findViewById(R.id.doctor_secilization)
        appointment_time = view.findViewById(R.id.appointment_time)
        hospital_name = view.findViewById(R.id.hospital_name)
        all_appointments = view.findViewById(R.id.all_appointments)
        health_tips = view.findViewById(R.id.health_tips)
        precautions = view.findViewById(R.id.precautions)
        feedback = view.findViewById(R.id.feedback)

        if (patientUid.isNotEmpty()) {
            getdata(patientUid)
            doctor_appointment(patientUid)
        }

        book_appointment.setOnClickListener {
            val intent = Intent(requireContext(), BookAppointmentActivity::class.java)
            intent.putExtra("uid", patientUid)
            startActivity(intent)
        }

        book_appointment_layout.setOnClickListener {
            val intent = Intent(requireContext(), BookAppointmentActivity::class.java)
            intent.putExtra("uid", patientUid)
            startActivity(intent)
        }

        all_appointments.setOnClickListener {
            val fragment = all_Appointment_Fragment().apply {
                arguments = Bundle().apply {
                    putString("uid", patientUid)
                }
            }
            parentFragmentManager.beginTransaction()
                .replace(R.id.patient_fragment_container, fragment)
                .addToBackStack(null)
                .commit()
        }
    }

    fun getdata(uid: String) {
        db.collection("users").document(uid).get()
            .addOnSuccessListener { success ->
                val fullName = success.getString("fullname") ?: ""
                patient_name.text = "Hello, $fullName"
            }
            .addOnFailureListener { }
    }

    fun doctor_appointment(uid: String) {
        db.collection("appointments")
            .whereEqualTo("patientUid", uid)
            .get()
            .addOnSuccessListener { querySnapshot ->
                if (!querySnapshot.isEmpty) {
                    val document = querySnapshot.documents[0]
                    val doctorUid = document.getString("doctorUid") ?: ""
                    val time = document.getString("time") ?: ""
                    val date = document.getString("date") ?: ""

                    appointment_time.text = "$date $time"

                    if (doctorUid.isNotEmpty()) {
                        db.collection("users").document(doctorUid).get()
                            .addOnSuccessListener { doc ->
                                doctor_name.text = doc.getString("name") ?: doc.getString("fullname") ?: "Doctor"
                                doctor_specilization.text = doc.getString("spec") ?: doc.getString("specialization") ?: ""
                            }
                    }
                }
            }
    }
}
