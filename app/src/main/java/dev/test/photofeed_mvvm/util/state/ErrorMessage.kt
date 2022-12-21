package dev.test.photofeed_mvvm.util.state

import com.google.gson.annotations.SerializedName

data class ErrorMessage(
    @SerializedName("errors")
    val errors : ArrayList<ErrorContent>
)

data class ErrorContent(
    @SerializedName("code")
    val code : String?,
    @SerializedName("cause")
    val cause : String?,
    @SerializedName("message")
    val message : String?
)