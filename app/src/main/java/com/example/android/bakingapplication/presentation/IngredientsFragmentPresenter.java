package com.example.android.bakingapplication.presentation;

import com.example.android.bakingapplication.view.fragment.IngredientsFragmentView;

public interface IngredientsFragmentPresenter {

    void loadIngredients();

    void setView(IngredientsFragmentView view);
}
