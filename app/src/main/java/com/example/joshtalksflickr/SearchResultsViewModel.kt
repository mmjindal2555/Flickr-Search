package com.example.joshtalksflickr

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.ItemKeyedDataSource
import androidx.paging.PagedList
import com.example.joshtalksflickr.api.Photo


class SearchResultsViewModel: ViewModel() {

    val photos: MutableLiveData<MutableList<Photo>> = MutableLiveData()

    fun addPhotos(photos: List<Photo>) {
        this.photos.value?.addAll(photos)
    }
}

