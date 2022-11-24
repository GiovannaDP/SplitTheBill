package com.ifsp.giovanna.splitthebill.adapter

import android.content.Context
import android.os.Parcel
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.ifsp.giovanna.splitthebill.R
import com.ifsp.giovanna.splitthebill.databinding.CellPersonBinding
import com.ifsp.giovanna.splitthebill.model.Person

class PersonAdapter (
        context: Context,
        private val personList:
        MutableList<Person>
        ): ArrayAdapter<Person>(context, R.layout.cell_person, personList) {

        private data class CellPeopleHolder(val nameTv: TextView, val valorTv: TextView)
        private lateinit var cpb: CellPersonBinding

        constructor(parcel: Parcel) : this(
            TODO("context"),
            TODO("peopleList")
        ) {}

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            val contact = personList[position]
            var personCellView = convertView

            if(personCellView == null) {
                // inflo uma nova celula
                cpb = CellPersonBinding.inflate(
                    context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater,
                    parent,
                    false
                )

                personCellView = cpb.root
                val cellPeopleHolder = CellPeopleHolder(cpb.nameTv, cpb.valorpagoTv)
                personCellView.tag = cellPeopleHolder
            }

            with (personCellView.tag as CellPeopleHolder) {
                nameTv.text = contact.name
                valorTv.text = contact.valorPago
            }

            return personCellView
        }

        fun writeToParcel(parcel: Parcel, flags: Int) {

        }

        fun describeContents(): Int {
            return 0
        }

        companion object CREATOR : Parcelable.Creator<PersonAdapter> {
            override fun createFromParcel(parcel: Parcel): PersonAdapter {
                return PersonAdapter(parcel)
            }

            override fun newArray(size: Int): Array<PersonAdapter?> {
                return arrayOfNulls(size)
            }
        }

    }
