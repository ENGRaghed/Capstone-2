package com.bignerdranch.android.capstone2

import android.location.Location
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bignerdranch.android.capstone2.model.Photo
import com.bignerdranch.android.capstone2.viewmodel.PhotoViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.squareup.picasso.Picasso


class SearchPhotoGalleryFragment : Fragment() {

    private lateinit var fusedLocationProviderClient : FusedLocationProviderClient
    private val requestNum = 1
    private lateinit var clocation : Location
    private lateinit var  photoViewModel: PhotoViewModel
    lateinit var recyclerView: RecyclerView
    private var photo = emptyList<Photo>()
    private var adapter = PhotoAdapter(photo)
    private val args by navArgs<SearchPhotoGalleryFragmentArgs>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_search_photo_gallery, container, false)


        photoViewModel= ViewModelProvider(this).get(PhotoViewModel::class.java)
        recyclerView = view.findViewById(R.id.rvSearchPhoto)
        recyclerView.layoutManager = GridLayoutManager(context,3)
        photoViewModel.fetchPhotoWithRadius(args.lat, args.lon,args.radius).observe(viewLifecycleOwner, Observer {
            adapter.setData(it)
        })

        recyclerView.adapter = adapter


        return view
    }

    private inner class PhotoAdapter( private var photos : List<Photo>) : RecyclerView.Adapter<PhotoAdapter.PhotoHolder>(){
        private inner class PhotoHolder(view: View): RecyclerView.ViewHolder(view){
            lateinit var photo: Photo
            var photoImage: ImageView = view.findViewById(R.id.image_view)

            fun bind(photo: Photo){
                this.photo= photo
                Picasso.get().load(photo.url).into(photoImage)

            }

        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoHolder {
            val view = layoutInflater.inflate(R.layout.list_item_fragment, parent,false)

            return PhotoHolder(view)
        }

        override fun getItemCount() = photos.size

        override fun onBindViewHolder(holder: PhotoHolder, position: Int) {
            var photo = this.photos[position]
            holder.bind(photo)
            Log.i("GeoLatLon","#$position :${photo.title} = ${photo.latitude} , ${photo.longitude}")

        }
        fun setData(photos: List<Photo>){
            this.photos = photos
            notifyDataSetChanged()
        }
    }


}