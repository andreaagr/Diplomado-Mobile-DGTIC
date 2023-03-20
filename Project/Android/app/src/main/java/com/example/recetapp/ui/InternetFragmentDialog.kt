package com.example.recetapp.ui

import android.app.AlertDialog
import androidx.fragment.app.FragmentActivity
import com.example.recetapp.databinding.InternetFragmentDialogBinding

class InternetFragmentDialog(private val activity: FragmentActivity) {

    private val alertDialog  by lazy { createLoadingDialog() }
    private lateinit var binding: InternetFragmentDialogBinding

    private fun createLoadingDialog() : AlertDialog {
        val builder = AlertDialog.Builder(activity)
        val inflater = activity.layoutInflater
        builder.setView(
            InternetFragmentDialogBinding.inflate(inflater).apply {
                binding = this
            }.root
        )
        binding.button.setOnClickListener { alertDialog.dismiss() }
        return builder.create()
    }

    fun startDialog(){
        alertDialog.show()
    }
}