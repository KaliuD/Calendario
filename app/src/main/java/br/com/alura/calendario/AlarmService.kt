package br.com.alura.calendario

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.media.RingtoneManager
import android.os.IBinder

class AlarmService : Service() {
    private var mediaPlayer : MediaPlayer? = null
    private val somDoAlarme = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)

    override fun onBind(p0: Intent?): IBinder? {
        TODO("Not yet implemented")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (intent?.action == "ACTION_START_ALARM"){
            startAlarm()
        } else if (intent?.action == "ACTION_STOP_ALARM"){
            stopAlarm()
            val dataSelecionada = intent?.getStringExtra("dataSelecionada")

            try {
                abrirLembrete()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return START_NOT_STICKY
    }

    private fun startAlarm() {
        mediaPlayer = MediaPlayer.create(this, somDoAlarme)
        mediaPlayer?.isLooping = true
        mediaPlayer?.start()
    }

    private fun stopAlarm(){
        mediaPlayer?.stop()
        mediaPlayer?.release()
    }

    private fun abrirLembrete(){
        val intent = Intent(this, LembreteActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        stopAlarm()
    }

}