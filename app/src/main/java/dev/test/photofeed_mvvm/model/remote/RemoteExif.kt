package dev.test.photofeed_mvvm.model.remote

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import dev.test.photofeed_mvvm.model.local.Exif
import kotlinx.parcelize.Parcelize

@Parcelize
data class RemoteExif(
    @SerializedName("aperture")
    val aperture: String?,
    @SerializedName("exposure_time")
    val exposure_time: String?,
    @SerializedName("focal_length")
    val focal_length: String?,
    @SerializedName("iso")
    val iso: Int?,
    @SerializedName("make")
    val make: String?,
    @SerializedName("model")
    val model: String?,
    @SerializedName("name")
    val name: String?
) : Parcelable {

    fun translate() : Exif {

        val localExif = Exif()
        if (this.aperture != null)
            localExif.aperture = this.aperture
        if (this.exposure_time != null)
            localExif.exposure_time = this.exposure_time
        if (this.focal_length != null)
            localExif.focal_length = this.focal_length
        if (this.iso != null)
            localExif.iso = this.iso
        if (this.make != null)
            localExif.make = this.make
        if (this.model != null)
            localExif.model = this.model
        if (this.name != null)
            localExif.name = this.name

        return localExif
    }
}