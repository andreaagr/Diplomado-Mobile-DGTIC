package com.andreagr.greatwondersapi.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.andreagr.greatwondersapi.R
import com.andreagr.greatwondersapi.databinding.FragmentWonderDetailBinding
import com.andreagr.greatwondersapi.util.showImage
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class WonderDetailFragment : Fragment(), OnMapReadyCallback {

    private lateinit var _binding: FragmentWonderDetailBinding
    private val binding get() = _binding
    private val navArgs by navArgs<WonderDetailFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val view = FragmentWonderDetailBinding.inflate(inflater, container, false)
            .apply { _binding = this }
            .root
        val mapFragment = childFragmentManager.findFragmentById(R.id.googleMap) as SupportMapFragment?
        mapFragment?.getMapAsync(this)
        return view
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val location = if (navArgs.greatWonder.location.city.isNotEmpty())
            navArgs.greatWonder.location.city + ", " + navArgs.greatWonder.location.country
        else
            navArgs.greatWonder.location.country
        with(binding) {
            showImage(navArgs.greatWonder.image, wonderDetailImageView, root.context)
            showImage(navArgs.greatWonder.location.flagImage, flagImageView, root.context)
            wonderTitleTextView.text = navArgs.greatWonder.name
            wonderDescriptionTextView.text = navArgs.greatWonder.description
            wonderDetailLocationTextView.text = location
        }
    }

    override fun onMapReady(p0: GoogleMap) {
        val latLng = LatLng(
            navArgs.greatWonder.location.coords.latitude,
            navArgs.greatWonder.location.coords.longitude
        )
        val markerOptions = MarkerOptions()
            .position(latLng)
            .title(navArgs.greatWonder.name)

        p0.addMarker(markerOptions)
        p0.animateCamera(
            CameraUpdateFactory.newLatLngZoom(latLng, 18f),
            4000,
            null
        )
    }
}