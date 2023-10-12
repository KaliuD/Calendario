package br.com.alura.calendario

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.CalendarView
import br.com.alura.calendario.databinding.ActivityMainBinding

class MainActivity : Activity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val activityMain = binding.root
        setContentView(activityMain)

        val cv = binding.calendarView

        vaiParaTelaAnotacao(cv)

    }

    private fun vaiParaTelaAnotacao(cv: CalendarView) {
        var dia = 0
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