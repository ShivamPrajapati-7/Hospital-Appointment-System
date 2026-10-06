package com.shivaminteractive.hospital_appointment_system

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Intent
import android.health.connect.datatypes.units.BloodGlucose
import android.os.Bundle
import android.provider.MediaStore
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
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.util.Calendar

class Patient_registration_Fragment : Fragment() {


    //initlization
    lateinit var name: EditText
    lateinit var btnpatientregistration: Button
    lateinit var concent: CheckBox
    lateinit var dob: EditText
    lateinit var Gender_group: RadioGroup
    lateinit var email_input: EditText
    lateinit var BloodGroup: Spinner
    lateinit var patient_phone: EditText
    lateinit var patient_password: EditText
    lateinit var patient_confirm_password: EditText
    lateinit var progressBar: ProgressBar
    var ispass=false
     var gender:String=" "
    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()


    @SuppressLint("MissingInflatedId", "SuspiciousIndentation")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view: View=inflater.inflate(R.layout.fragment_patient_registration_, container, false)

        //finding id
        btnpatientregistration=view.findViewById(R.id.btnpatientregistation)
        name =view.findViewById(R.id.patient_Full_name)
        concent=view.findViewById(R.id.concent)
        dob = view.findViewById(R.id.patient_Date_Of_Birth)
        email_input=view.findViewById(R.id.email_input)
        BloodGroup=view.findViewById(R.id.BloodGroup)
        patient_phone=view.findViewById(R.id.patient_phone)
        patient_password=view.findViewById(R.id.patient_password)
        patient_confirm_password=view.findViewById(R.id.patient_confirm_password)
        Gender_group = view.findViewById(R.id.patient_gender_group)
        progressBar=view.findViewById(R.id.progressBar)

        //Date of Birth
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

        //Password Checker
        if(patient_confirm_password.text==patient_password.text) {
            ispass=true
        }




        //Gender Group
        Gender_group.setOnCheckedChangeListener { group, checkedId ->

            val selectedRadioButton =group.findViewById<RadioButton>(checkedId)

            val selectedGender = selectedRadioButton.text.toString()
            gender=selectedGender

        }


        //Registration Button
        btnpatientregistration.setOnClickListener {
            progressBar.visibility= View.VISIBLE
            val fullname = name.text.toString().trim()
            val email = email_input.text.toString().trim()
            val phoneNumber = patient_phone.text.toString().trim()
            val date_of_birth = dob.text.toString().trim()
            val bloodgroup = BloodGroup.selectedItem.toString().trim()
            val pass = patient_password.text.toString().trim()
            val conf_pass= patient_confirm_password.text.toString().trim()


            registerPatient(fullname, email, phoneNumber, date_of_birth, bloodgroup, pass, conf_pass)

        }
        return view
    }

    private fun registerPatient( fullname:String, email: String, phoneNumber: String, date_of_birth: String, bloodgroup: String,pass:String,conf_pass:String) {

        auth.createUserWithEmailAndPassword(email,pass)
            .addOnSuccessListener { sucess->
                val uid=sucess.user!!.uid

                val patientData = hashMapOf(
                    "fullname" to fullname,
                    "email" to email,
                    "phoneNumber" to phoneNumber,
                    "date_of_birth" to date_of_birth,
                    "bloodgroup" to bloodgroup,
                    "gender" to gender,
                    "role" to "patient")

                db.collection("users").document(uid).set(patientData)
                    .addOnSuccessListener { success ->
                        progressBar.visibility= View.GONE
                        startActivity(Intent(context, patient_dashboard_activity::class.java))

                    }
                    .addOnFailureListener { fail->
                        progressBar.visibility= View.GONE
                        Toast.makeText(context, "Firestore : ${fail.message}", Toast.LENGTH_SHORT).show()
                    }


            }
            .addOnFailureListener { fail->
                progressBar.visibility= View.GONE
                Toast.makeText(context, "FireAuth : ${fail.message}", Toast.LENGTH_SHORT).show()

            }


    }

}