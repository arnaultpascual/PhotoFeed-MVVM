package dev.test.photofeed_mvvm.ui.home.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.test.photofeed_mvvm.data.repository.PhotosRepository
import javax.inject.Inject

@HiltViewModel
class PhotoFeedListViewModel @Inject constructor(private val photosRepository: PhotosRepository) :
    ViewModel() {


}