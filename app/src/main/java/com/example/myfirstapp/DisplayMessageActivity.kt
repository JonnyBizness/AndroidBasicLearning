package com.example.myfirstapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class DisplayMessageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_message)

        // Get the Intent that started this activity and extract teh string
        val message = intent.getStringExtra(EXTRA_MESSAGE) //EXTRA_MESSAGE is a key defined in the main activity but that seems like not best practice.

        // Capture the layout's TextView and set the string as its text
        val textView = findViewById<TextView>(R.id.textView).apply {
            text = message
        }

        // getting the recyclerview by its id
        val recyclerview = findViewById<RecyclerView>(R.id.recyclerview)

        // this creates a vertical layout manager
        recyclerview.layoutManager = LinearLayoutManager(this) //if this doesn't work it maybe because i made my thing constrained.

        // Create array list for ItemsViewModel
        val data = ArrayList<ItemsViewModel>() //empty array initilized?

        // This loop will create 20 Views containing the image with the count of the view
        for (i in 1..20) {
            data.add(ItemsViewModel(com.google.android.material.R.drawable.material_ic_calendar_black_24dp, "Item " + i))
        }

        // This will pass the arrayList to our adapter
        val adapter = CustomAdapter(data) // essentially give me new CustomAdapter in a variable adapter.

        // Setting the adapter with the recyclerview
        recyclerview.adapter = adapter // not sure on this syntax, updating our recycler thingy to be like here, with this shit.


    }
}