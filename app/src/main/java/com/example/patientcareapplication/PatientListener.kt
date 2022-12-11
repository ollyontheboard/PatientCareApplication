package com.example.patientcareapplication

import com.example.patientcareapplication.data.model.Patient

interface PatientListener {
    fun onClick(patient:Patient)
}