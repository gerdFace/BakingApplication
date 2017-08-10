package com.example.android.bakingapplication

import com.example.android.bakingapplication.model.Ingredient
import com.example.android.bakingapplication.model.RecipeData
import com.example.android.bakingapplication.model.Step

class MockRecipeData {
    val step1 = Step()
    val step2 = Step()
    val step3 = Step()
    val step4 = Step()

    val ingredient1 = Ingredient()
    val ingredient2 = Ingredient()
    val ingredient3 = Ingredient()
    val ingredient4 = Ingredient()

    val recipe1 = RecipeData()
    val recipe2 = RecipeData()

    val recipeList: MutableList<RecipeData> = mutableListOf(recipe1, recipe2)

    fun createRecipeList() {
        setupMockIngredients()
        setupMockSteps()
        setupMockRecipes()
    }

    fun setupMockIngredients() {
        ingredient1.ingredient = "eggs"
        ingredient1.measure = "unit"
        ingredient1.quantity = 3.5f

        ingredient2.ingredient = "milk"
        ingredient2.measure = "cup"
        ingredient2.quantity = 2f

        ingredient3.ingredient = "cream cheese"
        ingredient3.measure = "oz"
        ingredient3.quantity = 3f

        ingredient4.ingredient = "butter"
        ingredient4.measure = "TBLSP"
        ingredient4.quantity = 1000f
    }

    fun setupMockSteps() {
        step1.id = 0
        step1.shortDescription = "Something short and sweet"
        step1.description = "Here's a big long description. It's such a serious description. My God! What a description!"
        step1.thumbnailURL = "https://www.youtube.com/watch?v=BB0DU4DoPP4"
        step1.videoURL = ""

        step2.id = 1
        step2.shortDescription = "Shorty"
        step2.description = "Long, Long, Long, Long!"
        step2.thumbnailURL = ""
        step2.videoURL = "https://www.youtube.com/watch?v=1cQh1ccqu8M"

        step3.id = 2
        step3.shortDescription = "Short description"
        step3.description = "Long description..............................."
        step3.thumbnailURL = ""
        step3.videoURL = "https://www.youtube.com/watch?v=_1hgVcNzvzY"

        step4.id = 3
        step4.shortDescription = "Shor descr"
        step4.description = "LONG DESCRRIPPTIONN"
        step4.thumbnailURL = ""
        step4.videoURL = "https://www.youtube.com/watch?v=VbgT70vuyAQ"
    }

    fun setupMockRecipes() {
        recipe1.id = 0
        recipe1.image = R.drawable.nutella_pie.toString()
        recipe1.name = "Indiana Sugar Cream Pie"
        recipe1.ingredients.add(ingredient1)
        recipe1.ingredients.add(ingredient2)
        recipe1.servings = 200
        recipe1.steps.add(step1)
        recipe1.steps.add(step2)

        recipe2.id = 1
        recipe2.image = R.drawable.nutella_pie.toString()
        recipe2.name = "Mashed Potato Fudge"
        recipe2.ingredients.add(ingredient3)
        recipe2.ingredients.add(ingredient4)
        recipe2.servings = 200
        recipe2.steps.add(step1)
        recipe2.steps.add(step2)
        recipe2.steps.add(step3)
        recipe2.steps.add(step4)
    }
}