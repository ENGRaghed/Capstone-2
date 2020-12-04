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
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
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
import kotlinx.android.synthetic.main.fragment_search_map.*

class SearchMapFragment : Fragment() {
    private lateinit var fusedLocationProviderClient : FusedLocationProviderClient
    private val requestNum = 1
    private lateinit var clocation : Location
    private lateinit var  photoViewModel: PhotoViewModel



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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


                }
            }
        }
    }

    private val callback = OnMapReadyCallback { googleMap ->

        photoViewModel= ViewModelProvider(this).get(PhotoViewModel::class.java)


        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        val sydney = LatLng(clocation.latitude, clocation.longitude)
//        googleMap.addMarker(MarkerOptions().position(sydney).title("my location"))
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney,10f))

        googleMap.setOnMapLongClickListener {LatLng ->
//            val action = SearchMapFragmentDirections
//                .actionSearchMapFragmentToSearchPhotoGalleryFragment(LatLng.latitude.toString(),LatLng.longitude.toString())
//            findNavController().navigate(action)
            googleMap.clear()
            photoViewModel.fetchPhoto(LatLng.latitude.toString(),LatLng.longitude.toString()).observe(viewLifecycleOwner, Observer {photos ->
                val boundsBuilder = LatLngBounds.Builder()
                for (photo in photos){
                    val latLng = LatLng(photo.latitude.toDouble(), photo.longitude.toDouble())
                    boundsBuilder.include(latLng)
//            googleMap.addMarker(MarkerOptions().position(latLng).title(photo.title))
                    googleMap.addMarker(MarkerOptions().position(latLng).title(photo.title).snippet(photo.url))
                    googleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(boundsBuilder.build(),1000,1000,0))

                    floatingButton.visibility = View.VISIBLE
                    floatingButton.setOnClickListener {
                        val action = SearchMapFragmentDirections
                            .actionSearchMapFragmentToSearchPhotoGalleryFragment(LatLng.latitude.toString(),LatLng.longitude.toString())
                        findNavController().navigate(action)
                    }

                }
            })
        }



        googleMap.setOnMarkerClickListener(object : GoogleMap.OnMarkerClickListener{
            override fun onMarkerClick(marker: Marker?): Boolean {

                val url = marker?.snippet
                val action = SearchMapFragmentDirections.actionSearchMapFragmentToPhotoDialogFragment(url!!)
                findNavController().navigate(action)

                return false
            }

        })
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_search_map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }
}