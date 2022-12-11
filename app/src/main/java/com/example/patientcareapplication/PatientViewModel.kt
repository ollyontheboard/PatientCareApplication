package com.example.patientcareapplication

import android.app.Application
import androidx.lifecycle.*
import com.example.patientcareapplication.data.model.Patient
import com.example.patientcareapplication.data.model.PatientDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PatientViewModel(application: Application) :AndroidViewModel(application) {

    private val database = PatientDatabase.getInstance(application)
private suspend fun addPatient(patient: Patient){
withContext(Dispatchers.IO){

    database.patientDao.addPatient(patient)
}


}

     fun validateEnteredData(patient: Patient): Boolean {
      if(patient.name.isEmpty()|| patient.address.isEmpty()|| patient.age.isEmpty()|| patient.photo.isNullOrEmpty()){


            return false
        }
         else{
             viewModelScope.launch {
                 addPatient(patient)
             }

            return true
         }

    }
    fun validateComplaintEntered(complaint: String?, name: String){
        if(complaint.isNullOrEmpty()){
            return
        }
        else{
            viewModelScope.launch {
                addComplaint(complaint, name)

            }

        }
    }

    private suspend fun addComplaint(complaint: String, name: String) {
        withContext(Dispatchers.IO){
            database.patientDao.addComplaint(complaint,name)
        }

    }
}