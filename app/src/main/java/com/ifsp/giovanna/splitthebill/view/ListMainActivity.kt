package com.ifsp.giovanna.splitthebill.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.ifsp.giovanna.splitthebill.R
import com.ifsp.giovanna.splitthebill.adapter.PersonAdapter
import com.ifsp.giovanna.splitthebill.databinding.ActivityListMainBinding
import com.ifsp.giovanna.splitthebill.model.Bill
import com.ifsp.giovanna.splitthebill.model.Constant
import com.ifsp.giovanna.splitthebill.model.Constant.BILL_PEOPLE
import com.ifsp.giovanna.splitthebill.model.Constant.BILL_VALOR
import com.ifsp.giovanna.splitthebill.model.Person

class ListMainActivity : AppCompatActivity() {

    private val almb: ActivityListMainBinding by lazy {
        ActivityListMainBinding.inflate(layoutInflater)
    }

    private var quantidadePessoas = 0
    private var valorPorPessoa = 0.0

    private val personList: MutableList<Person> = mutableListOf()
    private lateinit var carl: ActivityResultLauncher<Intent>
    private lateinit var marl: ActivityResultLauncher<Intent>

    private lateinit var peopleAdapter: PersonAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(almb.root)

        marl = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult(),
        ) { resultado ->
            if (resultado.resultCode == RESULT_OK) {
                Toast.makeText(this,"ok", Toast.LENGTH_SHORT).show()
            }}

        var bill = intent.getParcelableExtra<Bill>(BILL_PEOPLE)
        bill?.let { _bill ->
            quantidadePessoas = _bill.quantidadePessoas.toInt();
            valorPorPessoa = _bill.valorPorPessoa.toDouble()
        }

        fillContactList()
        peopleAdapter = PersonAdapter(this, personList)
        almb.peopleLv.adapter = peopleAdapter

        carl = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult(),
        ) { resultado ->
            if (resultado.resultCode == RESULT_OK) {
                val person = resultado.data?.getParcelableExtra<Person>(Constant.EXTRA_PERSON)
                person?.let { _person ->
                    if (personList.any{ it.id == _person.id}){
                        val position = personList.indexOfFirst { it.id == _person.id }
                        personList[position] = _person
                    } else {
                        personList.add(_person)
                    }
                    peopleAdapter.notifyDataSetChanged()
                }
            }
        }

        registerForContextMenu(almb.peopleLv)

        almb.peopleLv.onItemClickListener = object: AdapterView.OnItemClickListener {
            override fun onItemClick(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val person = personList[position]
                val personIntent = Intent(this@ListMainActivity, PersonActivity::class.java)
                personIntent.putExtra(Constant.EXTRA_PERSON, person)
                personIntent.putExtra(Constant.VIEW_PERSON, true)
                startActivity(personIntent)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.addPersonMi -> {
                carl.launch(Intent(this, PersonActivity::class.java))
                true
            }

            R.id.changeBillMi -> {
                marl.launch(Intent(this, MainActivity::class.java))
                true
            }
            else -> { false }
        }
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        menuInflater.inflate(R.menu.menu_changes_main, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val position = (item.menuInfo as AdapterView.AdapterContextMenuInfo).position
        return when(item.itemId) {
            R.id.removePersonMi -> {
                personList.removeAt(position)
                peopleAdapter.notifyDataSetChanged()
                Toast.makeText(this, "Pessoa removida com sucesso!", Toast.LENGTH_SHORT).show()
                true
            }

            R.id.editPersonMi -> {
                val person = personList[position]
                val personIntent = Intent(this, PersonActivity::class.java)
                personIntent.putExtra(Constant.EXTRA_PERSON, person)
                personIntent.putExtra(Constant.VIEW_PERSON, false)
                carl.launch(personIntent)
                true
            }

            else -> { false }
        }
    }

    private fun fillContactList() {
        for (i in 1 ..quantidadePessoas.toInt()){
            personList.add(
                Person(
                    id = i,
                    name = "$i",
                    valorPago = "0.00",
                    compras = "",
                    valorAPagarAutomatico = "$valorPorPessoa",
                    valorAPagarFixo = "$valorPorPessoa",
                )
            )
        }
    }
}