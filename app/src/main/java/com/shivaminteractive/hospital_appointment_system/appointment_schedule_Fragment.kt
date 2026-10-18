package com.shivaminteractive.hospital_appointment_system

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.DatePicker
import android.widget.TextView
import androidx.core.graphics.scaleMatrix
import androidx.navigation.fragment.findNavController
import java.util.Calendar


class appointment_schedule_Fragment : Fragment() {

    lateinit var select_date: TextView
    lateinit var bookbtn: Button
    lateinit var doctname: TextView
    lateinit var doctspec: TextView
    lateinit var doctrating: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =inflater.inflate(R.layout.fragment_appointment_schedule_, container, false)

        doctname=view.findViewById(R.id.Doctor_name_appointment)
        doctspec=view.findViewById(R.id.docter_spec_appointment)
        doctrating=view.findViewById(R.id.doctor_rating_appointment)

        val doctername=arguments?.getString("docter_name")
        val doctersepc=arguments?.getString("docter_spec")
        val docterrating=arguments?.getString("docter_rating")

        doctname.text=doctername.toString()
        doctspec.text=doctersepc.toString()
        doctrating.text=docterrating.toString()
        return view
    }

         fun showDatePicker() {

            val calendar = Calendar.getInstance()

            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePicker = context?.let {
                DatePickerDialog(
                    it,
                    { _, selectedYear, selectedMonth, selectedDay ->

                        val selectedDate =
                            "$selectedDay/${selectedMonth + 1}/$selectedYear"

                        select_date.text = selectedDate

                    },
                    year,
                    month,
                    day
                )
            }

            datePicker?.datePicker?.minDate = System.currentTimeMillis()

             datePicker?.show()
        }
    }

