package br.com.alura.calendario

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import br.com.alura.calendario.databinding.TelaAnotacoesBinding

class TelaAnotacoes : Activity(){
    private lateinit var anotacao: EditText
    private lateinit var binding: TelaAnotacoesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = TelaAnotacoesBinding.inflate(layoutInflater)
        val telaAnotacoes = binding.root
        setContentView(telaAnotacoes)

        anotacao = binding.editText

        val dataSelecionada = intent.getStringExtra("dataSelecionada")
        val sharedPreferences = getSharedPreferences("Anotacoes", Context.MODE_PRIVATE)

        val textoDataSelecionada = sharedPreferences.getString(dataSelecionada, "")
        anotacao.setText(textoDataSelecionada)

        voltarParaTelaAnterior()

        vaiParaTelaAlarme(dataSelecionada)
    }

    private fun vaiParaTelaAlarme(dataSelecionada: String?) {
        val botaoAlerta = binding.botaoAlerta
        botaoAlerta.setOnClickListener {
            val intent = Intent(this, ConfigurarAlarmeActivity::class.java)
            intent.putExtra("dataSelecionada", dataSelecionada)
            startActivity(intent)
        }
    }

    private fun voltarParaTelaAnterior() {
        val botaoVoltar = binding.telaAnotaoesBotaoVoltar
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