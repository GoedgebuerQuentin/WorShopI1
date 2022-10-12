package com.example.myapplication

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.myapplication.databinding.ActivityMainBinding
import kotlinx.coroutines.*
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.math.log

class MainActivity : Activity() {

    private lateinit var binding: ActivityMainBinding
    var index = 1
    var heartBeat = 70
    var question = false

    private companion object {
        private const val CHANNEL_ID = "channel01"
    }

    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



         val timer = object  : CountDownTimer(500000, 1000) {
             override fun onTick(p0: Long) {
                 setheartbeat()
             }

             override fun onFinish() {
             }
         }
        timer.start()

        var btnQuestion = findViewById<TextView>(R.id.heartbeatTextView)
        btnQuestion.setOnClickListener {
            showNotification()
            question = true
            findViewById<TextView>(R.id.question).visibility = View.VISIBLE
            findViewById<Button>(R.id.button_bien).visibility = View.VISIBLE
            findViewById<Button>(R.id.button_mal).visibility = View.VISIBLE
            //Toast.makeText(this@MainActivity, "Comment vous sentez vous ?", Toast.LENGTH_LONG).show()
        }

        var btnBien = findViewById<Button>(R.id.button_bien)
        btnBien.setOnClickListener{
            findViewById<TextView>(R.id.question).visibility = View.INVISIBLE
            findViewById<Button>(R.id.button_bien).visibility = View.INVISIBLE
            findViewById<Button>(R.id.button_mal).visibility = View.INVISIBLE
        }

        var btnMal = findViewById<Button>(R.id.button_mal)
        btnMal.setOnClickListener{
            findViewById<TextView>(R.id.question).visibility = View.INVISIBLE
            findViewById<Button>(R.id.button_bien).visibility = View.INVISIBLE
            findViewById<Button>(R.id.button_mal).visibility = View.INVISIBLE
        }


    }

    /*fun setQuestion(){
        Toast.makeText(applicationContext, "Tu va bien frero ????", Toast.LENGTH_LONG).show()
    }*/

    fun showNotification() {
        createNotificationChannel()

        val date = Date()
        val notificationId = SimpleDateFormat("ddHHmmss", Locale.FRANCE).format(date).toInt()

        val notificationBuilder = NotificationCompat.Builder(this,"$CHANNEL_ID")

        notificationBuilder.setSmallIcon(R.drawable.ic_notification)

        //notificationBuilder.setContentTitle("Bonjour")

        notificationBuilder.setContentText("Comment vous sentez vous ?")

        notificationBuilder.priority = NotificationCompat.PRIORITY_DEFAULT
        
        val notificationCompatManager = NotificationManagerCompat.from(this)
        notificationCompatManager.notify(notificationId, notificationBuilder.build())
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val name: CharSequence = "MyNotification"
            val description = "my notification channel description"

            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val notificationChannel = NotificationChannel(CHANNEL_ID, name, importance)
            notificationChannel.description = description
            val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(notificationChannel)
        }
    }

    fun setheartbeat() {
        Log.d("test", "set" + index)
             if (heartBeat == 70) {
                heartBeat = 73
                 binding.heartbeatTextView.text = heartBeat.toString()
            }
            else if (heartBeat == 73) {
                 heartBeat = 71
                 binding.heartbeatTextView.text = heartBeat.toString()
            }
            else if (heartBeat == 71) {
                 heartBeat = 68
                 binding.heartbeatTextView.text = heartBeat.toString()
            }
            else if (heartBeat == 68){
                 heartBeat = 70
                 binding.heartbeatTextView.text = heartBeat.toString()
            }
    }
}