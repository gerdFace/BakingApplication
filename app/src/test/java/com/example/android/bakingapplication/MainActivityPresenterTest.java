package com.example.android.bakingapplication;

import com.example.android.bakingapplication.model.RecipeData;
import com.example.android.bakingapplication.presentation.MainActivityPresenter;
import com.example.android.bakingapplication.repository.RecipeRepository;
import com.example.android.bakingapplication.view.activity.MainActivityView;

import org.junit.Before;

import static org.mockito.Mockito.mock;

public class MainActivityPresenterTest {

    private RecipeRepository mockUserRepository;
    private MainActivityView mockView;
    private MainActivityPresenter presenter;
    private RecipeData recipeList;

    @Before
    public void setup() {
        mockUserRepository = mock(RecipeRepository.class);

        Mock new MockRecipeData().;

    }
}

