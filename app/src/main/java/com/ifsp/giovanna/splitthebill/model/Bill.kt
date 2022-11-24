package com.ifsp.giovanna.splitthebill.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class Bill(
        var valorTotal: String,
        var quantidadePessoas: String,
        var valorPorPessoa: String
): Parcelable
