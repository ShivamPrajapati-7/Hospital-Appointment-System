package com.shivaminteractive.hospital_appointment_system

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

    lateinit var email: EditText
    lateinit var password: EditText
    lateinit var button:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)


        email=findViewById(R.id.emailAddress)
        password=findViewById(R.id.Password)
        button=findViewById(R.id.loginbtn)


        button.setOnClickListener {

            Log.d("Login_Info","Email : ${email.text.toString()}  Password : ${password.text.toString()}" )

            Toast.makeText(this,"Details Shared",Toast.LENGTH_SHORT).show()

        }
    }
}