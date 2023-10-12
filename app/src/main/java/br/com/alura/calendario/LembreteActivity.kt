package br.com.alura.calendario

import android.app.Activity
import android.content.Context
import android.os.Bundle
import br.com.alura.calendario.databinding.LembreteActivityBinding

class LembreteActivity : Activity() {
    private lateinit var binding: LembreteActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LembreteActivityBinding.inflate(layoutInflater)
        val lembreteActivity = binding.root
        setContentView(lembreteActivity)

        val lembrete = binding.lembrete
        val appData = application as Calendario
        val dataSelecionada = appData.dataSelecionada
        val sharedPreferences = getSharedPreferences("Anotacoes", Context.MODE_PRIVATE)
        val textoDataSelecionada = sharedPreferences.getString(dataSelecionada, "")
        lembrete.text = textoDataSelecionada
    }

}