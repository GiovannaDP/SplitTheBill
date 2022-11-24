package com.ifsp.giovanna.splitthebill.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Person(
    val id: Int,
    var name: String,
    var valorPago: String,
    var compras: String,
    var valorAPagarAutomatico: String,
    var valorAPagarFixo: String
): Parcelable
