package com.shivaminteractive.hospital_appointment_system

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

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
    }
}