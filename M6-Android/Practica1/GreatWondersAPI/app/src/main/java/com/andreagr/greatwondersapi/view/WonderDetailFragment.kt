package com.andreagr.greatwondersapi.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.andreagr.greatwondersapi.databinding.FragmentWonderDetailBinding
import com.andreagr.greatwondersapi.util.showImage
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WonderDetailFragment : Fragment() {

    private lateinit var _binding: FragmentWonderDetailBinding
    private val binding get() = _binding
    private val navArgs by navArgs<WonderDetailFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return FragmentWonderDetailBinding.inflate(inflater, container, false)
            .apply { _binding = this }
            .root
    }

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
}