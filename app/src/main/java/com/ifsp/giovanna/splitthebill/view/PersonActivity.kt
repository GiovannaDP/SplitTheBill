package com.ifsp.giovanna.splitthebill.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.ifsp.giovanna.splitthebill.databinding.ActivityPersonBinding
import com.ifsp.giovanna.splitthebill.model.Constant
import com.ifsp.giovanna.splitthebill.model.Person
import kotlin.math.roundToLong
import kotlin.random.Random

class PersonActivity : AppCompatActivity() {

    private val apb: ActivityPersonBinding by lazy {
        ActivityPersonBinding.inflate(layoutInflater)
    }

    private var valorAPagarAtualizado = 0.0
    private var valorFixo = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(apb.root)

        val receivedInfo = intent.getParcelableExtra<Person>(Constant.EXTRA_PERSON)
        receivedInfo?.let { _receivedInfo ->
            with(apb) {
                with(_receivedInfo){
                    nameEt.setText(name)
                    valorPagoEt.setText(valorPago)
                    comprasEt.setText(compras)
                    dividaEt.setText(valorAPagarAutomatico)
                }
            }
            valorFixo = _receivedInfo.valorAPagarFixo.toDouble()
        }

        val viewPerson = intent.getBooleanExtra(Constant.VIEW_PERSON, false)
        if (viewPerson) {
            with(apb){
                nameEt.isEnabled = false
                valorPagoEt.isEnabled = false
                comprasEt.isEnabled = false
                saveBt.visibility = View.GONE
            }
        }

        apb.saveBt.setOnClickListener{

            diferenca((apb.valorPagoEt.text.toString()).toDouble(), valorFixo)
            val person = Person(
                id = receivedInfo?.id?: Random(System.currentTimeMillis()).nextInt(),
                name = apb.nameEt.text.toString(),
                valorPago = apb.valorPagoEt.text.toString(),
                compras = apb.comprasEt.text.toString(),
                valorAPagarAutomatico = valorAPagarAtualizado.toString(),
                valorAPagarFixo = valorFixo.toString()
            )

            val resultIntent = Intent()
            resultIntent.putExtra(Constant.EXTRA_PERSON, person)
            setResult(RESULT_OK, resultIntent)
            finish()
        }
    }



//

//
    fun diferenca(valorPago: Double, valorAPagar: Double){
        var diferenca = valorAPagar - valorPago

        valorAPagarAtualizado = diferenca
    }
}