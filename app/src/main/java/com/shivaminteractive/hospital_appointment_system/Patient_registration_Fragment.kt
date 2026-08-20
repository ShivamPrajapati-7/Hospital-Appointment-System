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
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Spinner
import android.widget.TextView
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
    var ispass=false
     var gender:String=" "


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

                    val bloodgroup=BloodGroup.selectedItem.toString()
                   val status = "Patient"
                   val fullname = name.text.toString()
                   (activity as Registration_Actvity).takedata(fullname,gender,email_input.text.toString(),patient_phone.text.toString(),bloodgroup,status)


        }






        return view
    }

}