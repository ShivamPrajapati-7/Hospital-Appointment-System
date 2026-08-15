package com.shivaminteractive.hospital_appointment_system

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.set

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
        val patdefuser="patient"
        val docdefuser="doctor";
        val defpass="admin";
        email_input=findViewById(R.id.email_input)
        pass_input=findViewById(R.id.pass_input)

        login=findViewById(R.id.btLogin)
        register=findViewById(R.id.btnRegistation)


        login.setOnClickListener {


//            var intent= Intent(this, Doctor_DashBoard_Activity::class.java)
//            startActivity(intent)


            if(email_input.text.toString()==docdefuser && pass_input.text.toString()==defpass)
            {
                var intent= Intent(this, Doctor_DashBoard_Activity::class.java)
                startActivity(intent)
                finish()
            }
//            if(email_input.text.toString()==patdefuser && pass_input.text.toString()==defpass)
//            {
//                var intent= Intent(this, Patient_DashBoard_Activity::class.java)
//                startActivity(intent)
//            }

            else{
                var password=pass_input.text.toString()
                password=""
                Toast.makeText(this, "Incorrect Credentials",Toast.LENGTH_LONG).show()
            }



        }

        register.setOnClickListener {
            var intent= Intent(this, Registration_Actvity::class.java)
            startActivity(intent)
        }
    }
}