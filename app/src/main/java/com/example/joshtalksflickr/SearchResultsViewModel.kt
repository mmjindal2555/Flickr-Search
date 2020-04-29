package com.example.joshtalksflickr

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.demolib.api.Photo


class SearchResultsViewModel: ViewModel() {

    val photos: MutableLiveData<MutableList<Photo>> = MutableLiveData()

    fun addPhotos(photos: List<Photo>) {
        this.photos.value?.addAll(photos)
    }
}

