package com.example.android.bakingapplication;

import com.example.android.bakingapplication.model.RecipeData;
import com.example.android.bakingapplication.presentation.MainActivityPresenter;
import com.example.android.bakingapplication.repository.RecipeRepository;
import com.example.android.bakingapplication.view.activity.MainActivityView;

import org.junit.Before;

import java.util.List;

import static org.mockito.Mockito.mock;

public class MainActivityPresenterTest {

    private RecipeRepository mockRecipeRepository;
    private MainActivityView mockView;
    private MainActivityPresenter presenter;
    private List<RecipeData> recipeList;

    @Before
    public void setup() {
        mockRecipeRepository = mock(RecipeRepository.class);

        MockRecipeData mockRecipeData = new MockRecipeData();
        mockRecipeData.createRecipeList();
        recipeList = mockRecipeData.getRecipeList();
    }
}

