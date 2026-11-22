package com.example.geekfindermg

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.app.Dialog
import android.content.Context
import android.widget.Button

class DeleteStoreDialog(
    context: Context,
    private val onConfirm: () -> Unit
) : Dialog(context) {

    init {
        setContentView(R.layout.activity_dialog_delete_store)

        val btnConfirm = findViewById<Button>(R.id.btnConDelete)
        val btnCancel  = findViewById<Button>(R.id.btnCancelDelete)

        btnConfirm.setOnClickListener {
            dismiss()
            onConfirm()
        }

        btnCancel.setOnClickListener {
            dismiss()
        }
    }
}