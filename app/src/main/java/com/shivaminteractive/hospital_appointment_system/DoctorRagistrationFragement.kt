package com.shivaminteractive.hospital_appointment_system

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Spinner
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.util.Calendar

class DoctorRagistrationFragement : Fragment() {

    //Declaration
    lateinit var docname: EditText
    lateinit var dob: EditText
    lateinit var gender_group: RadioGroup
    lateinit var doclicence: EditText
    lateinit var docemail: EditText
    lateinit var docphone: EditText
    lateinit var pass: EditText
    lateinit var confpass: EditText
    lateinit var concent : CheckBox
    lateinit var btnreg: Button
    lateinit var speclaization: Spinner
    lateinit var  progressBar: ProgressBar
    var gender:String=""

    private var auth= FirebaseAuth.getInstance()
    private var db= FirebaseFirestore.getInstance()




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view=inflater.inflate(R.layout.fragment_doctor_ragistration_fragement, container, false)
        docname=view.findViewById(R.id.Doctor_Full_name)
        dob=view.findViewById(R.id.Doctor_Date_Of_Birth)
        gender_group=view.findViewById(R.id.gender_group)
        doclicence=view.findViewById(R.id.Doctor_licence_no)
        docemail=view.findViewById(R.id.email_input)
        docphone=view.findViewById(R.id.Doctor_phone)
        pass=view.findViewById(R.id.doctor_password)
        confpass=view.findViewById(R.id.doctor_confirm_password)
        concent=view.findViewById(R.id.consent)
        btnreg=view.findViewById(R.id.btnRegistation)
        speclaization=view.findViewById(R.id.sep)
        progressBar=view.findViewById(R.id.progressBar)


        dob.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)
            val datePicker = DatePickerDialog(
                requireContext(),
                { _, selectedYear, selectedMonth, selectedDay ->
                    val selectedDate = String.format(
                        "%02d/%02d/%04d",
                        selectedDay,
                        selectedMonth + 1,
                        selectedYear
                    )
                    dob.setText(selectedDate)
                },
                year,
                month,
                day
            )
            datePicker.show()
        }

        gender_group.setOnCheckedChangeListener { group, i ->

            val selectedGenderbtn=group.findViewById<RadioButton>(i)
            val selectedGender = selectedGenderbtn.text.toString()
            gender=selectedGender
        }

        btnreg.setOnClickListener {
            progressBar.visibility= View.VISIBLE
            val name =docname.text.toString().trim()
            val birth=dob.text.toString().trim()
            val licence=doclicence.text.toString().trim()
            val email=docemail.text.toString().trim()
            val phone=docphone.text.toString().trim()
            val password=pass.text.toString().trim()
            val specli=speclaization.selectedItem.toString().trim()

            docterRegistration(name, email,phone,birth, licence ,password,specli)

        }


        return view
    }

    fun docterRegistration(fullname:String, email: String, phoneNumber: String, date_of_birth: String, licence: String, pass:String,speclization:String)
    {

        auth.createUserWithEmailAndPassword(email,pass)
            .addOnSuccessListener { success->
                val uid=success.user!!.uid

                val doctordata=hashMapOf(
                    "name" to fullname,
                    "email" to email,
                    "phone" to phoneNumber,
                    "dob" to date_of_birth,
                    "licence" to licence,
                    "spec" to speclization,
                    "gender" to gender,
                    "role" to "doctor"
                )

                db.collection("users").document(uid).set(doctordata)
                    .addOnSuccessListener { success->
                        progressBar.visibility= View.GONE
                        val intent = Intent(context, Doctor_DashBoard_Activity::class.java)
                        intent.putExtra("uid",uid)//uid  not going to dashboard of doctor
                        startActivity(intent)

                    }
                    .addOnFailureListener { fail->
                        progressBar.visibility= View.GONE
                        Toast.makeText(context,"${fail.message}",Toast.LENGTH_SHORT).show()
                    }
            }
            .addOnFailureListener { fail->
                progressBar.visibility= View.GONE
                Toast.makeText(context,"${fail.message}",Toast.LENGTH_SHORT).show()
            }

    }


}