package com.example.newproject.repository

import com.example.newproject.model.RemedyModel
import com.google.firebase.database.DatabaseReference

interface RemedyRepository {
    fun addRemedy(remedy: RemedyModel, database: DatabaseReference, callback: (Boolean, String?) -> Unit)
    fun getRemedies(database: DatabaseReference, callback: (List<RemedyModel>?, String?) -> Unit)
    fun updateRemedy(remedy: RemedyModel, database: DatabaseReference, callback: (Boolean, String?) -> Unit)
    fun deleteRemedy(remedyId: String, database: DatabaseReference, callback: (Boolean, String?) -> Unit)
}