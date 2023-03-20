package com.example.recetapp.ui

import android.app.AlertDialog
import androidx.fragment.app.FragmentActivity
import com.example.recetapp.databinding.InternetFragmentDialogBinding

class InternetFragmentDialog(
    private val activity: FragmentActivity,
    private val message: String
) {

    private val alertDialog  by lazy { createLoadingDialog(message) }
    private lateinit var binding: InternetFragmentDialogBinding

    private fun createLoadingDialog(message: String) : AlertDialog {
        val builder = AlertDialog.Builder(activity)
        val inflater = activity.layoutInflater
        builder.setView(
            InternetFragmentDialogBinding.inflate(inflater).apply {
                binding = this
            }.root
        )
        binding.button.setOnClickListener { alertDialog.dismiss() }
        binding.textView.text = message
        return builder.create()
    }

    fun startDialog(){
        alertDialog.show()
    }
}