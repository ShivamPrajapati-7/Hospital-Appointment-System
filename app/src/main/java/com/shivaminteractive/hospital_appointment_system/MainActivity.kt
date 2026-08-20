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


        //finding by id's
        btnlogin=findViewById(R.id.btnLogin)
        btnregistor=findViewById(R.id.btnRegister)

        con_doc=findViewById(R.id.con_doc)
        con_patient=findViewById(R.id.con_patient)


        //login btn
        btnlogin.setOnClickListener {

            //var intent=Intent(this, Login_Activity::class.java)
            var intent=Intent(this, Login_Activity::class.java)
            startActivity(intent)

        }

        //register btn
        btnregistor.setOnClickListener {
            var intent= Intent(this, Registration_Actvity::class.java)
            startActivity(intent)
        }
    }
}