package dev.test.photofeed_mvvm.model.local

import android.os.Parcelable
import androidx.room.Embedded
import kotlinx.parcelize.Parcelize

@Parcelize
data class Location (
    var title: String = "",
    var locationName: String = "",
    var city: String = "",
    var country: String = "",
    @Embedded
    var position: Position? = null
) : Parcelable