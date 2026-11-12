package com.shivaminteractive.hospital_appointment_system

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.firestore.FirebaseFirestore
import java.net.Inet4Address

class Patient_info_Page_Fragment : Fragment() {

    private lateinit var tvPatientName: TextView
    private lateinit var tvPatientage: TextView
    private lateinit var tvPatientGender: TextView
    private lateinit var tvPatientPhone: TextView
    private lateinit var tvBloodGroup: TextView
    private lateinit var tvAddress: TextView
    private lateinit var tvNextAppointment: TextView


    private val db = FirebaseFirestore.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_patient_info__page_, container, false)

        tvPatientName = view.findViewById(R.id.tvPatientName)
        tvPatientage=view.findViewById(R.id.tvPatientAge)
        tvPatientGender=view.findViewById(R.id.tvPatientGender)
        tvPatientPhone = view.findViewById(R.id.tvPatientPhone)
        tvNextAppointment=view.findViewById(R.id.tvNextAppointment)
        tvBloodGroup=view.findViewById(R.id.tvBloodGroup)
        tvAddress=view.findViewById(R.id.tvAddress)


        val patientName = arguments?.getString("patient_name") ?: ""
        val patientUid = arguments?.getString("patient_uid") ?: ""
        val appointmentTime = arguments?.getString("appointment_time") ?: ""

        if (patientName.isNotEmpty()) {
            tvPatientName.text = patientName
        }

        tvNextAppointment.text = appointmentTime

        if (patientUid.isNotEmpty()) {
            loadPatientData(patientUid)
        }

        return view
    }

    private fun loadPatientData(uid: String) {
        db.collection("users").document(uid).get()
            .addOnSuccessListener { doc ->
                if (doc.exists()) {
                    val name = doc.getString("fullname") ?: doc.getString("name") ?: ""
                    if (name.isNotEmpty()) {
                        tvPatientName.text = name
                    }
                    val phone = doc.getString("phone") ?: doc.getString("mobile") ?: ""
                    if (phone.isNotEmpty()) {
                        tvPatientPhone.text = "Phone: $phone"
                    }
                    val bloodgroup=doc.getString("bloodgroup")
                    tvBloodGroup.text=bloodgroup
                    val gender=doc.getString("gender")
                    if(gender=="F")
                    {
                        tvPatientGender.text="Female"
                    }
                    else if(gender=="M"){
                        tvPatientGender.text="Male"
                    }
                }
            }
            .addOnFailureListener { e ->
                Toast.makeText(requireContext(), "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }
}
