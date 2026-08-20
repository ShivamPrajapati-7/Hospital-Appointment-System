package com.shivaminteractive.hospital_appointment_system

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import org.w3c.dom.Text

class patient_profile_Activity : AppCompatActivity() {

    lateinit var name: TextView
    lateinit var tvpatientID: TextView
    lateinit var email: TextView
    lateinit var phone_no: TextView
    lateinit var dob: TextView
    lateinit var gender: TextView
    lateinit var blood_group: TextView
    lateinit var address: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_patient_profile)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //finding components by id's
        name=findViewById(R.id.Name)
        email=findViewById(R.id.tvEmail)
        phone_no=findViewById(R.id.tvPhone)
        gender=findViewById(R.id.tvGender)
        blood_group=findViewById(R.id.tvBloodGroup)


        //receiving data form dashboard
        val fullname=intent.getStringExtra("fullname")
        val Email=intent.getStringExtra("email")
        val Gender=intent.getStringExtra("gender")
        val phone=intent.getStringExtra("phone_no")
        val bloodgroup=intent.getStringExtra("BloodGroup")


        //data fetching
        name.text=fullname
        email.text=Email
        gender.text=Gender
        phone_no.text=phone
        blood_group.text=bloodgroup



    }
}