package com.example.patientcareapplication

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.patientcareapplication.data.model.Patient
import com.example.patientcareapplication.databinding.FragmentSearchPatientBinding


class SearchPatientFragment : Fragment(),PatientListener {

    private  val viewModel by lazy{
        ViewModelProvider(this).get(PatientSearchViewModel::class.java)

    }
    private lateinit var patientAdapter: PatientListAdapter

    private var patientList = listOf<Patient>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
       val binding = FragmentSearchPatientBinding.inflate(inflater)

        viewModel._patient.observe(viewLifecycleOwner, Observer {selectedPatients->
            patientAdapter = PatientListAdapter(selectedPatients,this)
            binding.patientRV.adapter = patientAdapter
            patientList = selectedPatients
        })

        viewModel._navigateToDetails.observe(viewLifecycleOwner, Observer { patient->
            this.findNavController().navigate(SearchPatientFragmentDirections.actionSearchPatientFragmentToIndividualPatientFragment(patient))


        })
        setHasOptionsMenu(true)


        return binding.root }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        // below line is to get our inflater
        val inflater = activity?.menuInflater

        // inside inflater we are inflating our menu file.
        if (inflater != null) {
            inflater.inflate(R.menu.search_menu, menu)
        }

        // below line is to get our menu item.
        val searchItem: MenuItem = menu.findItem(R.id.actionSearch)

        // getting search view of our item.
        val searchView: SearchView = searchItem.getActionView() as SearchView

        searchView.clearFocus()

        // below line is to call set on query text listener method.
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(newText: String?): Boolean {


                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filter(newText)
                return true

            }


        })
    }
    private fun filter(text: String?) {
        // creating a new array list to filter our data.
        val filteredlist: MutableList<Patient> = mutableListOf()

        // running a for loop to compare elements.
        for (item in patientList) {
            // checking if the entered string matched with any item of our recycler view.
            if (text != null) {
                if (item.name.lowercase().contains(text.lowercase())) {
                    // if the item is matched we are
                    // adding it to our filtered list.
                    filteredlist.add(item)
                }
            }
        }
        if (filteredlist.isEmpty()) {
            // if no item is added in filtered list we are
            // displaying a toast message as no data found.
            Toast.makeText(activity, "No Data Found..", Toast.LENGTH_SHORT).show()
            patientAdapter.filterList(filteredlist)
        } else {
            // at last we are passing that filtered
            // list to our adapter class.
            patientAdapter.filterList(filteredlist)
        }
    }

    override fun onClick(patient: Patient) {
        viewModel.onPatientClicked(patient)

    }
}