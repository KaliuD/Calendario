package br.com.alura.calendario

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Button
import android.widget.EditText

class TelaAnotacoes : Activity(){
    private lateinit var anotacao: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.tela_anotacoes)

        anotacao = findViewById(R.id.editText)

        val dataSelecionada = intent.getStringExtra("dataSelecionada")
        val sharedPreferences = getSharedPreferences("Anotacoes", Context.MODE_PRIVATE)

        val textoDataSelecionada = sharedPreferences.getString(dataSelecionada, "")
        anotacao.setText(textoDataSelecionada)

        voltarParaTelaAnterior()

        vaiParaTelaAlarme(dataSelecionada)
    }

    private fun vaiParaTelaAlarme(dataSelecionada: String?) {
        val botaoAlerta = findViewById<Button>(R.id.botaoAlerta)
        botaoAlerta.setOnClickListener {
            val intent = Intent(this, ConfigurarAlarmeActivity::class.java)
            intent.putExtra("dataSelecionada", dataSelecionada)
            startActivity(intent)
        }
    }

    private fun voltarParaTelaAnterior() {
        val botaoVoltar = findViewById<Button>(R.id.telaAnotaoesBotaoVoltar)
        botaoVoltar.setOnClickListener {
            finish()
        }
    }

    override fun onPause() {
        super.onPause()

        val dataSelecionada = intent.getStringExtra("dataSelecionada")
        val sharedPreferences = getSharedPreferences("Anotacoes", Context.MODE_PRIVATE)

        val textoSalvo = anotacao.text.toString()
        val editor = sharedPreferences.edit()
        editor.putString(dataSelecionada, textoSalvo)
        editor.apply()
    }
}