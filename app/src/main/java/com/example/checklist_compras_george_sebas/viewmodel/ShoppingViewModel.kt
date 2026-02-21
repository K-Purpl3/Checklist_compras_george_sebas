package com.example.checklist_compras_george_sebas.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.checklist_compras_george_sebas.model.ShoppingItem

class ShoppingViewModel : ViewModel() {

    private val _shoppingItems = mutableStateListOf<ShoppingItem>()
    val shoppingItems: List<ShoppingItem> = _shoppingItems

    init {
        // Agregar algunos items de ejemplo
        addItem("Leche")
        addItem("Pan")
        addItem("Huevos")
        addItem("Manzanas")
        addItem("Arroz")
        addItem("George persona normal y decente")
        addItem("Sebastian LadyBoy y muy posible therian")
    }

    fun addItem(name: String) {
        if (name.isNotBlank()) {
            _shoppingItems.add(ShoppingItem(name = name.trim()))
        }
    }

    fun toggleItemPurchased(itemId: String) {
        val index = _shoppingItems.indexOfFirst { it.id == itemId }
        if (index != -1) {
            val item = _shoppingItems[index]
            _shoppingItems[index] = item.copy(isPurchased = !item.isPurchased)
        }
    }

    fun deleteItem(itemId: String) {
        _shoppingItems.removeIf { it.id == itemId }
    }

    fun clearPurchasedItems() {
        _shoppingItems.removeIf { it.isPurchased }
    }

    fun getPurchasedCount(): Int {
        return _shoppingItems.count { it.isPurchased }
    }

    fun getTotalCount(): Int {
        return _shoppingItems.size
    }
}
