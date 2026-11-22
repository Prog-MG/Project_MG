package com.example.geekfindermg

import android.app.Dialog
import android.content.Context
import android.widget.Button

class dialog_update_store(
    context: Context,
    private val onConfirm: () -> Unit
) : Dialog(context) {

    init {
        setContentView(R.layout.activity_dialog_update_store)

        val btnConfirm = findViewById<Button>(R.id.btnConUpdate)
        val btnCancel  = findViewById<Button>(R.id.btnCancelUpdate)

        btnConfirm.setOnClickListener {
            dismiss()
            onConfirm()
        }

        btnCancel.setOnClickListener {
            dismiss()
        }
    }
}