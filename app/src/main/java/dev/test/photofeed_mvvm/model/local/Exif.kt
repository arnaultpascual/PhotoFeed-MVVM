package dev.test.photofeed_mvvm.model.local

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Exif(
    var aperture: String = "",
    var exposure_time: String = "",
    var focal_length: String = "",
    var iso: Int = -999999,
    var make: String = "",
    var model: String = "",
    var name: String  = ""
) : Parcelable