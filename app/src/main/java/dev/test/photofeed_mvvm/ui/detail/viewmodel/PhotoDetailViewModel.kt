package dev.test.photofeed_mvvm.ui.detail.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.test.photofeed_mvvm.data.repository.PhotosRepository
import dev.test.photofeed_mvvm.model.local.PhotoItem
import javax.inject.Inject

@HiltViewModel
class PhotoDetailViewModel @Inject constructor(private val photosRepository: PhotosRepository) :
    ViewModel() {

    lateinit var chosenPhotoFromList : PhotoItem
}