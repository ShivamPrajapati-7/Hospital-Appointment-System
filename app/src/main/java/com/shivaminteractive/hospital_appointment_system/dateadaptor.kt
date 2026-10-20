package com.shivaminteractive.hospital_appointment_system

import android.graphics.Color
import android.icu.text.Transliterator
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import kotlinx.coroutines.selects.select

class dateadaptor(
    private val dateList: List<Datamodel>,
    private val ondateselected:(String)-> Unit
) : RecyclerView.Adapter<dateadaptor.DateViewHolder>(){

    private var selectedPosition=0
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DateViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.choise_appointment_date_card,parent,false)
        return DateViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: DateViewHolder,
        position: Int
    ) {
         val item=dateList[position]
        holder.carddate.setCardBackgroundColor(Color.parseColor("#0D6EFD"))
        holder.dayname.setTextColor(Color.parseColor("#E0E5EC"))
        holder.date.setTextColor(Color.WHITE)

        if(selectedPosition== position)
        {
            holder.carddate.setCardBackgroundColor(Color.parseColor("#0D6EFD"))
            holder.dayname.setTextColor(Color.parseColor("#E0E5EC"))
            holder.date.setTextColor(Color.WHITE)
        }
        else{
            holder.carddate.setCardBackgroundColor(Color.parseColor("#F4F6F9"))
            holder.dayname.setTextColor(Color.parseColor("#6C757D"))
            holder.date.setTextColor(Color.parseColor("#0D1B2A"))
        }

        holder.itemView.setOnClickListener {
            val previousPosition = selectedPosition
            selectedPosition = holder.bindingAdapterPosition
            notifyItemChanged(previousPosition)
            notifyItemChanged(selectedPosition)
            ondateselected(item.fulldate)
        }
    }

    override fun getItemCount(): Int {
        return dateList.size
    }

    class DateViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
    {
        val carddate: MaterialCardView =itemView.findViewById(R.id.carddate)
        val dayname: TextView=itemView.findViewById(R.id.dayname)
        val date: TextView=itemView.findViewById(R.id.date)
    }



}