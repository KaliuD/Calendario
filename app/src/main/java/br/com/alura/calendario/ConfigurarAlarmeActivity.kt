package br.com.alura.calendario

import android.Manifest
import android.app.Activity
import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import br.com.alura.calendario.databinding.ConfigurarAlarmeActivityBinding
import java.util.Calendar

class ConfigurarAlarmeActivity: Activity() {
    private lateinit var binding: ConfigurarAlarmeActivityBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ConfigurarAlarmeActivityBinding.inflate(layoutInflater)
        val configurarAlarmeActivity = binding.root
        setContentView(configurarAlarmeActivity)

        try {
            val filter = IntentFilter("ACTION_CONFIGURAR_NOTIFICACAO")
            registerReceiver(receiver, filter, RECEIVER_EXPORTED)
        } catch (e: Exception) {
            Log.d("NOTIFICACAO", "onCreate: ")
            e.printStackTrace()
        }


        val botaoPronto = binding.configurarAlarmeBotaoPronto
        botaoPronto.setOnClickListener{
            configuraAlarme()
//            finish()
        }

        voltarParaTelaAnterior()
    }

    val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
                Log.d("NOTIFICACAO", "receiver: ")
            if (intent?.action == "ACTION_CONFIGURAR_NOTIFICACAO") {
                configuraNotificacao()
            }
        }
    }

    fun configuraNotificacao() {
        val notificationManager = NotificationManagerCompat.from(this)
        val channelId = "alarm_channel"
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val channel = NotificationChannel(
                channelId,
                "Alarm Channel", NotificationManager.IMPORTANCE_HIGH
            )
            notificationManager.createNotificationChannel(channel)
        }

        val dataSelecionada = intent.getStringExtra("dataSelecionada")
        val stopIntent = Intent(this, AlarmService::class.java)
        stopIntent.action = "ACTION_STOP_ALARM"
        stopIntent.putExtra("dataSelecionada", dataSelecionada)
        val pendingStopIntent = PendingIntent.getService(this, 0, stopIntent, PendingIntent.FLAG_IMMUTABLE)

        val notificacao = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.ic_alarm)
            .setContentTitle("Alarme")
            .setContentText("Teste de Alarme")
            .addAction(R.drawable.ic_stop, "Parar", pendingStopIntent)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setCategory(NotificationCompat.CATEGORY_ALARM)
            .setAutoCancel(true)
            .build()

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.POST_NOTIFICATIONS.toString()),101)

        }
        notificationManager.notify(1, notificacao)
    }

    private fun configuraAlarme() {
        val timePicker = binding.timePicker

        val dataSelecionada = intent.getStringExtra("dataSelecionada")
        val partes = dataSelecionada?.split("-")
        val ano = partes?.get(0)?.toInt()
        val mes = partes?.get(1)?.toInt()
        val dia = partes?.get(2)?.toInt()

        val alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager

        val intent = Intent(this, AlarmService::class.java)
        intent.action = "ACTION_START_ALARM"
        val pendingIntent = PendingIntent.getService(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)

        val hora = timePicker.hour
        val minuto = timePicker.minute

        Log.d("Data", "ano:" +ano+ ", mes:"+ mes + ", dia:" + dia)
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.YEAR, ano!!)
        calendar.set(Calendar.MONTH, mes!!)
        calendar.set(Calendar.DAY_OF_MONTH, dia!!)
        calendar.set(Calendar.HOUR_OF_DAY, hora)
        calendar.set(Calendar.MINUTE, minuto)

//        if (calendar.timeInMillis <= System.currentTimeMillis()){
//            calendar.add(Calendar.DAY_OF_MONTH, 1)
//        }

        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
    }

    private fun voltarParaTelaAnterior() {
        val botaoVoltar = binding.configurarAlarmeBotaoVoltar
        botaoVoltar.setOnClickListener {
            finish()
        }
    }

    override fun onDestroy() {
        unregisterReceiver(receiver)
        super.onDestroy()
    }

}