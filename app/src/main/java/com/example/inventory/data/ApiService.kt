package com.example.inventory.data

import kotlinx.coroutines.flow.Flow
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    @GET("api/employees")
    suspend fun getEmployees(): List<Item>

    @GET("api/employees/{id}")
    suspend fun getEmployee(@Path("id") id: Int): Item

    @POST("api/employees")
    suspend fun addEmployee(@Body employee: Item): Item

    @PUT("api/employees/{id}")
    suspend fun updateEmployee(@Path("id") id: Int, @Body employee: Item): Response<Unit>

    @DELETE("api/employees/{id}")
    suspend fun deleteEmployee(@Path("id") id: Int): Response<Unit>
}
