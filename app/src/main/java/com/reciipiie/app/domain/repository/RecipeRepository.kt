package com.reciipiie.app.domain.repository

import com.reciipiie.app.common.utils.Result
import com.reciipiie.app.data.model.Recipe

interface RecipeRepository {
    suspend fun getRandomRecipes(number: Int, includeTags: String?, excludeTags: String?): Result<List<Recipe>>
}
