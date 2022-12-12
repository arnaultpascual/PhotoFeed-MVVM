package dev.test.photofeed_mvvm.model.remote

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


@Parcelize
data class Links(
    @SerializedName("download")
    val download: String,
    @SerializedName("download_location")
    val download_location: String,
    @SerializedName("html")
    val html: String,
    @SerializedName("self")
    val self: String
) : Parcelable