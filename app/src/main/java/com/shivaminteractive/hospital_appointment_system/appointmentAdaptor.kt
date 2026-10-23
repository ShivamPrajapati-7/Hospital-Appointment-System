package com.shivaminteractive.hospital_appointment_system

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class appointmentAdaptor(
    private val appointmentList: List<appointment>,
    private val onSelectClick: (appointment) -> Unit
) : RecyclerView.Adapter<appointmentAdaptor.appointmentViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): appointmentViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.all_appointment_card, parent, false)
        return appointmentViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: appointmentViewHolder,
        position: Int
    ) {
        val appointment = appointmentList[position]
        holder.doctername.text = appointment.docter_name
        holder.docterspec.text = appointment.docter_spec
        holder.appointmentTime.text = appointment.time
        holder.hospitalname.text = appointment.hospital

        holder.itemView.setOnClickListener {
            onSelectClick(appointment)
        }
    }

    override fun getItemCount(): Int {
        return appointmentList.size
    }

    class appointmentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val doctername: TextView = itemView.findViewById(R.id.all_appointments_docter_name)
        val docterspec: TextView = itemView.findViewById(R.id.all_appointments_docter_spec)
        val appointmentTime: TextView = itemView.findViewById(R.id.all_appointment_time)
        val hospitalname: TextView = itemView.findViewById(R.id.all_appointments_hospital)
    }
}
