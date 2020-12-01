package com.bignerdranch.android.capstone2

import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.service.controls.actions.FloatAction
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bignerdranch.android.capstone2.model.Photo
import com.bignerdranch.android.capstone2.viewmodel.PhotoViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_photo_gallery.*


class PhotoGalleryFragment : Fragment() {

    private lateinit var fusedLocationProviderClient : FusedLocationProviderClient
    private val requestNum = 1
    private lateinit var clocation : Location
    private lateinit var  photoViewModel: PhotoViewModel
    lateinit var recyclerView: RecyclerView
    private var photo = emptyList<Photo>()
    private var adapter = PhotoAdapter(photo)



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment


        val view = inflater.inflate(R.layout.fragment_photo_gallery, container, false)
        photoViewModel= ViewModelProvider(this).get(PhotoViewModel::class.java)
        recyclerView = view.findViewById(R.id.rvPhotos)
        recyclerView.layoutManager = GridLayoutManager(context,3)

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        if (ActivityCompat.checkSelfPermission(requireActivity(),android.Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED&&
                ActivityCompat.checkSelfPermission(requireContext(),android.Manifest.permission.ACCESS_COARSE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),requestNum)
        }else{
            //main logic
            val task = fusedLocationProviderClient.lastLocation
            task.addOnSuccessListener { location ->
                if (location != null){
                    clocation = location
                    Toast.makeText(context,"lat : ${clocation.latitude}, lon: ${clocation.longitude}",Toast.LENGTH_LONG).show()
                    Log.i("clocation","lat : ${clocation.latitude}, lon: ${clocation.longitude}")
                    photoViewModel.fetchPhoto(clocation.latitude.toString(),clocation.longitude.toString()).observe(viewLifecycleOwner, Observer {
                        adapter.setData(it)
                        photo = it
                    })
                }
            }
        }
        val button = view.findViewById<FloatingActionButton>(R.id.go_to_map)

        button.setOnClickListener {
            /*
             val action = TaskListFragmentDirections.actionTaskListFragmentToTaskDetailsFragment(taskList[position])
                holder.itemView.findNavController().navigate(action)
             */
            val action = PhotoGalleryFragmentDirections.actionPhotoGalleryFragmentToPhotoMapsFragment(
                photos = photo.toTypedArray())
            findNavController().navigate(action)
        }

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