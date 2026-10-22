package com.shivaminteractive.hospital_appointment_system

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.RadioButton
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class appointment_schedule_Fragment : Fragment(R.layout.fragment_appointment_schedule_) {

    private lateinit var db: FirebaseFirestore
    private lateinit var auth: FirebaseAuth

    private var doctorUid: String = ""
    private var selectedDate: String = ""
    private var selectedTime: String = ""
    private var selectedAppointmentType: String = "In-clinic Visit"
    private var doctorBaseSlots: List<String> = emptyList()

    private val currentWeekStart: Calendar = Calendar.getInstance()

    // UI Views
    private lateinit var doctname: TextView
    private lateinit var doctspec: TextView
    private lateinit var doctrating: TextView
    private lateinit var rvDates: RecyclerView
    private lateinit var rvTimeSlots: RecyclerView
    private lateinit var bookbtn: Button
    private lateinit var rbClinic: RadioButton
    private lateinit var rbVideo: RadioButton

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        db = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()

        // 1. Retrieve arguments passed from ChoiseDoctorFragment
        doctorUid = arguments?.getString("DOCTOR_UID") ?: ""
        val doctorName = arguments?.getString("docter_name") ?: ""
        val doctorSpec = arguments?.getString("docter_spec") ?: ""
        val doctorRating = arguments?.getString("docter_rating") ?: ""

        // 2. Bind Views
        doctname = view.findViewById(R.id.Doctor_name_appointment)
        doctspec = view.findViewById(R.id.docter_spec_appointment)
        doctrating = view.findViewById(R.id.doctor_rating_appointment)
        rvDates = view.findViewById(R.id.dateselector)
        rvTimeSlots = view.findViewById(R.id.time)
        bookbtn = view.findViewById(R.id.bookbtn)
        rbClinic = view.findViewById(R.id.rbClinic)
        rbVideo = view.findViewById(R.id.rbVideo)

        // Set doctor info texts
        doctname.text = doctorName
        doctspec.text = doctorSpec
        doctrating.text = doctorRating

        // Setup RecyclerViews
        rvDates.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        rvTimeSlots.layoutManager = GridLayoutManager(requireContext(), 3)

        // 3. Default base slots
        doctorBaseSlots = listOf(
            "09:00 AM", "09:30 AM", "10:00 AM",
            "10:30 AM", "11:00 AM", "11:30 AM",
            "12:00 PM", "12:30 PM", "01:00 PM",
            "04:00 PM", "04:30 PM", "05:00 PM",
            "05:30 PM", "06:00 PM"
        )
        generateWeekDates()

        // 4. Appointment Type Selection
        rbClinic.isChecked = true
        rbClinic.setOnClickListener { selectedAppointmentType = "In-clinic Visit" }
        rbVideo.setOnClickListener { selectedAppointmentType = "Video Consultation" }

        // 5. Book Button Listener
        bookbtn.setOnClickListener {
            bookAppointment()
        }
    }

    private fun generateWeekDates() {
        val dateList = mutableListOf<Datamodel>()
        val tempCal = currentWeekStart.clone() as Calendar

        val dayNameFormat = SimpleDateFormat("EEE", Locale.getDefault())
        val dayNumFormat = SimpleDateFormat("d", Locale.getDefault())
        val fullDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

        repeat(7) {
            dateList.add(
                Datamodel(
                    dayName = dayNameFormat.format(tempCal.time),
                    dayNumber = dayNumFormat.format(tempCal.time),
                    fulldate = fullDateFormat.format(tempCal.time)
                )
            )
            tempCal.add(Calendar.DAY_OF_YEAR, 1)
        }

        selectedDate = dateList[0].fulldate
        selectedTime = ""
        fetchBookedSlotsForDate(selectedDate)

        rvDates.adapter = dateadaptor(dateList) { clickedDate ->
            selectedDate = clickedDate
            selectedTime = ""
            fetchBookedSlotsForDate(selectedDate)
        }
    }

    private fun fetchBookedSlotsForDate(date: String) {
        if (doctorUid.isEmpty()) {
            val slotModels = doctorBaseSlots.map { time ->
                TimeSlotModel(time = time, isBooked = false)
            }
            rvTimeSlots.adapter = timeAdaptor(slotModels) { clickedTime ->
                selectedTime = clickedTime
            }
            return
        }

        db.collection("appointments")
            .whereEqualTo("doctorUid", doctorUid)
            .whereEqualTo("date", date)
            .get()
            .addOnSuccessListener { querySnapshot ->
                val bookedTimes = querySnapshot.documents.mapNotNull { it.getString("time") }.toSet()

                val slotModels = doctorBaseSlots.map { time ->
                    TimeSlotModel(time = time, isBooked = bookedTimes.contains(time))
                }

                rvTimeSlots.adapter = timeAdaptor(slotModels) { clickedTime ->
                    selectedTime = clickedTime
                }
            }
            .addOnFailureListener {
                val slotModels = doctorBaseSlots.map { time ->
                    TimeSlotModel(time = time, isBooked = false)
                }
                rvTimeSlots.adapter = timeAdaptor(slotModels) { clickedTime ->
                    selectedTime = clickedTime
                }
            }
    }

    private fun bookAppointment() {
        if (selectedDate.isEmpty() || selectedTime.isEmpty()) {
            Toast.makeText(requireContext(), "Please select both a date and a time slot", Toast.LENGTH_SHORT).show()
            return
        }

        val patientUid = auth.currentUser?.uid ?: "anonymous"
        val appointmentDocId = "${doctorUid}_${selectedDate}_${selectedTime.replace(" ", "")}"

        bookbtn.isEnabled = false

        db.collection("users").document(patientUid).get()
            .addOnSuccessListener { success ->
                val patientName = success.getString("fullname") ?: "Unknown Patient"

                val appointmentData = hashMapOf(
                    "doctorUid" to doctorUid,
                    "patientUid" to patientUid,
                    "patient_Name" to patientName,
                    "date" to selectedDate,
                    "time" to selectedTime,
                    "type" to selectedAppointmentType,
                    "status" to "confirmed",
                    "createdAt" to FieldValue.serverTimestamp()
                )

                db.collection("appointments").document(appointmentDocId)
                    .set(appointmentData)
                    .addOnSuccessListener {
                        Toast.makeText(requireContext(), "Appointment Booked Successfully!", Toast.LENGTH_LONG).show()
                        requireActivity().finish()
                    }
                    .addOnFailureListener { e ->
                        bookbtn.isEnabled = true
                        Toast.makeText(requireContext(), "Booking failed: ${e.message}", Toast.LENGTH_SHORT).show()
                    }
            }
            .addOnFailureListener { e ->
                bookbtn.isEnabled = true
                Toast.makeText(requireContext(), "Failed to get patient info: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }
}
