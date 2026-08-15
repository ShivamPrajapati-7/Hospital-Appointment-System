package com.shivaminteractive.hospital_appointment_system

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction

class Registration_Actvity : AppCompatActivity() {

    lateinit var Doctor_regisration: Button
    lateinit var Patient_regisration: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_registration_actvity)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, DoctorRagistrationFragement())
                .commit()
        }

        Doctor_regisration=findViewById(R.id.Doctor_registration)
        Patient_regisration=findViewById(R.id.Patient_registration)


        Doctor_regisration.setOnClickListener {

            setdoctor()

        }
        Patient_regisration.setOnClickListener {

            setpatient()

        }



    }

    fun setpatient() {

        Patient_regisration.setTextColor(
            ContextCompat.getColor(this, R.color.white)
        )

        Doctor_regisration.setTextColor(
            ContextCompat.getColor(this, R.color.black)
        )

        Doctor_regisration.setBackgroundResource(R.drawable.noramlbtn)
        Patient_regisration.setBackgroundResource(R.drawable.login)

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, Patient_registration_Fragment())
            .commit()
    }

    fun setdoctor() {

        Doctor_regisration.setBackgroundResource(R.drawable.login)
        Patient_regisration.setBackgroundResource(R.drawable.noramlbtn)

        Patient_regisration.setTextColor(
            ContextCompat.getColor(this, R.color.black)
        )

        Doctor_regisration.setTextColor(
            ContextCompat.getColor(this, R.color.white)
        )

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, DoctorRagistrationFragement())
            .commit()
    }
}