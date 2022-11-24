package com.ifsp.giovanna.splitthebill.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.ifsp.giovanna.splitthebill.databinding.ActivityMainBinding
import com.ifsp.giovanna.splitthebill.model.Bill
import com.ifsp.giovanna.splitthebill.model.Constant
import com.ifsp.giovanna.splitthebill.model.Constant.BILL_PEOPLE
import com.ifsp.giovanna.splitthebill.model.Constant.BILL_VALOR
import com.ifsp.giovanna.splitthebill.model.Person
import kotlin.random.Random

class MainActivity : AppCompatActivity() {


    private val amb: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }


    private lateinit var marl: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(amb.root)


        marl = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult(),
        ) { resultado ->
            if (resultado.resultCode == RESULT_OK) {
                Toast.makeText(this,"ok", Toast.LENGTH_SHORT).show()
            }}



        amb.dividirContaBt.setOnClickListener {
            val valor = (amb.valorTotalEt.text.toString()).toDouble()
            val quatidade = (amb.quantidadePessoasEt.text.toString()).toInt()
            val valorPessoa = (valor/quatidade).toString()

            val bill = Bill(
                valorTotal = amb.valorTotalEt.text.toString(),
                quantidadePessoas = amb.quantidadePessoasEt.text.toString(),
                valorPorPessoa = valorPessoa
            )

            val peopleIntent = Intent(this@MainActivity, ListMainActivity::class.java)
            peopleIntent.putExtra(Constant.BILL_PEOPLE, bill)
            startActivity(peopleIntent)
        }

    }
}