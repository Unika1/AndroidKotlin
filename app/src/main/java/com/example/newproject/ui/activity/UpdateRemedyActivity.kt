package com.example.newproject.ui.activity

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.newproject.databinding.ActivityUpdateRemedyBinding
import com.example.newproject.model.RemedyModel
import com.example.newproject.repository.RemedyRepositoryImpl
import com.example.newproject.viewmodel.RemedyAddingViewModel

class UpdateRemedyActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUpdateRemedyBinding
    private lateinit var remedyAddingViewModel: RemedyAddingViewModel
    private lateinit var remedy: RemedyModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityUpdateRemedyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize ViewModel
        val repo = RemedyRepositoryImpl()
        remedyAddingViewModel = RemedyAddingViewModel(repo)

//        // Get Remedy Data from Intent
//        val remedy: RemedyModel? = intent.getParcelableExtra("remedy")
//        if (remedy == null) {
//            Toast.makeText(this, "Remedy data is missing", Toast.LENGTH_SHORT).show()
//            finish()
//            return
//        }
        remedy = intent.getParcelableExtra("remedy") ?: run {
            Toast.makeText(this, "Remedy data is missing", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        // Pre-fill form fields with existing remedy data
        binding.etCustomerName.setText(remedy.title)  // Title
        binding.etCustomerEmail.setText(remedy.description) // Description
        binding.etCustomerPhone.setText(remedy.procedure) // Procedure

        binding.btnUpdateBooking.setOnClickListener {
            if (binding.etCustomerName.text.isNullOrEmpty() ||
                binding.etCustomerEmail.text.isNullOrEmpty() ||
                binding.etCustomerPhone.text.isNullOrEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Update Remedy Data
            remedy.title = binding.etCustomerName.text.toString()
            remedy.description = binding.etCustomerEmail.text.toString()
            remedy.procedure = binding.etCustomerPhone.text.toString()

            // Call ViewModel to update remedy
            remedyAddingViewModel.updateRemedy(remedy)
            Toast.makeText(this, "Remedy updated!", Toast.LENGTH_SHORT).show()
            finish()
        }

        // Handle Edge Insets for UI Adjustments
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}
