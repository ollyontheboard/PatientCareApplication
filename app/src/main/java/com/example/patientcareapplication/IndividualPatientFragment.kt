package com.example.patientcareapplication

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.patientcareapplication.databinding.FragmentIndividualPatientBinding


class IndividualPatientFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentIndividualPatientBinding.inflate(inflater)

        binding.lifecycleOwner = this

        val patient = IndividualPatientFragmentArgs.fromBundle(arguments!!).patient
        binding.patient = patient
        binding.patientImage.setImageURI(Uri.parse(patient.photo))

        return binding.root


    }


}