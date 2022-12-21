package dev.test.photofeed_mvvm.model.local

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Position(
    @SerializedName("latitude")
    var latitude: Double = 0.0,
    @SerializedName("longitude")
    var longitude: Double = 0.0
) : Parcelable
