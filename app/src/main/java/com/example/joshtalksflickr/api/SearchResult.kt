package com.example.joshtalksflickr.api


import android.annotation.SuppressLint
import kotlinx.android.parcel.Parcelize
import android.os.Parcelable

@SuppressLint("ParcelCreator")
@Parcelize
data class SearchResult(
    val photos: Photos,
    val stat: String
) : Parcelable


@SuppressLint("ParcelCreator")
@Parcelize
data class Photos(
    val page: Int,
    val pages: String,
    val perpage: Int,
    val photo: List<Photo>,
    val total: String
) : Parcelable


@SuppressLint("ParcelCreator")
@Parcelize
data class Photo(
    val farm: Int,
    val id: String,
    val isfamily: Int,
    val isfriend: Int,
    val ispublic: Int,
    val owner: String,
    val secret: String,
    val server: String,
    val title: String
) : Parcelable

