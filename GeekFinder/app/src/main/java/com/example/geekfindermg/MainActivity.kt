package com.example.geekfindermg

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.geekfindermg.data.memory.GeekMemoryManager
import com.example.geekfindermg.domain.model.StoreFilter
import com.example.geekfindermg.viewmodel.StoreListViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var edtSearch: EditText

    private val viewModel = StoreListViewModel(GeekMemoryManager.instance)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        edtSearch = findViewById(R.id.edtSearch)

        val btnSearch = findViewById<Button>(R.id.btnSearch)
        val btnViewStore = findViewById<Button>(R.id.btnViewStore)
        val btnAdd = findViewById<Button>(R.id.btnAdd)
        val btnEditSearch = findViewById<Button>(R.id.btnEditSearch)


        btnSearch.setOnClickListener { openStore() }
        btnViewStore.setOnClickListener { openStore() }
        btnEditSearch.setOnClickListener { openStoreEdit() }

        btnAdd.setOnClickListener {
            startActivity(Intent(this, StoreFormActivity::class.java))
        }
    }


    private fun openStoreDetail() {
        val found = searchStore()
        if (found == null) {
            Toast.makeText(this, getString(R.string.MsgTextNotFound_StoreForm), Toast.LENGTH_LONG).show()
        } else {
            val intent = Intent(this, StoreDetailActivity::class.java)
            intent.putExtra("STORE_ID", found.id)
            startActivity(intent)
        }
    }

    private fun openStoreEdit() {
        val found = searchStore()
        if (found == null) {
            Toast.makeText(this, getString(R.string.MsgTextNotFound_StoreForm), Toast.LENGTH_LONG).show()
        } else {
            val intent = Intent(this, StoreFormActivity::class.java)
            intent.putExtra("STORE_ID", found.id)
            startActivity(intent)
        }
    }

    // SEARCH: finds a store by name and opens its detail
    private fun openStore() {
        viewModel.updateFilter(StoreFilter(query = edtSearch.text.toString().trim()))
        val found = viewModel.stores.firstOrNull()

        if (found == null) {
            Toast.makeText(
                this,
                getString(R.string.MsgTextNotFound_StoreForm),
                Toast.LENGTH_LONG
            ).show()
        } else {
            val intent = Intent(this, StoreDetailActivity::class.java)
            intent.putExtra("STORE_ID", found.id)
            startActivity(intent)
        }
    }

    private fun searchStore() = run {
        viewModel.updateFilter(StoreFilter(query = edtSearch.text.toString().trim()))
        viewModel.stores.firstOrNull()
    }
}