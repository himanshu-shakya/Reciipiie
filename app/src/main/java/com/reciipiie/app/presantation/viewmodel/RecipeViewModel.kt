package com.reciipiie.app.presantation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.reciipiie.app.common.utils.Result
import com.reciipiie.app.common.utils.UiState
import com.reciipiie.app.data.model.Recipe
import com.reciipiie.app.domain.repository.RecipeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RecipeViewModel(
    private val recipeRepository: RecipeRepository
) : ViewModel() {
    private val _randomRecipeFlow = MutableStateFlow<UiState<List<Recipe>>>(UiState.Idle)
    val randomRecipeFlow = _randomRecipeFlow.asStateFlow()

    private val _popularRecipesFlow = MutableStateFlow<List<Recipe>>(emptyList())
    val popularRecipesFlow = _popularRecipesFlow.asStateFlow()

    fun getRandomRecipe(
        number:Int,
        includeTags: String?,
        excludeTags: String?,
    ){
        _randomRecipeFlow.update { UiState.Loading }
        viewModelScope.launch {
            when(val result = recipeRepository.getRandomRecipes(number, includeTags, excludeTags)){
                is Result.Error -> {
                    _randomRecipeFlow.update { UiState.Error(result.error) }
                }
                is Result.Success -> {
                    val recipes = result.data
                    _randomRecipeFlow.update { UiState.Success(recipes) }

                    // Update popular recipes with the first 5 recipes from the fetched list
                    val popularRecipes = if (recipes.size > 5) recipes.take(5) else recipes
                    _popularRecipesFlow.update { popularRecipes }
                }
            }
        }
    }
}
