package dev.test.photofeed_mvvm.model.remote

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import dev.test.photofeed_mvvm.model.remote.Athletics
import kotlinx.parcelize.Parcelize

@Parcelize
data class TopicSubmissions(
    @SerializedName("athletics")
    val athletics: Athletics
) : Parcelable