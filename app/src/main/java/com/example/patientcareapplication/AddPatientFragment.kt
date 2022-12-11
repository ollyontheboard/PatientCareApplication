package com.example.patientcareapplication

import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.ContentResolver
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.provider.SyncStateContract.Helpers.insert
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.patientcareapplication.data.model.Patient
import com.example.patientcareapplication.databinding.FragmentAddPatientBinding
import java.io.File


class AddPatientFragment : Fragment() {
    private  val viewModel by lazy{
        ViewModelProvider(this).get(PatientViewModel::class.java)

    }
    private val pickImage = 100
    private val takeImage = 101
    private val imageCaptureCode = 1001
    private var imageUri: Uri? = null
    private val permissionCode = 102
    private var uriString: String? = null
    lateinit var binding: FragmentAddPatientBinding

    private val takeImageResult = registerForActivityResult(ActivityResultContracts.TakePicture()) { isSuccess ->
        if (isSuccess) {
            imageUri?.let { uri ->
                binding.patientImage.setImageURI(uri)
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

       binding = FragmentAddPatientBinding.inflate(inflater)
        binding.uploadBtn.setOnClickListener {

            val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            startActivityForResult(gallery, pickImage)
        }
        binding.cameraBtn.setOnClickListener {
            takeImage()


//            if (requireActivity().checkSelfPermission(Manifest.permission.CAMERA)==PackageManager.PERMISSION_DENIED
//                || requireActivity().checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)==
//                PackageManager.PERMISSION_DENIED
//
//            ){
//                val permission = arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
//                requestPermissions(permission, permissionCode)
//            }
//            else{
//                val values = ContentValues()
//                values.put(MediaStore.Images.Media.TITLE, "New Picture")
//                values.put(MediaStore.Images.Media.DESCRIPTION, "From the camera")
//                imageUri = activity?.contentResolver?.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
//                val camera = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
//                camera.putExtra(MediaStore.EXTRA_OUTPUT,takeImage)
//                startActivityForResult(camera,takeImage)
//            }

        }
        binding.submitButton.setOnClickListener {
            validateUserInput()
        }
        binding.searchButton.setOnClickListener {

            this.findNavController().navigate(R.id.action_addPatientFragment_to_searchPatientFragment)
        }
        return binding.root
        



    }

    private fun checkpermissions() {



    }

     fun openCamera(){



    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when(requestCode){
            permissionCode->
                if(grantResults.isNotEmpty() && grantResults[0]==PackageManager.PERMISSION_GRANTED ){
                    openCamera()

                }
            else
                {
                    Toast.makeText(activity,"Permissions not granted",Toast.LENGTH_SHORT).show()

                }
        }
    }

    fun validateUserInput(){
        val patient = Patient(null, binding.patientNameTxt.text.toString(),binding.patientAgeTxt.text.toString(),
            binding.patientAddressText.text.toString(),uriString,null)
        if(!viewModel.validateEnteredData(patient)
        ){
            Toast.makeText(requireActivity(), "Please Complete the form", Toast.LENGTH_SHORT).show()
        }
        else{
            this.findNavController().navigate(AddPatientFragmentDirections.actionAddPatientFragmentToPatientDetailFragment(patient))



        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == pickImage) {
            imageUri = data?.data
            uriString = imageUri.toString()

            binding.patientImage.setImageURI(imageUri)
        }


    }

    private fun takeImage() {
        lifecycleScope.launchWhenStarted {
            getTmpFileUri().let { uri ->
               imageUri= uri
                uriString = imageUri.toString()
                takeImageResult.launch(uri)
            }
        }
    }

    private fun getTmpFileUri(): Uri {
        val tmpFile = File.createTempFile("tmp_image_file", ".png").apply {
            createNewFile()
            deleteOnExit()

    }
        return FileProvider.getUriForFile(requireContext(), "${BuildConfig.APPLICATION_ID}.provider", tmpFile)
    }


}