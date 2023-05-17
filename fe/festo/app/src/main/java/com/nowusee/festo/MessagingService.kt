package com.nowusee.festo

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Bitmap
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.request.FutureTarget
import com.google.firebase.messaging.Constants.TAG
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MessagingService : FirebaseMessagingService() {
    private var default_notification_channel_id: String? = null

    // 수신 메세지 처리
    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        Log.i("dddddddd", message.notification?.title.toString())
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
        /*val intent = Intent(this, SearchActivity::class.java)
        intent.putExtra("FRAGMENT_NAME", "OrderlistFragment")
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)*/
        // fest'O 기본 로고 보여주기
        val futureTarget: FutureTarget<Bitmap> = Glide.with(this)
            .asBitmap()
            .load(R.drawable.festologo)
            .submit()
        val bitmap: Bitmap = futureTarget.get()

        Log.i("비트맵 정보", "너비: ${bitmap.width}, 높이: ${bitmap.height}")

        val notificationBuilder = NotificationCompat.Builder(this, default_notification_channel_id!!)
            .setSmallIcon(R.drawable.ic_launcher_background)
            /*.setStyle(NotificationCompat.BigPictureStyle()
                .bigPicture(bitmap))*/
            .setLargeIcon(bitmap)
            .setContentTitle(title)
            .setContentText(body)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
        //.setFullScreenIntent(pendingIntent, true)
        // .setContentIntent(pendingIntent)
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
