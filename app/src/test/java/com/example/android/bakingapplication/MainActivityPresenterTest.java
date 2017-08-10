package com.example.android.bakingapplication;

import com.example.android.bakingapplication.model.RecipeData;
import com.example.android.bakingapplication.presentation.MainActivityPresenter;
import com.example.android.bakingapplication.repository.RecipeRepository;
import com.example.android.bakingapplication.view.activity.MainActivityView;

import org.junit.Before;

import static org.mockito.Mockito.mock;

public class MainActivityPresenterTest {

    RecipeRepository mockUserRepository;
    MainActivityView mockView;
    MainActivityPresenter presenter;
    RecipeData recipeList;

    @Before
    public void setup() {
        mockUserRepository = mock(RecipeRepository.class);

        recipeList = new RecipeData();

    }
}

