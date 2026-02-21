package com.example.checklist_compras_george_sebas.model

import java.util.UUID

data class ShoppingItem (
    val id: String = UUID.randomUUID().toString()
    var name: String,
    var isPurchased: Boolean = false
)