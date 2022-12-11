package com.example.patientcareapplication.data.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import androidx.versionedparcelable.ParcelField
import androidx.versionedparcelable.VersionedParcelize

@Entity(tableName = "patient_table")
@Parcelize
data class Patient(
    @PrimaryKey(autoGenerate = true)
    var id: Long? =null,
    @ColumnInfo(name = "patient_name")
    var name: String,
    @ColumnInfo(name = "patient_age")
    var age: String,
    @ColumnInfo(name = "patient_address")
    var address: String,
    @ColumnInfo(name = "patient_photo")
    var photo: String?,
    @ColumnInfo(name = "patient_complaint")
    var complaint:String?
        ):Parcelable
