package com.example.newproject.ui.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newproject.R
import com.example.newproject.adapter.RemedyAdapter
import com.example.newproject.databinding.ActivityMyRemedyBinding
import com.example.newproject.model.RemedyModel
import com.example.newproject.repository.RemedyRepositoryImpl
import com.example.newproject.utils.LoadingUtils
import com.example.newproject.viewmodel.RemedyAddingViewModel

class MyRemedyActivity : AppCompatActivity() {
    lateinit var binding: ActivityMyRemedyBinding
    lateinit var remedyAddingViewModel: RemedyAddingViewModel
    lateinit var adapter: RemedyAdapter
    lateinit var loadingUtils: LoadingUtils



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMyRemedyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadingUtils = LoadingUtils(this)

        val repo = RemedyRepositoryImpl()
        remedyAddingViewModel = RemedyAddingViewModel(repo)

        setupRecyclerView()
        observeViewModel()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun setupRecyclerView() {
        adapter = RemedyAdapter(
            remedyList = emptyList(),
            onUpdateClick = { remedy -> openUpdateDialog(remedy) },
            onDeleteClick = { remedyId -> onDeleteRemedy(remedyId) }
        )

        binding.recyclerViewRemedies.apply {
            layoutManager = LinearLayoutManager(this@MyRemedyActivity)
            adapter = this@MyRemedyActivity.adapter
        }
    }

    private fun openUpdateDialog(remedy: RemedyModel) {
        val intent = Intent(this, UpdateRemedyActivity::class.java)
        intent.putExtra("remedy", remedy)
        startActivity(intent)
    }

    private fun observeViewModel() {
        remedyAddingViewModel.remediesList.observe(this) { remedies ->
            adapter.updateList(remedies)
        }

        remedyAddingViewModel.remedyStatus.observe(this) { status ->
            loadingUtils.dismiss()
            Toast.makeText(this, status, Toast.LENGTH_SHORT).show()
        }
    }

    private fun onDeleteRemedy(remedyId: String) {
        loadingUtils.show()
        remedyAddingViewModel.deleteRemedy(remedyId)
    }


}