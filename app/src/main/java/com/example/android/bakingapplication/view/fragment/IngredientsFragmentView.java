package com.example.android.bakingapplication.view.fragment;

import com.example.android.bakingapplication.model.Ingredient;
import java.util.List;

public interface IngredientsFragmentView {

    void showIngredients(List<Ingredient> ingredientList);

    void showErrorMessage(String failureMessage);

    int getRecipeId();
}
