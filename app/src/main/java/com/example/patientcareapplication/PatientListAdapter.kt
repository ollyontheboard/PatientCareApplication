package com.example.patientcareapplication

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.patientcareapplication.data.model.Patient
import com.example.patientcareapplication.databinding.PatientDetailsItemBinding
import com.example.patientcareapplication.generated.callback.OnClickListener

class PatientListAdapter(var patientList: List<Patient>,val clickListener: PatientListener): RecyclerView.Adapter<PatientListAdapter.ViewHolder>(){
    class ViewHolder(val binding: PatientDetailsItemBinding): RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding= PatientDetailsItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
      return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val patient = patientList[position]
        holder.binding.clicklistener = clickListener
        holder.binding.patient = patient
        holder.binding.patientImage.setImageURI(Uri.parse(patient.photo))


    }
    fun filterList(filterlist: MutableList<Patient>) {
        // below line is to add our filtered
        // list in our course array list.
        patientList = filterlist
        // below line is to notify our adapter
        // as change in recycler view data.
        notifyDataSetChanged()
    }

    override fun getItemCount() = patientList.size
}



