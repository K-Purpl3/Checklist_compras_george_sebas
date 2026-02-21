package com.example.checklist_compras_george_sebas.ui.theme

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.checklist_compras_george_sebas.ui.components.AddItemDialog
import com.example.checklist_compras_george_sebas.ui.components.ShoppingItemCard
import com.example.checklist_compras_george_sebas.viewmodel.ShoppingViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShoppingListScreen(
    viewModel: ShoppingViewModel = viewModel()
) {
    var showAddDialog by remember { mutableStateOf(false) }
    val items = viewModel.shoppingItems
    val purchasedCount = viewModel.getPurchasedCount()
    val totalCount = viewModel.getTotalCount()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = "Mi Lista de Compras",
                            style = MaterialTheme.typography.titleLarge
                        )
                        if (totalCount > 0) {
                            Text(
                                text = "$purchasedCount de $totalCount comprados",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                ),
                actions = {
                    if (purchasedCount > 0) {
                        IconButton(onClick = { viewModel.clearPurchasedItems() }) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Limpiar comprados",
                                tint = MaterialTheme.colorScheme.error
                            )
                        }
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showAddDialog = true },
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Agregar artículo"
                )
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            if (items.isEmpty()) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "🛒",
                        style = MaterialTheme.typography.displayLarge
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "No hay artículos en tu lista",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Agrega tu primer artículo usando el botón +",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                        textAlign = TextAlign.Center
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(vertical = 8.dp)
                ) {
                    items(
                        items = items,
                        key = { it.id }
                    ) { item ->
                        ShoppingItemCard(
                            item = item,
                            onTogglePurchased = { viewModel.toggleItemPurchased(item.id) },
                            onDelete = { viewModel.deleteItem(item.id) }
                        )
                    }
                }
            }
        }

        if (showAddDialog) {
            AddItemDialog(
                onDismiss = { showAddDialog = false },
                onConfirm = { itemName ->
                    viewModel.addItem(itemName)
                }
            )
        }
    }
}
