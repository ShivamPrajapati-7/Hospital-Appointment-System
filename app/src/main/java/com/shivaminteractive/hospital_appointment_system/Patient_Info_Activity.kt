package com.shivaminteractive.hospital_appointment_system

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Patient_Info_Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_patient_info)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val patientName = intent.getStringExtra("patient_name") ?: ""
        val patientUid = intent.getStringExtra("patient_uid") ?: ""
        val appointmentTime = intent.getStringExtra("appointment_time") ?: ""

        if (savedInstanceState == null) {
            val fragment = Patient_info_Page_Fragment().apply {
                arguments = Bundle().apply {
                    putString("patient_name", patientName)
                    putString("patient_uid", patientUid)
                    putString("appointment_time", appointmentTime)
                }
            }
            supportFragmentManager.beginTransaction()
                .replace(R.id.patient_info_fragment_container, fragment)
                .commit()
        }
    }
}
