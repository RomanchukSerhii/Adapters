package com.example.adapters

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import com.example.adapters.databinding.ActivityArrayAdapterBinding
import com.example.adapters.databinding.DialogAddCharacterBinding
import java.util.*

class ArrayAdapterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityArrayAdapterBinding
    private lateinit var adapter: ArrayAdapter<Character>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityArrayAdapterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupListWithArrayAdapter()
        binding.addButton.setOnClickListener { onAddPressed() }
    }

    private fun setupListWithArrayAdapter() {
        val data = mutableListOf(
            Character(id = UUID.randomUUID().toString(), name = "Reptile"),
            Character(id = UUID.randomUUID().toString(), name = "Subzero"),
            Character(id = UUID.randomUUID().toString(), name = "Scorpion"),
            Character(id = UUID.randomUUID().toString(), name = "Raiden"),
            Character(id = UUID.randomUUID().toString(), name = "Smoke"),
        )

        adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            android.R.id.text1,
            data
        )

        binding.listView.adapter = adapter

        binding.listView.setOnItemClickListener { parent, view, position, id ->
            adapter.getItem(position)?.let {
                deleteCharacter(it)
            }
        }

        binding.spinner.adapter = adapter

        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val character = data[position]
                binding.characterInfoTextView.text = "${character.id}, ${character.name}"
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                binding.characterInfoTextView.text = ""
            }
        }
    }

    private fun onAddPressed() {
        val dialogBinding = DialogAddCharacterBinding.inflate(layoutInflater)
        val dialog = AlertDialog.Builder(this)
            .setTitle("Create character")
            .setView(dialogBinding.root)
            .setPositiveButton("Add") { d, which ->
                val name = dialogBinding.characterNameEditText.text.toString()
                if (name.isNotBlank()) {
                    createCharacter(name)
                }
            }
            .create()
        dialog.show()
    }

    private fun createCharacter(name: String) {
        val character = Character(
            id = UUID.randomUUID().toString(),
            name = name
        )
        adapter.add(character)
    }

    private fun deleteCharacter(character: Character) {
        val listener = DialogInterface.OnClickListener { dialogInterface, which ->
            if (which == DialogInterface.BUTTON_POSITIVE) {
                adapter.remove(character)
            }
        }

        val dialog = AlertDialog.Builder(this)
            .setTitle("Delete Character")
            .setMessage("Are you sure you want to delete the character $character?")
            .setPositiveButton("Delete", listener)
            .setNegativeButton("Cancel", listener)
            .create()
        dialog.show()
    }

    class Character (
        val id: String,
        val name: String
    ) {
        override fun toString(): String {
            return name
        }
    }
}