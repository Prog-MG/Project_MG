package com.example.geekfindermg

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.app.Dialog
import android.content.Context
import android.widget.Button

class AddPhotoDialog(
    context: Context,
    private val onTakePhoto: () -> Unit,
    private val onChooseGallery: () -> Unit
) : Dialog(context) {

    init {
        setContentView(R.layout.activity_dialog_add_photo)

        val btnTakePhoto = findViewById<Button>(R.id.btnTakePhoto)
        val btnGallery   = findViewById<Button>(R.id.btnGallery)
        val btnCancel    = findViewById<Button>(R.id.btnCancelPhoto)

        btnTakePhoto.setOnClickListener {
            dismiss()
            onTakePhoto()
        }

        btnGallery.setOnClickListener {
            dismiss()
            onChooseGallery()
        }

        btnCancel.setOnClickListener {
            dismiss()
        }
    }
}