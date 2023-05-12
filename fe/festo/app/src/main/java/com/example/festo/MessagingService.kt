package com.example.festo

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.festo.customer_ui.home.HomeActivity
import com.example.festo.customer_ui.orderlist.OrderlistActivity
import com.example.festo.customer_ui.search.SearchActivity
import com.google.firebase.messaging.Constants.TAG
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MessagingService : FirebaseMessagingService() {
    private var default_notification_channel_id: String? = null

    // 수신 메세지 처리
    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        if (message.notification != null)
            showNotification(message.notification?.title, message.notification!!.body)
    }

    // token 처리
    override fun onNewToken(token: String) {
        Log.d(TAG,"FCM token: $token")
        val sharedPreferences = getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
        sharedPreferences.edit().putString("FCM_TOKEN", token).apply()
        sendRegistrationToServer(token)
    }

    private fun sendRegistrationToServer(token: String) {

    }

    override fun onCreate(){
        super.onCreate()
        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "My Notification Channel"
            val descriptionText = "Channel description"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel("CHANNEL_ID", name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)

            // Assign the channel ID to the default notification channel
            default_notification_channel_id = "CHANNEL_ID"
        }
    }

    @SuppressLint("MissingPermission")
    private fun showNotification(title: String?, body: String?) {
        /*val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("FRAGMENT_NAME", "OrderlistFragment")
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)*/

        val notificationBuilder = NotificationCompat.Builder(this, default_notification_channel_id!!)
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentTitle(title)
            .setContentText(body)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            //.setFullScreenIntent(pendingIntent, true)
            //.setContentIntent(pendingIntent)
            /*.apply {
                if (body != null) {
                    setContentText(body)
                }
            }*/


        with(NotificationManagerCompat.from(this)) {
            notify(1, notificationBuilder.build())
        }
    }
}
