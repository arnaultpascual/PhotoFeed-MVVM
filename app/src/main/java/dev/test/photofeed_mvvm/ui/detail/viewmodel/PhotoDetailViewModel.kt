package dev.test.photofeed_mvvm.ui.detail.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.test.photofeed_mvvm.data.repository.PhotosRepository
import dev.test.photofeed_mvvm.model.local.Exif
import dev.test.photofeed_mvvm.model.local.PhotoItem
import dev.test.photofeed_mvvm.model.local.Position
import dev.test.photofeed_mvvm.util.formatApiDateToRfc1123
import dev.test.photofeed_mvvm.util.state.Resource
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PhotoDetailViewModel @Inject constructor(private val photosRepository: PhotosRepository) :
    ViewModel() {

    lateinit var chosenPhotoFromList : PhotoItem
    var position : Position? = null
    val chosenPhotoWithExif = MutableLiveData<Resource<PhotoItem>>()

    fun fetchPhotoFromGivenId() {
        viewModelScope.launch {
            photosRepository.fetchPhotoFromGivenId(chosenPhotoFromList.id).collect(){
                chosenPhotoWithExif.value = it
            }
        }
    }

    /**
     * Return a formatted string with all Exif data given in the [PhotoItem]
     *
     * @param exif : [Exif]
     * @return [String]
     */
    fun getExifString(exif: Exif) : String{
        var aperture = ""
        if (exif.aperture != "")
            aperture = "f/${exif.aperture} - "

        var exposureTime = ""
        if (exif.exposure_time != "")
            exposureTime = exif.exposure_time + " - "

        var focalLength = ""
        if (exif.focal_length != "")
            focalLength = exif.focal_length + " - "

        var iso = ""
        if (exif.iso != -999999)
            iso = "ISO${exif.iso}"

        var shotInfo = aperture + exposureTime + focalLength + iso

        if (shotInfo == "")
            shotInfo = "Détails non fournis"

        return shotInfo
    }

    /**
     * Return a formatted string with the location name
     * We assume that if there's no title we can put the name
     *
     * return default string all is null
     *
     * @param photoItem : [PhotoItem]
     * @return [String]
     */
    fun getLocationDataTitle(photoItem: PhotoItem) : String {
        return if (photoItem.location != null)
            if (photoItem.location!!.title != "")
                photoItem.location!!.title
            else if (photoItem.location!!.locationName != "")
                photoItem.location!!.locationName
            else
                "Pas de nom de lieu"
        else
            "Pas de nom de lieu"
    }

    /**
     * Get the [Position] inside the [PhotoItem].[Location] by asserting non null [Location]
     * @param photoItem : [PhotoItem]
     * @return [Position]?
     */
    fun getPosition(photoItem: PhotoItem) : Position?{
        var position : Position? = null
        if (photoItem.location != null){
            position = photoItem.location!!.position
        }
        this.position = position

        return position
    }

    /**
     * Get an apiDate and formatting it to the RFC 1123 format (a more human readable)
     * @param apiDate : [String]
     * @return [String]?
     */
    fun getHumanReadableDate(apiDate : String) : String {
        return formatApiDateToRfc1123(apiDate.subSequence(0, apiDate.length - 1).toString())
            ?: "Erreur conversion de date"
    }


}