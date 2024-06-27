package com.reciipiie.app.data.remote.api// data/remote/api/RecipeApiService.kt
import com.reciipiie.app.data.model.RecipeResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface RecipeApiService {
    @GET("recipes/random")
    suspend fun getRandomRecipe(
        @Query("apiKey") apiKey: String,
        @Query("number") number: Int,
        @Query("include-tags") includeTags: String?,
        @Query("exclude-tags") excludeTags: String?
    ): RecipeResponse
}
