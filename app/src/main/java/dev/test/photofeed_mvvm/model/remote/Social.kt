package dev.test.photofeed_mvvm.model.remote

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Social(
    @SerializedName("instagram_username")
    val instagram_username: String,
    @SerializedName("paypal_email")
    val paypal_email: String,
    @SerializedName("portfolio_url")
    val portfolio_url: String,
    @SerializedName("twitter_username")
    val twitter_username: String
) : Parcelable