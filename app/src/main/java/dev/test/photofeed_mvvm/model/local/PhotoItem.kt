package dev.test.photofeed_mvvm.model.local

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class PhotoItem (
    @NonNull
    @PrimaryKey
    val id : String,
    var description : String = "",
    var authorName : String = "",
    var url : String = "",
    var urlFull : String = "",
    var width : Int = 0,
    var height : Int = 0,
    var createdAt : String = "",
    var nbView : Int = 0,
    var nbDownload : Int = 0,
    var nbLikes : Int = 0,
    @Embedded
    var exif: Exif? = null,
    @Embedded
    var location: Location? = null

) : Parcelable {

    @Transient
    @IgnoredOnParcel
    var isAlreadyDraw: Boolean = false

    fun getResFromSizeInMpx() : Double {
        return ((width*height)/1000000).toDouble()
    }
}
