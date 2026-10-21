package com.shivaminteractive.hospital_appointment_system

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView

class timeAdaptor(
    private val slotList: List<TimeSlotModel>,
    private val ontimeselected:(String)->Unit
): RecyclerView.Adapter<timeAdaptor.TimeViewHolder>(){
    class TimeViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val cardtime: MaterialCardView=itemView.findViewById(R.id.timecard)
        val time: TextView=itemView.findViewById(R.id.time)
    }
    private var selectedPosition =-1
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TimeViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.choise_appointment_time_card,parent,false)
        return TimeViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: TimeViewHolder,
        position: Int
    ) {
        val slot=slotList[position]
        holder.time.text=slot.time

        when{
            slot.isBooked->
            {
                holder.cardtime.setCardBackgroundColor(Color.parseColor("#E9ECEF"))
                holder.cardtime.strokeWidth = 0
                holder.time.setTextColor(Color.parseColor("#ADB5BD"))
                holder.itemView.isEnabled = false
            }
            selectedPosition==position->{
                holder.cardtime.setCardBackgroundColor(Color.parseColor("#0D6EFD"))
                holder.cardtime.strokeWidth = 0
                holder.time.setTextColor(Color.WHITE)
                holder.itemView.isEnabled = true
            }
            else -> {
                holder.cardtime.setCardBackgroundColor(Color.WHITE)
                holder.cardtime.strokeWidth = 2
                holder.cardtime.strokeColor = Color.parseColor("#E0E5EC")
                holder.time.setTextColor(Color.parseColor("#0D1B2A"))
                holder.itemView.isEnabled = true
            }

        }
        holder.itemView.setOnClickListener {
            val previousPosition=selectedPosition
            selectedPosition=holder.bindingAdapterPosition
            notifyItemChanged(previousPosition)
            notifyItemChanged(selectedPosition)
            ontimeselected(slot.time)
        }

    }

    override fun getItemCount(): Int {
        return slotList.size
    }


}