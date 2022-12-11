package com.example.patientcareapplication

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.patientcareapplication.databinding.FragmentAddPatientBinding
import com.example.patientcareapplication.databinding.FragmentPatientDetailBinding


class PatientDetailFragment : Fragment() {

    private  val viewModel by lazy{
        ViewModelProvider(this).get(PatientViewModel::class.java)

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentPatientDetailBinding.inflate(inflater)

        binding.lifecycleOwner = this

        val patient = PatientDetailFragmentArgs.fromBundle(arguments!!).newPatient
        binding.patient = patient
        binding.patientImage.setImageURI(Uri.parse(patient.photo))

        binding.submitComplaintBtn.setOnClickListener {
            patient.complaint = binding.patientComplainttxt.text.toString()
            viewModel.validateComplaintEntered(patient.complaint!!,patient.name)
            this.findNavController().navigate(R.id.action_patientDetailFragment_to_addPatientFragment)
        }


        return binding.root
    }


}