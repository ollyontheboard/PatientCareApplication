package com.example.patientcareapplication

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.patientcareapplication.data.model.Patient
import com.example.patientcareapplication.data.model.PatientDatabase
import com.example.patientcareapplication.data.model.SingleLiveEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PatientSearchViewModel(application: Application): AndroidViewModel(application) {
    private var patient_ = MutableLiveData<List<Patient>>()

    var _patient : LiveData<List<Patient>> = patient_
    private var navigateToDetails = SingleLiveEvent<Patient>()
    var _navigateToDetails: SingleLiveEvent<Patient> = navigateToDetails


    private val database = PatientDatabase.getInstance(application)
    init{
        viewModelScope.launch {
            getPatients()
        }
    }

    private suspend fun getPatients() {
        withContext(Dispatchers.IO){
            patient_.postValue(database.patientDao.getPatient())
        }

    }
    fun onPatientClicked(patient: Patient){
        navigateToDetails.value = patient
    }


}