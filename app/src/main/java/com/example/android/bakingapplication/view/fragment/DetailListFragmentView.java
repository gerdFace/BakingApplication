package com.example.android.bakingapplication.view.fragment;

import com.example.android.bakingapplication.model.Step;

import java.util.List;

public interface DetailListFragmentView {

    void showSteps(List<Step> steps);

    int getRecipeId();
}
