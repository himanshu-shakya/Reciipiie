package com.reciipiie.app.data.model

// model/Recipe.kt
data class Recipe(
    val id: Int,
    val title: String,
    val image: String,
    val servings: Int,
    val readyInMinutes: Int,
    val pricePerServing: Double,
    val extendedIngredients: List<Ingredient>,
    val analyzedInstructions: List<Instruction>,
    val summary: String
)

// model/Ingredient.kt
data class Ingredient(
    val name: String,
    val amount: Double,
    val unit: String
)

// model/Instruction.kt
data class Instruction(
    val steps: List<Step>
)

// model/Step.kt
data class Step(
    val number: Int,
    val step: String,
    val equipment: List<Equipment>
)

// model/Equipment.kt
data class Equipment(
    val name: String
)

// model/RecipeResponse.kt
data class RecipeResponse(
    val recipes: List<Recipe>
)
