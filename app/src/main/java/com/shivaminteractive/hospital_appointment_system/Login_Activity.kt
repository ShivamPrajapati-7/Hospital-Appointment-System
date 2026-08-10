package com.shivaminteractive.hospital_appointment_system

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class Login_Activity : AppCompatActivity() {

    lateinit var email_input: EditText
    lateinit var pass_input: EditText
    lateinit var login:Button
    lateinit var register:Button
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)


        email_input=findViewById(R.id.email_input)
        pass_input=findViewById(R.id.pass_input)

        login=findViewById(R.id.btLogin)
        register=findViewById(R.id.btnRegistation)

        register.setOnClickListener {
            var intent= Intent(this, Registration_Actvity::class.java)
            startActivity(intent)
        }
    }
}