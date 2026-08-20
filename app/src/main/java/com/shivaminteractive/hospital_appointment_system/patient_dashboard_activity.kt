package com.shivaminteractive.hospital_appointment_system

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.menu.MenuView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarMenu
import com.google.android.material.navigation.NavigationView

class patient_dashboard_activity : AppCompatActivity() {



    //variables declarations
    lateinit var hamborger: ImageButton
    lateinit var patient_name: TextView
    lateinit var patient_profile_image: ImageView
    lateinit var book_appointment: Button
    lateinit var book_appointment_layout: LinearLayout
    lateinit var medical_records : LinearLayout
    lateinit var prescription : LinearLayout
    lateinit var doctor_profile_photo: ImageView
    lateinit var doctor_name: TextView
    lateinit var doctor_specilization: TextView
    lateinit var appointment_time: TextView
    lateinit var hostpital_name: TextView
    lateinit var all_appointments: TextView
    lateinit var health_tips: LinearLayout
    lateinit var precautions: LinearLayout
    lateinit var feedback: LinearLayout





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_patient_dashboard)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        patient_name=findViewById(R.id.patient_name)


        //Data transfer to another activity
        val name=intent.getStringExtra("fullname")
        val gender=intent.getStringExtra("gender")
        val email=intent.getStringExtra("email")
        val phoneNumber=intent.getStringExtra("phone_no")
        val bloodgroup=intent.getStringExtra("BloodGroup")
        patient_name.text="Hello, $name"
        val navigationView = findViewById< BottomNavigationView>(R.id.bottomNavigationView)


        //bottom navigation bar
        navigationView.setOnItemSelectedListener { item ->

            when (item.itemId) {

                R.id.nav_home -> {
                    // Home clicked
                    true
                }

                R.id.nav_schadule -> {
                    // Profile clicked
                    true
                }

                R.id.nav_chat-> {
                    // Appointments clicked
                    true
                }
                R.id.nav_profile-> {

                    //sending data to profile
                    var intent= Intent(this , patient_profile_Activity::class.java)
                    intent.putExtra("fullname",name)
                    intent.putExtra("gender",gender)
                    intent.putExtra("email",email)
                    intent.putExtra("phone_no",phoneNumber)
                    intent.putExtra("BloodGroup",bloodgroup)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }

    }




}