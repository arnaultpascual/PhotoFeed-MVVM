package dev.test.photofeed_mvvm.model.remote

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import dev.test.bruxlessphotofeed.model.local.PhotoItem
import dev.test.photofeed_mvvm.model.remote.Urls
import kotlinx.parcelize.Parcelize

@Parcelize
data class RemotePhotoListItem(
    @SerializedName("id")
    val id: String,
    @SerializedName("alt_description")
    val alt_description: String?,
    @SerializedName("blur_hash")
    val blur_hash: String,
    @SerializedName("color")
    val color: String,
    @SerializedName("exif")
    val exif: RemoteExif?,
    @SerializedName("views")
    val views: Int,
    @SerializedName("created_at")
    val created_at: String,
    @SerializedName("description")
    val description: String?,
    @SerializedName("height")
    val height: Int,
    @SerializedName("liked_by_user")
    val liked_by_user: Boolean,
    @SerializedName("likes")
    val likes: Int,
    @SerializedName("promoted_at")
    val promoted_at: String,
    @SerializedName("updated_at")
    val updated_at: String,
    @SerializedName("urls")
    val urls: Urls,
    @SerializedName("user")
    val user: User?,
    @SerializedName("width")
    val width: Int,
    @SerializedName("location")
    val location: RemoteLocation?,
    @SerializedName("downloads")
    val downloads: Int
) : Parcelable {

    fun translate() : PhotoItem{
        val localPhotoItem = PhotoItem(id = this.id, exif = null)

        if (this.user != null)
            localPhotoItem.authorName = this.user.name

        localPhotoItem.url = this.urls.regular
        localPhotoItem.urlFull = this.urls.full
        localPhotoItem.height = this.height
        localPhotoItem.width = this.width
        localPhotoItem.nbView = this.views
        localPhotoItem.nbDownload = this.downloads
        localPhotoItem.nbLikes = this.likes
        localPhotoItem.createdAt = this.created_at

        if (!this.description.isNullOrBlank())
            localPhotoItem.description = this.description
        else if (!this.alt_description.isNullOrBlank())
                localPhotoItem.description = this.alt_description
        else
            localPhotoItem.description = "Absence de description"

        if (this.exif != null){
            localPhotoItem.exif = this.exif.translate()
        }

        if (this.location != null)
            localPhotoItem.location = this.location.translate()


        return localPhotoItem
    }
}