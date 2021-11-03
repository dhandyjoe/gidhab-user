package com.dhandyjoe.gidhabapp.model

import android.os.Parcel
import android.os.Parcelable

data class User (
    val id: Int,
    val login: String,
    val avatar_url: String
)
