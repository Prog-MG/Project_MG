package com.example.geekfindermg

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.geekfindermg.data.memory.GeekMemoryManager
import com.example.geekfindermg.viewmodel.StoreDetailViewModel

class StoreDetailActivity : AppCompatActivity() {

    private lateinit var txtName: TextView
    private lateinit var rateStore: RatingBar
    private lateinit var txtHours: TextView
    private lateinit var txtPhone: TextView

    private var storeId: Long = -1L

    private val viewModel = StoreDetailViewModel(GeekMemoryManager.instance)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_store_detail)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        txtName = findViewById(R.id.textName_StoreD)
        rateStore = findViewById(R.id.rate_StoreD)
        txtHours = findViewById(R.id.textHour_StoreD)
        txtPhone = findViewById(R.id.textPhone_StoreD)

        val btnEdit = findViewById<Button>(R.id.btnEdit_StoreD)
        val btnDelete = findViewById<Button>(R.id.btnDelete_StoreD)

        storeId = intent.getLongExtra("STORE_ID", -1L)

        btnEdit.setOnClickListener {
            val intent = Intent(this, StoreFormActivity::class.java)
            intent.putExtra("STORE_ID", storeId)
            startActivity(intent)
        }

        btnDelete.setOnClickListener { showDeleteDialog() }
    }

    // Reloads the data every time the screen is shown (also after editing)
    override fun onResume() {
        super.onResume()
        showStore()
    }

    // READ
    private fun showStore() {
        viewModel.loadStore(storeId)
        val store = viewModel.store
        if (store == null) {
            finish()
            return
        }
        txtName.text = store.name
        rateStore.rating = store.averageRating.toFloat()
        txtHours.text = getString(R.string.TextHours_StoreDetail) + ": " + store.openingHours
        txtPhone.text = getString(R.string.TextPhone_StoreDetail) + ": " + store.phone
    }

    // DELETE
    private fun deleteStore() {
        try {
            viewModel.deleteStore()
            Toast.makeText(
                this,
                getString(R.string.MsgTextDelete_StoreDetail),
                Toast.LENGTH_LONG
            ).show()
            finish()
        } catch (e: Exception) {
            Toast.makeText(
                this,
                getString(R.string.MsgTextDeleteError_StoreDetail),
                Toast.LENGTH_LONG
            ).show()
        }
    }

    private fun showDeleteDialog() {
        DeleteStoreDialog(this) { deleteStore() }.show()
    }
}