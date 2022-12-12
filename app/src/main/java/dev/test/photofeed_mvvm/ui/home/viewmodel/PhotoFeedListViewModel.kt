package dev.test.photofeed_mvvm.ui.home.viewmodel

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
class PhotoFeedListViewModel @Inject constructor(private val photosRepository: PhotosRepository) :
    ViewModel() {

    val photoFeedList = MutableLiveData<Resource<ArrayList<PhotoItem>>>()

    //default nb column for grid layout
    var spanCount = 3

    fun fetchPhotoFeed() {
        viewModelScope.launch {
            photosRepository.fetchPhotoFeed().collect(){
                photoFeedList.value = it
            }
        }
    }
}