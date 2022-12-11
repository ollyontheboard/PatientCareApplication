package com.example.patientcareapplication.data.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [Patient::class], version = 6, exportSchema = false)
abstract class PatientDatabase: RoomDatabase() {
    abstract val patientDao : PatientDAO
    companion object{
        @Volatile
        private var INSTANCE : PatientDatabase? = null

        fun getInstance(context: Context): PatientDatabase{
            synchronized(PatientDatabase::class.java){
                var instance = INSTANCE
                if(instance==null){
                    instance = Room.databaseBuilder(context.applicationContext,
                        PatientDatabase::class.java,"patient_database")
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }

                return instance
            }
        }
    }

}