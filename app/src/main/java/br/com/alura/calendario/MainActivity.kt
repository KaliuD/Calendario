package br.com.alura.calendario

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.CalendarView
import android.widget.TextView
import android.widget.Toast

class MainActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val cv = findViewById<CalendarView>(R.id.calendarView)

        vaiParaTelaAnotacao(cv)

    }

    private fun vaiParaTelaAnotacao(cv: CalendarView) {
        var dia: Int = 0
        cv.setOnDateChangeListener { _, ano, mes, diaDoMes ->
            if (diaDoMes == dia) {
                val intent = Intent(this, TelaAnotacoes::class.java)
                intent.putExtra("dataSelecionada", "$ano-$mes-$diaDoMes")
                val appData = application as Calendario
                appData.dataSelecionada = "$ano-$mes-$diaDoMes"

                startActivity(intent)
            }
            dia = diaDoMes
        }
    }
}