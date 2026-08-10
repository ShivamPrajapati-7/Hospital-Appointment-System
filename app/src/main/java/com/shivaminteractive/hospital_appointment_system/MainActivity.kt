package com.shivaminteractive.hospital_appointment_system

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    lateinit var btnlogin: Button
    lateinit var btnregistor: Button
    lateinit var con_doc: LinearLayout
    lateinit var con_patient: LinearLayout



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        btnlogin=findViewById(R.id.btnLogin)
        btnregistor=findViewById(R.id.btnRegister)

        con_doc=findViewById(R.id.con_doc)
        con_patient=findViewById(R.id.con_patient)



        btnlogin.setOnClickListener {

            var intent=Intent(this, Login_Activity::class.java)
            startActivity(intent)

        }

        btnregistor.setOnClickListener {
            var intent= Intent(this, Registration_Actvity::class.java)
            startActivity(intent)
        }
    }
}