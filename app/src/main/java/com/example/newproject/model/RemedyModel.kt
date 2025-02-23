package com.example.newproject.model

import android.os.Parcel
import android.os.Parcelable

class RemedyModel(
    var remedyId: String = "",
    var title: String = "",
    var description: String = "",
    var procedure: String = "",
    var category: String = "", // Hair Care, Face Care, Body Care
    var imageUrl: String = "", // Cloudinary Image URL
) : Parcelable {
    constructor(parcel: Parcel) : this (
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(remedyId)
        parcel.writeString(title)
        parcel.writeString(description)
        parcel.writeString(procedure)
        parcel.writeString(category)
        parcel.writeString(imageUrl)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<RemedyModel> {
        override fun createFromParcel(parcel: Parcel): RemedyModel {
            return RemedyModel(parcel)
        }

        override fun newArray(size: Int): Array<RemedyModel?> {
            return arrayOfNulls(size)
        }
    }
}