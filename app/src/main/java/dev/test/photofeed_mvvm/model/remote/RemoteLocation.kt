package dev.test.photofeed_mvvm.model.remote

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import dev.test.bruxlessphotofeed.model.local.Location
import kotlinx.parcelize.Parcelize

@Parcelize
data class RemoteLocation (
    @SerializedName("title")
    val title: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("city")
    val city: String?,
    @SerializedName("country")
    val country: String?,
    @SerializedName("position")
    val position: RemotePosition?
) : Parcelable {

    fun translate() : Location{
        val localLocation = Location()
        if (this.title != null)
            localLocation.title = title
        if (this.name != null)
            localLocation.locationName = name
        if (this.city != null)
            localLocation.city = city
        if (this.country != null)
            localLocation.country = country
        if (this.position != null)
            localLocation.position = position.translate()

        return localLocation
    }
}