package dev.test.photofeed_mvvm.ui.detail.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.test.photofeed_mvvm.data.repository.PhotosRepository
import dev.test.photofeed_mvvm.model.local.PhotoItem
import dev.test.photofeed_mvvm.util.state.Resource
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PhotoDetailViewModel @Inject constructor(private val photosRepository: PhotosRepository) :
    ViewModel() {

    lateinit var chosenPhotoFromList : PhotoItem
    val chosenPhotoWithExif = MutableLiveData<Resource<PhotoItem>>()

    fun fetchPhotoFromGivenId() {
        viewModelScope.launch {
            photosRepository.fetchPhotoFromGivenId(chosenPhotoFromList.id).collect(){
                chosenPhotoWithExif.value = it
            }
        }
    }

}