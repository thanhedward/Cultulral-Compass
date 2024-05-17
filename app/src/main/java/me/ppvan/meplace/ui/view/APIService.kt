package me.ppvan.meplace.ui.view

import android.util.Log
import com.google.gson.annotations.SerializedName
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    @GET("get/get-all-comment")
    suspend fun fetchAllData(): Response

    @GET("get/get-destination-comment/{destinationId}")
    suspend fun fetchDataDes(@Path("destinationId") destinationId: String): Response

    @POST("post/add-comment")
    suspend fun createComment(@Body requestBody: CommentDataDto): ResponseAdddata

}

data class Response(
    val code: String,
    val message: String,
    val data: List<CommentData>
)


data class ResponseAdddata(
    val code: String,
    val message: String,
    val data: CommentData
)
data class CommentData(
    @SerializedName("_id")
    val id: String,
    val idDestination: Int,
    val username: String,
    val body: String,
    val createDate: String
)

data class CommentDataDto(
    val idDes: Int,
    val username: String,
    val body: String,
)



// Tạo một đối tượng Retrofit
val retrofit = Retrofit.Builder()
    .baseUrl("https://backend-ui-m22u.onrender.com/") // Thay thế bằng URL gốc của API của bạn
    .addConverterFactory(GsonConverterFactory.create())
    .build()

val apiService = retrofit.create(ApiService::class.java)

suspend fun fetchAllComment(): List<CommentData> {
    val response = apiService.fetchAllData()
    return response.data
}

suspend fun fetchCmtsDestination(idDestination: Int): List<CommentData> {
    val response = apiService.fetchDataDes(idDestination.toString())
    Log.i("tesst fetch data", response.toString())
    Log.i("dataaloging", response.data.toString())
    return response.data
}

suspend fun createCommemtToDes(newCmt: CommentDataDto): Int {
    val response = apiService.createComment(newCmt);
    Log.i("create new comment", response.toString())
    if(response.code.equals("0")) {
        return 0
    }
    else {
        return 1
    }
}


