package com.andreagr.greatwondersapi

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.andreagr.greatwondersapi.databinding.FragmentWonderDetailBinding

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
        val location = navArgs.greatWonder.wonderLocation.city + ", " + navArgs.greatWonder.wonderLocation.country
        with(binding) {
            showImage(navArgs.greatWonder.image, wonderDetailImageView, root.context)
            showImage(navArgs.greatWonder.wonderLocation.flagImage, flagImageView, root.context)
            wonderTitleTextView.text = navArgs.greatWonder.name
            wonderDescriptionTextView.text = navArgs.greatWonder.description
            wonderDetailLocationTextView.text = location
        }
    }
}