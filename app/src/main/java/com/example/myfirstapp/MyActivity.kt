package com.example.myfirstapp

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.widget.TextView

class MyActivity : AppCompatActivity() {
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

            // Me add:
            findViewById<TextView>(R.id.textViewRecieved).apply {
                text = it
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

}