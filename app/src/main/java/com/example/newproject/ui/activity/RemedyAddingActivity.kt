package com.example.newproject.ui.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import com.example.newproject.R
import com.example.newproject.databinding.ActivityRemedyAddingBinding
import com.example.newproject.model.RemedyModel
import com.example.newproject.repository.RemedyRepositoryImpl
import com.example.newproject.utils.LoadingUtils
import com.example.newproject.viewmodel.RemedyAddingViewModel

class RemedyAddingActivity : AppCompatActivity() {
    lateinit var binding: ActivityRemedyAddingBinding
    lateinit var remedyViewModel: RemedyAddingViewModel
    lateinit var loadingUtils: LoadingUtils
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityRemedyAddingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadingUtils = LoadingUtils(this)

        val repo = RemedyRepositoryImpl()
        remedyViewModel = RemedyAddingViewModel(repo)

        remedyViewModel.remedyStatus.observe(this, Observer { status ->
            loadingUtils.dismiss()
            Toast.makeText(this, status, Toast.LENGTH_SHORT).show()

            if (status == "Remedy added successfully!") {
                navigateToMyRemedyActivity()
            }
        })
        binding.btnBookTable.setOnClickListener {
            val title = binding.etRname.text.toString().trim()
            val description = binding.etdesc.text.toString().trim()
            val procedure = binding.etprocedure.text.toString().trim()

            if (title.isEmpty() || description.isEmpty() || procedure.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val remedy = RemedyModel(
                remedyId = "",
                title = title,
                description = description,
                procedure = procedure
            )

            loadingUtils.show()
            remedyViewModel.addRemedy(remedy)
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun navigateToMyRemedyActivity() {
        val intent = Intent(this, MyRemedyActivity::class.java)
        startActivity(intent)
        finish()
    }
}