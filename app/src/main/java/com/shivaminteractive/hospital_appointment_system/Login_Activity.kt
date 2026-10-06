package com.shivaminteractive.hospital_appointment_system

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.set
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class Login_Activity : AppCompatActivity() {


    lateinit var email_input: EditText
    lateinit var pass_input: EditText
    lateinit var login:Button
    lateinit var register:Button
    lateinit var progressBar: ProgressBar
    private var auth= FirebaseAuth.getInstance()
    private var db= FirebaseFirestore.getInstance()

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        //Username and password declaration
        setContentView(R.layout.activity_login)

        //Finded by ids'
        email_input=findViewById(R.id.email_input)
        pass_input=findViewById(R.id.pass_input)
        login=findViewById(R.id.btLogin)
        register=findViewById(R.id.btnRegistation)
        progressBar=findViewById(R.id.progressBar)



        login.setOnClickListener {
            val email=email_input.text.toString().trim()
            val password=pass_input.text.toString().trim()
            progressBar.visibility = View.VISIBLE

            login(email, password)
        }


            //Register btn
        register.setOnClickListener {
            var intent= Intent(this, Registration_Actvity::class.java)
            startActivity(intent)
        }
    }

    fun login(email:String , pass:String){
        auth.signInWithEmailAndPassword(email,pass)
            .addOnSuccessListener { success->
                val uid=success.user!!.uid
                userRole(uid)
            }
            .addOnFailureListener { fail->
                progressBar.visibility=View.GONE
                Toast.makeText(this,"${fail.message}",Toast.LENGTH_SHORT).show()
            }
    }

    fun userRole(uid:String){
        db.collection("users").document(uid).get()
            .addOnSuccessListener { success->
                val role=success.getString("role")

                when(role)
                {
                    "patient"->{
                        val intent= Intent(this, patient_dashboard_activity::class.java)
                        intent.putExtra("uid",uid)
                        progressBar.visibility=View.GONE
                        startActivity(intent)

                    }
                    "doctor"->{
                        val intent= Intent(this, Doctor_DashBoard_Activity::class.java)
                        intent.putExtra("uid",uid)
                        progressBar.visibility=View.GONE
                        startActivity(intent)
                    }
                }
            }
            .addOnFailureListener { fail->
                Toast.makeText(this,"Role not specify",Toast.LENGTH_SHORT).show()
            }
    }
}