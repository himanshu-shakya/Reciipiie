package com.reciipiie.app.data.repository

import android.util.Log
import com.reciipiie.app.common.utils.Result
import com.reciipiie.app.data.model.Recipe
import com.reciipiie.app.data.remote.api.RecipeApiService
import com.reciipiie.app.domain.repository.RecipeRepository

// data/repository/RecipeRepositoryImpl.kt


class RecipeRepositoryImpl(
    private val apiService: RecipeApiService,
    private val apiKey: String
) : RecipeRepository {
    override suspend fun getRandomRecipes(number: Int, includeTags: String?, excludeTags: String?): Result<List<Recipe>> {
        return try  {

            val recipes = apiService.getRandomRecipe(apiKey, number, includeTags, excludeTags).recipes
            Log.i("TAG", "getRandomRecipes: $recipes")
            Result.Success(recipes)
        }catch (e:Exception){
            Result.Error(e.message?:"Unknown Error")
        }
    }
}
