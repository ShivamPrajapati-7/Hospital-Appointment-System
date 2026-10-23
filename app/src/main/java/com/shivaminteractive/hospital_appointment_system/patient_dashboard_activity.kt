package com.shivaminteractive.hospital_appointment_system

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth

class patient_dashboard_activity : AppCompatActivity() {

    private var uid: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_patient_dashboard)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        uid = intent.getStringExtra("uid") ?: FirebaseAuth.getInstance().currentUser?.uid

        if (savedInstanceState == null) {
            loadFragment(createDashboardFragment())
        }

        val navigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        navigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    loadFragment(createDashboardFragment())
                    true
                }
                R.id.nav_schadule -> {
                    loadFragment(all_Appointment_Fragment())
                    true
                }
                R.id.nav_chat -> {
                    loadFragment(chatFragment())
                    true
                }
                R.id.nav_profile -> {
                    loadFragment(ProfileFragment())
                    true
                }
                else -> false
            }
        }
    }

    private fun createDashboardFragment(): Fragment {
        return patient_dashboard_Fragment().apply {
            arguments = Bundle().apply {
                putString("uid", uid)
            }
        }
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.patient_fragment_container, fragment)
            .commit()
    }
}
