package br.com.alura.calendario

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.media.RingtoneManager
import android.os.Build
import android.os.CountDownTimer
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class AlarmReceiver: BroadcastReceiver() {
    @SuppressLint("MissingPermission")
    override fun onReceive(contexto: Context?, p1: Intent?) {
//        Toast.makeText(contexto, "Alarme Acionado!", Toast.LENGTH_SHORT).show()
//        Log.d("AlarmReceiver", "Alarme Acionado!")

        alarmeSom(contexto)

        val notificationManager = NotificationManagerCompat.from(contexto!!)
        val channelId = "alarm_channel"

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val channel = NotificationChannel(
                channelId,
                "Alarm Channel", NotificationManager.IMPORTANCE_HIGH
            )
            notificationManager.createNotificationChannel(channel)
        }

        val stopIntent = Intent(contexto,LembreteActivity::class.java)
        val pendingStopIntent = PendingIntent.getBroadcast(contexto, 0, stopIntent, PendingIntent.FLAG_MUTABLE)

        val notificacao = NotificationCompat.Builder(contexto, channelId)
            .setSmallIcon(R.drawable.ic_alarm)
            .setContentTitle("Alarme")
            .setContentText("Teste de Alarme")
            .addAction(R.drawable.ic_stop, "Parar", pendingStopIntent)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setCategory(NotificationCompat.CATEGORY_ALARM)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(1, notificacao)
    }

    private fun alarmeSom(contexto: Context?) {
        val defaultUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
        val mediaPlayer = MediaPlayer.create(contexto, defaultUri)

        mediaPlayer.isLooping = true

        mediaPlayer.start()

        val stopTime: Long = 30000

        object : CountDownTimer(stopTime, 1000) {
            override fun onTick(p0: Long) {
            }

            override fun onFinish() {
                mediaPlayer?.stop()
                mediaPlayer?.release()
            }
        }.start()
    }

}