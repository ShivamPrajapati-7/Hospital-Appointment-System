package com.shivaminteractive.hospital_appointment_system
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore

class ChoiseDoctorFragment : Fragment() {
    private lateinit var recyclerview: RecyclerView
    private lateinit var adaptor: doctorAdaptor
    private val doctorList = mutableListOf<doctor>()
    private val db = FirebaseFirestore.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_choise_doctor, container, false)

        recyclerview = view.findViewById(R.id.choisedoctorrecyclerview)
        recyclerview.layoutManager = LinearLayoutManager(requireContext())
        
        adaptor = doctorAdaptor(doctorList) { selectedDoctor ->
            val fragment = appointment_schedule_Fragment().apply {
                arguments = Bundle().apply {
                    putString("DOCTOR_UID", selectedDoctor.doctorUid)
                    putString("docter_name", selectedDoctor.name)
                    putString("docter_spec", selectedDoctor.spec)
                    putString("docter_rating", selectedDoctor.rating)
                }
            }
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.choisedoctor, fragment)
                .addToBackStack(null)
                .commit()
        }
        recyclerview.adapter = adaptor

        getdoctor()

        return view
    }

    private fun getdoctor() {
        db.collection("users").whereEqualTo("role", "doctor").get()
            .addOnSuccessListener { success ->
                doctorList.clear()
                for (document in success) {
                    val doctor = document.toObject(doctor::class.java).copy(doctorUid = document.id)
                    doctorList.add(doctor)
                }
                adaptor.notifyDataSetChanged()
            }
            .addOnFailureListener { fail ->
                Toast.makeText(
                    requireContext(),
                    "Error: ${fail.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }
}
