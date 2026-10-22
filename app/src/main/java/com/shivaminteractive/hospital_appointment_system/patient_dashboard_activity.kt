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
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.firestore.FirebaseFirestore

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

    private val db = FirebaseFirestore.getInstance()





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_patient_dashboard)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val uid = intent.getStringExtra("uid")
        if (uid != null) {
            getdata(uid)
            //doctor_appointment(uid)
        }


        book_appointment=findViewById(R.id.book_appointment)


        //Book Appointment button
        book_appointment.setOnClickListener {

            val intent= Intent(this, BookAppointmentActivity::class.java)
            intent.putExtra("uid",uid)
            startActivity(intent)

        }

//        patient_name.text="Hello, $name"
        val navigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)


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
                R.id.nav_profile->{
                    true
                }
                else -> false
            }
        }

    }

    fun getdata(uid: String)
    {
        db.collection("users").document(uid).get()
            .addOnSuccessListener { success->
                patient_name=findViewById(R.id.patient_name)
                patient_name.text="Hello, ${success.get("fullname")}"
            }
            .addOnFailureListener { fail->

            }
    }
    fun doctor_appointment(uid: String)
    {
        db.collection("appointments").document(uid).get()
            .addOnSuccessListener { success->
                val docteruid=success.get("docterUid")
                db.collection("users").document(docteruid as String).get()
                    .addOnSuccessListener { docter->
                        doctor_name=findViewById(R.id.doctor_name)
                        doctor_name.text= docter.get("name").toString()
                        doctor_specilization=findViewById(R.id.doctor_secilization)
                        doctor_specilization.text= docter.get("spec").toString()
                        appointment_time=findViewById(R.id.appointment_time)
                        appointment_time.text=success.get("time").toString()
                    }
            }
    }






}