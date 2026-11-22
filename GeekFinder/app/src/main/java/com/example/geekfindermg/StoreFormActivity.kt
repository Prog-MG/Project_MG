package com.example.geekfindermg

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.geekfindermg.data.memory.GeekMemoryManager
import com.example.geekfindermg.domain.model.PriceLevel
import com.example.geekfindermg.domain.model.StoreCategory
import com.example.geekfindermg.viewmodel.StoreFormViewModel

class StoreFormActivity : AppCompatActivity() {

    private lateinit var txtName: EditText
    private lateinit var txtKind: EditText
    private lateinit var txtPrice: EditText
    private lateinit var txtHours: EditText
    private lateinit var txtPhone: EditText

    private var selectedCategory = StoreCategory.VIDEO_GAMES
    private var selectedPrice = PriceLevel.MEDIUM
    private val viewModel = StoreFormViewModel(GeekMemoryManager.instance)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_store_form)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        txtName = findViewById(R.id.txtName_StoreF)
        txtKind = findViewById(R.id.txtKindStore_StoreF)
        txtPrice = findViewById(R.id.txtPrice_StoreF)
        txtHours = findViewById(R.id.txtHours_StoreF)
        txtPhone = findViewById(R.id.txtPhone_StoreF)

        val btnSave = findViewById<Button>(R.id.btnSave_StoreF)
        val btnCancel = findViewById<Button>(R.id.btnCancel_StoreF)

        // Category and price are chosen from a list, not typed
        txtKind.isFocusable = false
        txtPrice.isFocusable = false
        txtKind.setOnClickListener { chooseCategory() }
        txtPrice.setOnClickListener { choosePrice() }
        showCategory()
        showPrice()

        // Edit mode: arrives with the id of the store to update
        val storeId = intent.getLongExtra("STORE_ID", -1L)
        if (storeId != -1L) {
            loadStore(storeId)
        }

        btnSave.setOnClickListener {
            if (viewModel.editingStoreId != null) {
                showUpdateDialog()
            } else {
                saveStore()
            }
        }

        btnCancel.setOnClickListener { cleanScreen() }
    }

    private fun categoryLabels(): Array<String> {
        return arrayOf(
            getString(R.string.TextCategoryVideoGames_StoreForm),
            getString(R.string.TextCategoryFigures_StoreForm),
            getString(R.string.TextCategoryCards_StoreForm)
        )
    }

    private fun priceLabels(): Array<String> {
        return arrayOf(
            getString(R.string.TextPriceLow_StoreForm),
            getString(R.string.TextPriceMedium_StoreForm),
            getString(R.string.TextPriceHigh_StoreForm)
        )
    }

    private fun showCategory() {
        txtKind.setText(categoryLabels()[selectedCategory.ordinal])
    }

    private fun showPrice() {
        txtPrice.setText(priceLabels()[selectedPrice.ordinal])
    }

    private fun chooseCategory() {
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.TextType_StoreForm))
            .setItems(categoryLabels()) { _, which ->
                selectedCategory = StoreCategory.entries[which]
                showCategory()
            }
            .show()
    }

    private fun choosePrice() {
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.TextPriceLevel_StoreForm))
            .setItems(priceLabels()) { _, which ->
                selectedPrice = PriceLevel.entries[which]
                showPrice()
            }
            .show()
    }

    // READ: fills the form with the store to edit
    private fun loadStore(storeId: Long) {
        viewModel.loadStore(storeId)
        txtName.setText(viewModel.name)
        selectedCategory = viewModel.category
        selectedPrice = viewModel.priceLevel
        showCategory()
        showPrice()
        txtHours.setText(viewModel.openingHours)
        txtPhone.setText(viewModel.phone)
    }

    // CREATE (new store) or UPDATE (edit mode)
    private fun saveStore() {
        viewModel.name = txtName.text.toString().trim()
        viewModel.category = selectedCategory
        viewModel.priceLevel = selectedPrice
        viewModel.openingHours = txtHours.text.toString().trim()
        viewModel.phone = txtPhone.text.toString().trim()

        val isEditMode = viewModel.editingStoreId != null

        try {
            viewModel.save()
            val message = if (isEditMode)
                getString(R.string.MsgTextUpdateSuccess_StoreForm)
            else
                getString(R.string.MsgTextSaveSuccess_StoreForm)
            Toast.makeText(this, message, Toast.LENGTH_LONG).show()
            if (isEditMode) {
                finish()
            } else {
                cleanScreen()
            }
        } catch (e: Exception) {
            Toast.makeText(this, e.message.toString(), Toast.LENGTH_LONG).show()
        }
    }

    private fun showUpdateDialog() {
        AlertDialog.Builder(this)
            .setMessage(getString(R.string.DialogTextUpdateQuestion_StoreForm))
            .setPositiveButton(getString(R.string.btnTextSave_StoreForm)) { _, _ -> saveStore() }
            .setNegativeButton(getString(R.string.btnTextCancel_StoreForm), null)
            .show()
    }

    private fun cleanScreen() {
        viewModel.editingStoreId = null
        selectedCategory = StoreCategory.VIDEO_GAMES
        selectedPrice = PriceLevel.MEDIUM
        txtName.setText("")
        showCategory()
        showPrice()
        txtHours.setText("")
        txtPhone.setText("")
    }
}