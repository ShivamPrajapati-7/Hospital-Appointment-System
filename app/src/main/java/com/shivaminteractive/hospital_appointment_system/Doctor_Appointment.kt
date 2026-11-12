package com.shivaminteractive.hospital_appointment_system

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class Doctor_Appointment(
    private val patientList: List<patientData>,
    private val onselectClick: (patientData) -> Unit
) : RecyclerView.Adapter<Doctor_Appointment.patientViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): patientViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.appointment_card, parent, false)
        return patientViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: patientViewHolder,
        position: Int
    ) {
        val patient = patientList[position]
        holder.patientname.text = patient.name
        holder.time.text = patient.time

        holder.forwardBtn?.setOnClickListener {
            onselectClick(patient)
        }
        holder.itemView.setOnClickListener {
            onselectClick(patient)
        }
    }

    override fun getItemCount(): Int {
        return patientList.size
    }

    class patientViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val patientname: TextView = itemView.findViewById(R.id.patientname_appointmentcard)
        val time: TextView = itemView.findViewById(R.id.time)
        val forwardBtn: ImageButton? = itemView.findViewById(R.id.forward)
    }
}
