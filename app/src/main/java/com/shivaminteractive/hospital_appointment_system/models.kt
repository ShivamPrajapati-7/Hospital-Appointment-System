package com.shivaminteractive.hospital_appointment_system

data class Datamodel (
    val dayName:String,
    val dayNumber:String,
    val fulldate:String
)
data class TimeSlotModel(
    val time:String,
    val isBooked: Boolean
)