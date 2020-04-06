package com.example.joshtalksflickr

import android.content.Context
import android.content.Intent
import android.os.Parcel
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.joshtalksflickr.api.Photo
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_photo.view.*

class PhotoAdapter(val context: Context, var photos: List<Photo>):
    RecyclerView.Adapter<PhotoAdapter.SRViewHolder>() {

    private val inflater: LayoutInflater
            = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    class SRViewHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun getItemViewType(position: Int): Int {
        if(position < photos.size)
            return 0
        else return 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SRViewHolder{
        val inflater = inflater
        if (viewType == 0)
        return SRViewHolder(inflater.inflate(R.layout.item_photo, parent, false))
        return SRViewHolder(inflater.inflate(R.layout.layout_footer, parent, false))
    }
    override fun getItemCount(): Int {
        return photos.size + 1
    }

    override fun onBindViewHolder(holder: SRViewHolder, position: Int) {
        if(holder.itemViewType == 1) return
        val photo = photos[position]
        val farm = photo.farm
        val secret = photo.secret
        val id = photo.id
        val server = photo.server

        val url = "https://farm$farm.staticflickr.com/$server/${id}_$secret.jpg"

        Picasso.with(context).load(url).into(holder.itemView.image)
        holder.itemView.image.setOnClickListener {

            context.startActivity(context.FullPhotoActivityIntent(url))
        }
        holder.itemView.photo_title.text = photo.title
    }

}

// change 1

// change 2

// change 3

// change to squash 11

// change 11
