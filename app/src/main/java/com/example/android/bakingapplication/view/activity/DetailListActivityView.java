package com.example.android.bakingapplication.view.activity;

import com.example.android.bakingapplication.model.Step;

public interface DetailListActivityView {

    void setStep(Step step);

    void showErrorMessage(String failureMessage);

    int getRecipeId();

    int getStepSelectedPosition();
}
