package com.kg.taskapp.service

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.kg.taskapp.R

@SuppressLint("MissingFirebaseInstanceTokenRefresh")
class PushNotificationService: FirebaseMessagingService() {


    @SuppressLint("MissingPermission")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        val channel = NotificationChannel(
            CHANNEL_ID,
            "Heads Up Notification",
            NotificationManager.IMPORTANCE_HIGH
        )

        getSystemService(NotificationManager::class.java).createNotificationChannel(channel)
        val notification = android.app.Notification.Builder(this, CHANNEL_ID)
        notification.apply {
            setContentTitle(message.notification?.title)
            setContentText(message.notification?.body)
            setSmallIcon(R.drawable.ic_notifications_black_24dp)
            setAutoCancel(true)
        }
        NotificationManagerCompat.from(this).notify(1, notification.build());
    }

    companion object{
        val CHANNEL_ID = "channel.taskApp"
    }
}