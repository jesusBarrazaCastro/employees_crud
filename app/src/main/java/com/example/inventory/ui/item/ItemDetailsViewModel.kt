/*
 * Copyright (C) 2023 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.inventory.ui.item
import retrofit2.Response

import ItemsRepository
import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

/**
 * ViewModel to retrieve, update and delete an item from the [ItemsRepository]'s data source.
 */
class ItemDetailsViewModel(
    savedStateHandle: SavedStateHandle,
    private val itemsRepository: ItemsRepository
) : ViewModel() {

    private val itemId: Int = checkNotNull(savedStateHandle[ItemDetailsDestination.itemIdArg])

    // Define a MutableStateFlow to hold the UI state
    private val _uiState = MutableStateFlow(ItemDetailsUiState())
    val uiState: StateFlow<ItemDetailsUiState> = _uiState.asStateFlow()

    init {
        // Launch a coroutine to collect the item details stream
        viewModelScope.launch {
            itemsRepository.getItemStream(itemId)
                //.filterNotNull()
                .collect { item ->
                    if (item != null) {
                        _uiState.value = ItemDetailsUiState(itemDetails = item.toItemDetails())
                    }
                }
        }
    }

    suspend fun deleteItem() {
        try {
            val response = itemsRepository.deleteItem(uiState.value.itemDetails.id)
            if (response.isSuccessful) {
                // Handle successful deletion
            } else {
                // Handle error, possibly show a message
            }
        } catch (e: Exception) {
            // Handle unexpected errors
            Log.e("DeleteItem", "Error deleting item", e)
        }
    }

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}

/**
 * UI state for ItemDetailsScreen
 */
data class ItemDetailsUiState(
    val outOfStock: Boolean = true,
    val itemDetails: ItemDetails = ItemDetails()
)
