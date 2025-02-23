package com.example.newproject.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.newproject.model.RemedyModel
import com.example.newproject.repository.RemedyRepository
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class RemedyViewModel (val repository: RemedyRepository):ViewModel(){
    val database: DatabaseReference = FirebaseDatabase.getInstance().reference

    val remedyStatus = MutableLiveData<String>()
    val remediesList = MutableLiveData<List<RemedyModel>>()

    fun addRemedy(remedy: RemedyModel) {
        repository.addRemedy(remedy, database) { success, error ->
            if (success) {
                remedyStatus.postValue("Remedy added successfully!")
            } else {
                remedyStatus.postValue("Error: $error")
            }
        }
    }
    fun getRemedies() {
        repository.getRemedies(database) { remedies, error ->
            if (remedies != null) {
                remediesList.postValue(remedies)
            } else {
                remedyStatus.postValue(error ?: "Error fetching remedies")
            }
        }
    }
    fun updateRemedy(remedy: RemedyModel) {
        repository.updateRemedy(remedy, database) { success, error ->
            if (success) {
                remedyStatus.postValue("Remedy updated successfully!")
            } else {
                remedyStatus.postValue("Error: $error")
            }
        }
    }
    // Delete a remedy by remedyId
    fun deleteRemedy(remedyId: String) {
        repository.deleteRemedy(remedyId, database) { success, error ->
            if (success) {
                // Remove the deleted remedy from the current remedies list
                val currentList = remediesList.value?.toMutableList() ?: mutableListOf()
                val remedyToRemove = currentList.find { it.remedyId == remedyId }
                if (remedyToRemove != null) {
                    currentList.remove(remedyToRemove)  // Remove the remedy from the list
                }

                // Update the LiveData with the modified list
                remediesList.postValue(currentList)

                // Notify that the remedy was deleted
                remedyStatus.postValue("Remedy deleted successfully!")
            } else {
                remedyStatus.postValue("Error: $error")
            }
        }
    }
}