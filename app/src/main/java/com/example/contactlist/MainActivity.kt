package com.example.contactlist

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SearchView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity(),ContactAdapter.ItemClickListener {

    val data = ArrayList<contactListData>()
    lateinit var searchView: SearchView
    lateinit var selectedContactTextView: TextView
    private val selectedContacts = mutableListOf<contactListData>()
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        searchView=findViewById(R.id.contactSearch)
        selectedContactTextView = findViewById(R.id.selectedContactTextView)
        val recyclerView = this.findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        addingData()
        val contactAdapter = ContactAdapter(data,this)
        recyclerView.adapter = contactAdapter



        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }



            override fun onQueryTextChange(newText: String): Boolean {
                contactAdapter.filter.filter(newText)
                return true
            }
        })
    }


    fun addingData(){
        data.clear()
        data.add(contactListData("Abraham Jones",R.drawable.smiley, "Mobile", "+1392-948-4020"))
        data.add(contactListData("Abby Smith",R.drawable.smiley, "Mobile", "+1392-343-5030"))
        data.add(contactListData("Adriene Pitt",R.drawable.smiley, "Mobile", "+1392-948-4020"))
        data.add(contactListData("Andrelina Smith",R.drawable.smiley, "Mobile", "+1392-948-4020"))
        data.add(contactListData("Jonas Pitt",R.drawable.smiley, "Mobile", "+1392-948-4020"))
        data.add(contactListData("Jonas Pitt",R.drawable.smiley, "Mobile", "+1392-948-4020"))
        data.add(contactListData("Honey Krwas",R.drawable.smiley, "Mobile", "+1392-948-4129"))
        data.add(contactListData("Selena Jan",R.drawable.smiley, "Mobile", "+1392-333-4020"))
        data.add(contactListData("James Bond",R.drawable.smiley, "Mobile", "+1392-948-4020"))




    }

    override fun onContactSelected(contact: contactListData) {

        contact.isSelected =!contact.isSelected
        if (contact.isSelected) {
            // Item is selected, add it to the selectedContacts list
            if (!selectedContacts.contains(contact)) {
                selectedContacts.add(contact)
            }
        } else {
            // Item is deselected, remove it from the selectedContacts list
            selectedContacts.remove(contact)
        }



        // Update the selectedContactTextView to display the selected items
        val selectedItemsText = selectedContacts.joinToString(separator = ", ") { it.name }
        selectedContactTextView.text = selectedItemsText
    }
}
