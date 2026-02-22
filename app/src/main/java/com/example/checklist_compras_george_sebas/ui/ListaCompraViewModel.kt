package com.example.checklist_compras_george_sebas.ui

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.checklist_compras_george_sebas.model.Producto

class ListaCompraViewModel : ViewModel() {

    private val _productos = mutableStateListOf<Producto>()
    val productos: List<Producto> = _productos

    init {
        //items de ejemplo
        addItem("Leche")
        addItem("Pan")
        addItem("Huevos")
        addItem("Manzanas")
        addItem("Arroz")
        addItem("Un skibidi")
        addItem("Dos skibidis")
    }

    fun addItem(name: String) {
        if (name.isNotBlank()) {
            _productos.add(Producto(name = name.trim()))
        }
    }

    fun toggleItemPurchased(itemId: String) {
        val index = _productos.indexOfFirst { it.id == itemId }
        if (index != -1) {
            val item = _productos[index]
            _productos[index] = item.copy(isPurchased = !item.isPurchased)
        }
    }

    fun deleteItem(itemId: String) {
        _productos.removeIf { it.id == itemId }
    }

    fun clearPurchasedItems() {
        _productos.removeIf { it.isPurchased }
    }

    fun getPurchasedCount(): Int {
        return _productos.count { it.isPurchased }
    }

    fun getTotalCount(): Int {
        return _productos.size
    }
}