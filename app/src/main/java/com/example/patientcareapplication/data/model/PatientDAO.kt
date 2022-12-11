package com.example.patientcareapplication.data.model

import androidx.room.*

@Dao
interface PatientDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addPatient(patient: Patient)
    @Query("SELECT * FROM patient_tabLe ORDER BY id ASC")
    fun getPatient(): List<Patient>
    @Query("UPDATE patient_table SET patient_complaint = :complaint WHERE patient_name =:name")
    fun addComplaint(complaint: String, name: String)

}