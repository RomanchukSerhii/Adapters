package com.example.adapters

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.AdapterView
import android.widget.SimpleAdapter
import androidx.appcompat.app.AlertDialog
import com.example.adapters.databinding.ActivitySimpleAdapterBinding

class SimpleAdapterActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySimpleAdapterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySimpleAdapterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupListViewSimple()
    }

    private fun setupListViewSimple() {
        val data = listOf(
            mapOf(
                KEY_TITLE to "First title 111",
                KEY_DESCRIPTION to "The first some description"
            ),
            mapOf(
                KEY_TITLE to "First title 222",
                KEY_DESCRIPTION to "The second some description"
            ),
            mapOf(
                KEY_TITLE to "First title 333",
                KEY_DESCRIPTION to "The third some description"
            )
        )

        val adapter = SimpleAdapter(
            this,
            data,
            android.R.layout.simple_list_item_2,
            arrayOf(KEY_TITLE, KEY_DESCRIPTION),
            intArrayOf(android.R.id.text1, android.R.id.text2)
        )
        binding.listView.adapter = adapter

        binding.listView.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            val selectedItemTitle = data[position][KEY_TITLE]
            val selectedItemDescription = data[position][KEY_DESCRIPTION]

            val dialog = AlertDialog.Builder(this)
                .setTitle(selectedItemTitle)
                .setMessage(selectedItemDescription)
                .setPositiveButton("Confirm", null)
                .create()

            dialog.show()
        }
    }

    companion object {
        @JvmStatic private val KEY_TITLE = "KEY_TITLE"
        @JvmStatic private val KEY_DESCRIPTION = "KEY_DESCRIPTION"
    }
}