package com.example.android.bakingapplication.view.activity;

import com.example.android.bakingapplication.model.Step;

import java.util.List;

public interface DetailListActivityView {

    void setSteps(List<Step> steps);

    void showErrorMessage(String failureMessage);

    int getRecipeId();
}
