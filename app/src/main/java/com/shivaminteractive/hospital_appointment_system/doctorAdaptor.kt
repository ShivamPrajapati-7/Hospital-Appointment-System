package com.shivaminteractive.hospital_appointment_system

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class doctorAdaptor(
    private val doctorlist: List<doctor>,
    private val onSelectClick: (doctor) -> Unit
) : RecyclerView.Adapter<doctorAdaptor.DoctorViewHolder>() {

    class DoctorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val doctorname: TextView = itemView.findViewById(R.id.doctorname)
        val speclization: TextView = itemView.findViewById(R.id.spec)
        val rating: TextView = itemView.findViewById(R.id.rating)
        val selectBtn: Button = itemView.findViewById(R.id.selectDoctorBtn)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DoctorViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.choise_card, parent, false)
        return DoctorViewHolder(view)
    }

    override fun onBindViewHolder(holder: DoctorViewHolder, position: Int) {
        val doctor = doctorlist[position]
        holder.doctorname.text = doctor.name
        holder.speclization.text = doctor.spec
        holder.rating.text = doctor.rating

        holder.selectBtn.setOnClickListener {
            onSelectClick(doctor)
        }
    }

    override fun getItemCount(): Int {
        return doctorlist.size
    }
}
