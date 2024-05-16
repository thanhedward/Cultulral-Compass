package me.ppvan.meplace.ui.view

import android.util.Log
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
interface ApiService {
    @GET("hot")
    suspend fun fetchData(): Array<ApiResponse>
}

data class ApiResponse(
    val title: String,
    val description: String,
    val ingredients: Array<String>,
    val image: String,
    val id: Int
)

// Tạo một đối tượng Retrofit
val retrofit = Retrofit.Builder()
    .baseUrl("https://api.sampleapis.com/coffee/") // Thay thế bằng URL gốc của API của bạn
    .addConverterFactory(GsonConverterFactory.create())
    .build()

val apiService = retrofit.create(ApiService::class.java)

suspend fun fetchDataFromApi(): String {
    return try {
        val response = apiService.fetchData()
        Log.i("tesst fetch data", response.toString())
        response.size.toString()
    } catch (e: Exception) {
        "Error: ${e.message}"
    }
}