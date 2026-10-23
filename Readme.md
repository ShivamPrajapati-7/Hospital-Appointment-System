# Hospital Appointment System

An Android application built with Kotlin, Material Design 3, and Firebase (Authentication & Cloud Firestore). It connects patients with doctors, allowing seamless doctor discovery, slot selection, appointment booking, and appointment management.

---

## 📱 Features Completed So Far

### 1. 🔐 User Authentication & Role-Based Access
- **Dual User Roles**: Support for **Patient** and **Doctor** user types.
- **Firebase Authentication**: Secure email/password login and registration.
- **Registration Flow**: Dedicated fragments for Patient (`Patient_registration_Fragment`) and Doctor (`DoctorRagistrationFragement`) registration.
- **Firestore User Profiles**: User information (Name, Email, Role, License Number, Phone, DOB, etc.) stored in the `users` collection.
- **Role Redirection**: Automatic routing upon login to either Patient Dashboard or Doctor Dashboard based on user role.

### 2. 🏠 Patient Dashboard
- **Fragment-Based Architecture**: Modular `patient_dashboard_Fragment` managed inside `patient_dashboard_activity`.
- **Personalized Header**: Dynamic greeting displaying the patient's full name fetched from Firestore.
- **Quick Action Banners**: One-tap access to Book Appointment, Medical Records, and Prescriptions.
- **Upcoming Appointment Preview**: Displays next scheduled appointment details (Doctor Name, Specialization, Date/Time, Confirmation Status).
- **Health Tools**: Quick access cards for Health Tips, Precautions, and Feedback.
- **Bottom Navigation**: Tab navigation between Dashboard, Appointments List, Chat, and Profile.

### 3. 👨‍⚕️ Doctor Discovery & Selection
- **Doctor List Screen**: `ChoiseDoctorFragment` displays all active doctors fetched in real-time from Firestore (`role == "doctor"`).
- **Custom Doctor Cards**: `doctorAdaptor` displays doctor name, specialization, rating, and avatar.
- **Seamless Navigation**: Passing doctor credentials (`doctorUid`, name, specialization, rating) directly into the scheduling screen.

### 4. 📅 Smart Appointment Scheduling
- **Dynamic Week Date Selector**: `dateadaptor` generates horizontal week dates (`Datamodel`) with active selection state.
- **Grid Time Slot Picker**: `timeAdaptor` provides grid-based time slots (e.g., 09:00 AM – 06:00 PM).
- **Double-Booking Prevention**: Automatically queries Firestore for booked slots under selected doctor & date, disabling already reserved slots in real-time.
- **Consultation Type**: Toggle between **In-Clinic Visit** and **Video Consultation**.
- **Atomic Booking Writes**: Generates composite document IDs (`doctorUid_date_time`) and saves complete booking data (Doctor UID, Patient UID, Patient Full Name, Date, Time, Type, Status, Timestamp) to Firestore.

### 5. 📋 All Appointments View
- **Patient Appointment History**: `all_Appointment_Fragment` lists all appointments for the logged-in patient.
- **Custom Appointment Cards**: `appointmentAdaptor` displays doctor details, specialization, appointment date/time, and hospital name.
- **Firestore Filtering**: Real-time Firestore queries filtered by `patientUid`.

### 6. 🩺 Doctor Dashboard
- Dedicated doctor activity (`Doctor_DashBoard_Activity`) with bottom navigation menu for managing doctor schedules and patient consultations.

---

## 🛠️ Tech Stack & Architecture

- **Language**: Kotlin
- **UI Framework**: Android XML Views, Material Design 3 Components, ViewBinding
- **Architecture**: Single-Activity / Multi-Fragment Pattern, RecyclerView Adapters, ViewHolders
- **Backend Services**:
  - **Firebase Authentication**: User identity & session management
  - **Cloud Firestore**: Real-time NoSQL database
- **Build System**: Gradle Version Catalog (`libs.versions.toml`), Android Gradle Plugin (AGP 9.3.1)

---

## 📁 Project Structure

```text
com.shivaminteractive.hospital_appointment_system/
├── MainActivity.kt                      # Welcome / Splash Screen
├── Login_Activity.kt                    # Authentication Screen
├── Registration_Actvity.kt              # Role Selection & Registration Container
├── Patient_registration_Fragment.kt      # Patient Registration Form
├── DoctorRagistrationFragement.kt       # Doctor Registration Form
├── patient_dashboard_activity.kt        # Patient Shell Activity (Bottom Nav)
├── patient_dashboard_Fragment.kt         # Patient Home / Dashboard View
├── BookAppointmentActivity.kt           # Appointment Flow Container
├── ChoiseDoctorFragment.kt              # Doctor List Screen
├── appointment_schedule_Fragment.kt     # Date, Time Slot & Booking Confirmation
├── all_Appointment_Fragment.kt          # Patient Appointments List Screen
├── Doctor_DashBoard_Activity.kt         # Doctor Home / Dashboard Activity
├── doctor.kt                            # Data Model: Doctor
├── models.kt                            # Data Models: Datamodel, TimeSlotModel
├── appointment.kt                       # Data Model: Appointment
├── doctorAdaptor.kt                     # Adapter for Doctor List
├── dateadaptor.kt                       # Adapter for Date Selector
├── timeAdaptor.kt                       # Adapter for Time Slots
└── appointmentAdaptor.kt                # Adapter for Appointments List
```

---

## 🚀 Running the Project

1. Clone the repository into Android Studio.
2. Ensure Firebase project is configured with `google-services.json` in the `app/` directory.
3. Sync Gradle project dependencies.
4. Build and run on an Android Emulator or physical device (API Level 24+).

---

## 🔮 Upcoming Features

- 💬 **Real-Time Patient-Doctor Chat**: Direct messaging system for post-appointment queries and consultations.
- 📄 **Digital Medical Records & Prescriptions**: Upload, store, and view PDF/Image prescriptions and diagnostic reports.
- 🔔 **Push Notifications**: Automated appointment reminders, status updates, and doctor confirmations via Firebase Cloud Messaging (FCM).
- 📹 **Video Consultation Integration**: In-app video calling support for remote medical consultations.
- 💳 **Payment Gateway Integration**: Secure online payment processing for appointment booking fees.
- 👤 **Profile Management**: Profile editing, avatar upload, and medical history updates for both Patients and Doctors.

---

## 👨‍💻 Author

Developed with ❤️ by **Shivam**

- **Project**: Hospital Appointment System

---

## 🙏 Thank You!

Thank you for exploring the **Hospital Appointment System** project! We appreciate your interest and support. If you have any suggestions or feedback, feel free to reach out. Happy coding!
