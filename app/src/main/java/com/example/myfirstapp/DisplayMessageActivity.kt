package com.example.myfirstapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Log.ERROR
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyLog
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import java.net.HttpURLConnection
import java.net.URL

class DisplayMessageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_message)

        // Get the Intent that started this activity and extract teh string
        val message = intent.getStringExtra(EXTRA_MESSAGE) //EXTRA_MESSAGE is a key defined in the main activity but that seems like not best practice.

        apiCall()
        // val anotherMessage = SendGet()

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

    /*private fun SendGet(): String {
        println("\n first where can i see this ffs")
        //val url = URL("https://publicobject.com/helloworld.txt")
        val url = URL("https://api2.binance.com/api/v3/ticker/24hr")

        with(url.openConnection() as HttpURLConnection) {

            println("\n second do i get to here or the open sucks?")

            requestMethod = "GET"  // optional default is GET

            println("\nSent 'GET' request to URL : $url; Response Code : $responseCode")

            inputStream.bufferedReader().use() {
                val stringStart = ""
                it.readLines().forEach(){
                    stringStart.plus(it)
                }
                return stringStart
            }
        }
    }*/

    /*private fun SendGet(): String {

        return apiCall()

        *//*val url = "http://my-json-feed"

        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null,
            Response.Listener { response ->
                textView.text = "Response: %s".format(response.toString())
            },
            Response.ErrorListener { error ->
                // TODO: Handle error
            }
        )

        // Access the RequestQueue through your singleton class.
        val something =  MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)*//*


    }*/



    private fun apiCall() {
        //val url = "https://some-random-api.ml/img/cat" //api url
        val url = "https://api.twitter.com/2/tweets/1531983613809799168"

        //create a request quque
        val queue = Volley.newRequestQueue(this)
        // for some reason this crazy format is needed to override the jsonobjectrequest in order to add the headers
        // so far this seems pretty shit compared to some js libraries.
        val jsonObjectRequest: JsonObjectRequest = object : JsonObjectRequest(
            Request.Method.GET,
            url,
            null,
            Response.Listener {

                //have to do everything here like...async..
                //this seems like getting into promise hell type teritory.
                // this method for our api call success
                Log.d("MainActivity", "Api call successful")
                Log.d("response string", it.toString())
                val tweetData = it.getJSONObject("data")
                val tweetId = tweetData.getString("id")
                val tweetText = tweetData.getString("text")

                val textView = findViewById<TextView>(R.id.textView).apply {
                    text = tweetText
                }

            },
            Response.ErrorListener {
                //this for errors
                Log.d("MainActivity", "API call failed")
                Log.d("Error", it.message.toString())
                Log.d("error", it.networkResponse.statusCode.toString())
            })
            {
                override fun getHeaders(): Map<String, String>  {
                    val apiHeader = HashMap<String, String>()
                    apiHeader["Authorization"] =
                        "Bearer AAAAAAAAAAAAAAAAAAAAADFxfgEAAAAAJmmaPcD5YvU7Z3dP%2F9Ch4ZPxmLQ%3Dmp5nHhksBVEEBQ5chN2NvNnDW2rgXA4z2gvmjLa2sjlHVuphGz"
                    return apiHeader
                }
            }
        //set the json request to queue
        queue.add(jsonObjectRequest)
    }
}