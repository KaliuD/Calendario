package br.com.alura.calendario

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.widget.TextView

class LembreteActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.lembrete_activity)

        val lembrete = findViewById<TextView>(R.id.lembrete)
        val appData = application as Calendario
        val dataSelecionada = appData.dataSelecionada
        val sharedPreferences = getSharedPreferences("Anotacoes", Context.MODE_PRIVATE)
        val textoDataSelecionada = sharedPreferences.getString(dataSelecionada, "")
        lembrete.text = textoDataSelecionada
    }

}