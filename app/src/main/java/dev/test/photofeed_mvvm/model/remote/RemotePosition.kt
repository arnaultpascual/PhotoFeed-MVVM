package dev.test.photofeed_mvvm.model.remote

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import dev.test.photofeed_mvvm.model.local.Position
import kotlinx.parcelize.Parcelize

@Parcelize
data class RemotePosition(
    @SerializedName("latitude")
    val latitude: Double?,
    @SerializedName("longitude")
    val longitude: Double?
) : Parcelable {

    fun translate() : Position {
        val localPosition= Position()

        if (this.latitude != null)
            localPosition.latitude = this.latitude
        if (this.longitude != null)
            localPosition.longitude = this.longitude

        return localPosition
    }
}
