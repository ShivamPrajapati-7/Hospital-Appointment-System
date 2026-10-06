package com.shivaminteractive.hospital_appointment_system

import android.os.Bundle
import android.widget.Spinner
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.google.firebase.firestore.FirebaseFirestore

class Doctor_DashBoard_Activity : AppCompatActivity() {

    lateinit var appointment: Fragment
    lateinit var speclization: TextView
    lateinit var name: TextView
    private val db= FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_doctor_dash_board)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val uid = intent.getStringExtra("uid")
        if(uid!=null){
            setdata(uid)
        }
    }
    fun setdata(uid:String)
    {
        db.collection("users").document(uid).get()
            .addOnSuccessListener { success->
                name=findViewById(R.id.docname)
                name.text="Dr.${success.get("name")}"

                speclization=findViewById(R.id.speclization)
                speclization.text="${success.get("spec")}"
            }
    }
}