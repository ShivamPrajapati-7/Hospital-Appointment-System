package com.shivaminteractive.hospital_appointment_system

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlin.jvm.java

class Registration_Actvity : AppCompatActivity() {

    lateinit var Doctor_regisration: Button
    lateinit var Patient_regisration: Button




    var regstatus: String?=null//status of patient and docter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContentView(R.layout.activity_registration_actvity)


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //fatching docter fragment
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, DoctorRagistrationFragement())
                .commit()
        }

        Doctor_regisration=findViewById(R.id.Doctor_registration)
        Patient_regisration=findViewById(R.id.Patient_registration)



        //docter registration btn
        Doctor_regisration.setOnClickListener {
            setdoctor()
        }


        //patient registration btn
        Patient_regisration.setOnClickListener {
            setpatient()
        }




    }


    //patient btn function
    fun setpatient() {

        Patient_regisration.setTextColor(ContextCompat.getColor(this, R.color.white))

        Doctor_regisration.setTextColor(ContextCompat.getColor(this, R.color.black))

        Doctor_regisration.setBackgroundResource(R.drawable.noramlbtn)
        Patient_regisration.setBackgroundResource(R.drawable.login)

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, Patient_registration_Fragment())
            .commit()
    }

    //docter btn function
    fun setdoctor() {

        Doctor_regisration.setBackgroundResource(R.drawable.login)
        Patient_regisration.setBackgroundResource(R.drawable.noramlbtn)

        Patient_regisration.setTextColor(ContextCompat.getColor(this, R.color.black))

        Doctor_regisration.setTextColor(ContextCompat.getColor(this, R.color.white))

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, DoctorRagistrationFragement())
            .commit()

    }

    //Taking Data From other activity
    fun takedata(fullname:String,gender:String, email: String , phoneNumber: String,bloodgroup:String,status: String)
    {
        val patient_name=fullname
        regstatus= status.toString()

        if(status=="Patient")
        {
                //opening patient activity and sending data to dashboard
                var intent = Intent(this, patient_dashboard_activity::class.java)
                intent.putExtra("fullname",fullname)
                intent.putExtra("gender",gender)
                intent.putExtra("email",email)
                intent.putExtra("phone_no",phoneNumber)
                intent.putExtra("BloodGroup",bloodgroup)
                startActivity(intent)


        }
    }

}