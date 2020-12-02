package com.bignerdranch.android.capstone2

import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.navArgs
import com.squareup.picasso.Picasso

class PhotoDialogFragment : DialogFragment() {

    private val args by navArgs<PhotoDialogFragmentArgs>()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.zoom_in_photo, container, false)
        val photoZoom : ImageView = view.findViewById(R.id.zoom_photo)

//        val fileName = arguments?.getSerializable("PHOTO_URI") as String
        val fileName = args.url


        Picasso.get().load(fileName).placeholder(R.drawable.ic_baseline_add_photo_alternate_24)
                .error(R.drawable.ic_launcher_background)
                .into(photoZoom)

        Log.i("Url : ",fileName)

//        Picasso.get().load(photoItem.url)
//                .placeholder(R.drawable.bill_up_close)
//                .error(R.drawable.ic_launcher_foreground)
//                .into(holder.itemView.image)
//        photoZoom.setImageBitmap(BitmapFactory.decodeFile(requireContext().filesDir.path + "/" + fileName))

        return view
    }

    companion object {
        fun newInstance(photoFileName: String): PhotoDialogFragment {
            val frag = PhotoDialogFragment()
            val args = Bundle()
            args.putSerializable("PHOTO_URI", photoFileName)
            frag.arguments = args
            return frag
        }
    }
}