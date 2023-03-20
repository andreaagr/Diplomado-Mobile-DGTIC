package com.example.recetapp.ui.recipedetails

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import com.example.recetapp.databinding.FragmentRecipeSummaryBinding
import com.example.recetapp.util.formatFromHTML

class RecipeSummaryFragment : Fragment() {

    private var _binding: FragmentRecipeSummaryBinding? = null
    private val binding get() = _binding!!
    private val summary: String
        get() = requireArguments().getString(ARG_SUMMARY) ?: ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return FragmentRecipeSummaryBinding.inflate(inflater, container, false)
            .apply { _binding = this }
            .root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.summaryDetailsTextView.text = formatFromHTML(summary)
    }

    companion object {
        private const val ARG_SUMMARY = "argSummaryText"

        fun newInstance(summary: String) = RecipeSummaryFragment().apply {
            arguments = bundleOf(
                ARG_SUMMARY to summary
            )
        }
    }
}