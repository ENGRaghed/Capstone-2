package com.bignerdranch.android.capstone2

import androidx.fragment.app.Fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

class PhotoMapsFragment : Fragment() {

    private val args by navArgs<PhotoMapsFragmentArgs>()

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

        for (cphoto in args.photos){
            val latLng = LatLng(cphoto.latitude.toDouble(), cphoto.longitude.toDouble())
            boundsBuilder.include(latLng)
            googleMap.addMarker(MarkerOptions().position(latLng).title(cphoto.title).snippet(cphoto.url))
            googleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(boundsBuilder.build(),1000,1000,0))
            Log.i("photo_title",cphoto.title)




        }
        googleMap.setOnMarkerClickListener(object : GoogleMap.OnMarkerClickListener{
            override fun onMarkerClick(marker: Marker?): Boolean {

                val title = marker?.snippet

//                    var zoomDialog= PhotoDialogFragment.newInstance(photo.url)
//
//                    zoomDialog.show(fragmentManager!!,null)

                    val action = PhotoMapsFragmentDirections.actionPhotoMapsFragmentToPhotoDialogFragment(title!!)
                    findNavController().navigate(action)

//                    zoomDialog.show()
//                val builder = AlertDialog.Builder(requireContext())
//                builder.setTitle(title)
//                builder.create()
//                builder.show()
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