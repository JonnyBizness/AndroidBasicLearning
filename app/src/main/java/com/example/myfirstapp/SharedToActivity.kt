package com.example.myfirstapp

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.widget.TextView

import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL


class SharedToActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_activity)

        when {
            intent?.action == Intent.ACTION_SEND -> {
                if("text/plain" == intent.type) {
                    handleSendText(intent) // Handle text being sent
                }else if(intent.type?.startsWith("image/") == true){
                    handleSendImage(intent) // Handle singe image being sent
                }// What if they're sent together? or do sends fire multiples...what mime type is a tweet with video.
            }
            intent?.action == Intent.ACTION_SEND_MULTIPLE
                    && intent.type?.startsWith("image/") == true -> {
                        handleSendMultipleImages(intent) // handle multiple images being sent
                    }
            else -> { // WTF? this else isn't on a when it just kind of exists on the intent? why need this.
                // Handle other intents, such as being started from the home screen
            }
        }
    }

    private fun handleSendText(intent: Intent) {
        intent.getStringExtra(Intent.EXTRA_TEXT)?.let {
            // Update UI to reflect text being shared

            val fullTextDisplay = "This is the URL of the tweet: $it"
            val newTest = sendGet()
            // Me add:
            findViewById<TextView>(R.id.textViewRecieved).apply {
                text = fullTextDisplay
            }
        }
    }

    private fun handleSendImage(intent: Intent) {
        (intent.getParcelableExtra<Parcelable>(Intent.EXTRA_STREAM) as? Uri)?.let { //this seems closest to what i'm going to need to do.
            // Update UI to reflect image being shared
        }
    }

    private fun handleSendMultipleImages(intent: Intent) {
        intent.getParcelableArrayListExtra<Parcelable>(Intent.EXTRA_STREAM)?.let { // Feels like tehre should be more here, like i have to loop over?
            // Update UI to reflect multiple images being shared
        }
    }

    private fun sendGet(): String {
        val url = URL("https://publicobject.com/helloworld.txt")

        with(url.openConnection() as HttpURLConnection) {
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
    }


}