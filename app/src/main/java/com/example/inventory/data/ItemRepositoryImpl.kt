package com.example.inventory.data

import ItemsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response

class EmployeeRepositoryImpl(
    private val apiService: ApiService
) : ItemsRepository {

    override suspend fun getAllItemsStream(): Flow<List<Item>> {
        return flow {
            val items = apiService.getEmployees() // Make the network call
            emit(items) // Emit the result to the Flow
        }
    }

    override suspend fun getItemStream(id: Int): Flow<Item?> {
        return flow {
            val item = apiService.getEmployee(id)  // Make the network call
            emit(item)  // Emit the result to the Flow
        }
    }

    override suspend fun insertItem(item: Item): Item {
        return apiService.addEmployee(item)
    }

    override suspend fun deleteItem(id: Int): Response<Unit> {
        return apiService.deleteEmployee(id)
    }

    override suspend fun updateItem(id: Int, item: Item): Response<Unit> {
        return apiService.updateEmployee(id, item)
    }
}
