package com.bignerdranch.android.capstone2

import android.content.pm.PackageManager
import android.location.Location
import androidx.fragment.app.Fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import com.bignerdranch.android.capstone2.model.Photo
import com.bignerdranch.android.capstone2.viewmodel.PhotoViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

class PhotoMapsFragment : Fragment() {

    private lateinit var fusedLocationProviderClient : FusedLocationProviderClient
    private val requestNum = 1
    private lateinit var clocation : Location
    private lateinit var  photoViewModel: PhotoViewModel
    private var photos = emptyList<Photo>()
//    private val args by navArgs<PhotoMapsFragmentArgs>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        photoViewModel= ViewModelProvider(this).get(PhotoViewModel::class.java)
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
                    Toast.makeText(context,"lat : ${clocation.latitude}, lon: ${clocation.longitude}",
                            Toast.LENGTH_LONG).show()
                    Log.i("clocation","lat : ${clocation.latitude}, lon: ${clocation.longitude}")
//                    photoViewModel.fetchPhoto(clocation.latitude.toString(),clocation.longitude.toString()).observe(viewLifecycleOwner, Observer {
//                        photos = it
//                    })
                }
            }
        }
    }

    private val callback = OnMapReadyCallback { googleMap ->
        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */

        val boundsBuilder = LatLngBounds.Builder()

        photoViewModel.fetchPhoto(clocation.latitude.toString(),clocation.longitude.toString()).observe(viewLifecycleOwner, Observer {photos ->
//            photos = it
            for (cphoto in photos){
                val latLng = LatLng(cphoto.latitude.toDouble(), cphoto.longitude.toDouble())
                boundsBuilder.include(latLng)
                googleMap.addMarker(MarkerOptions().position(latLng).title(cphoto.title).snippet(cphoto.url))
                googleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(boundsBuilder.build(),1000,1000,0))
                Log.i("photo_title",cphoto.title)

            }
        })


        googleMap.setOnMarkerClickListener(object : GoogleMap.OnMarkerClickListener{
            override fun onMarkerClick(marker: Marker?): Boolean {

                val title = marker?.snippet
                    val action = PhotoMapsFragmentDirections.actionPhotoMapsFragmentToPhotoDialogFragment(title!!)
                    findNavController().navigate(action)
                return false
            }

        })




//        val sydney = LatLng(-34.0, 151.0)
//        googleMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
//        googleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(boundsBuilder.build(),1000,1000,0))

    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_photo_maps, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }
}