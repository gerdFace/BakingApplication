package com.example.android.bakingapplication;

import android.content.Context;

import com.example.android.bakingapplication.model.RecipeData;
import com.example.android.bakingapplication.repository.RecipeRepository;
import com.example.android.bakingapplication.repository.RecipeRepositoryImpl;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

public class RecipeRepositoryTest {

    private MockRecipeData mockRecipeData = new MockRecipeData();

    private List<RecipeData> recipeList;

    private String failureMessage = "No Realm data";

    private RecipeRepositoryImpl recipeRepositoryImpl;

    @Mock
    private RecipeRepository recipeRemoteRepository;

    @Mock
    private RecipeRepository recipeLocalRepository;

    @Mock
    private Context context;

    @Mock
    private RecipeRepository.GetRecipeCallback getRecipeCallback;

    @Mock
    private RecipeRepository.LoadRecipesCallback loadRecipesCallback;

    @Captor
    private ArgumentCaptor<RecipeRepository.LoadRecipesCallback> recipesCallbackArgumentCaptor;

    @Captor
    private ArgumentCaptor<RecipeRepository.GetRecipeCallback> recipeCallbackArgumentCaptor;

    @Before
    public void setupRecipeRepository() {
        MockitoAnnotations.initMocks(this);

        recipeRepositoryImpl = new RecipeRepositoryImpl(recipeLocalRepository, recipeRemoteRepository);

        mockRecipeData.createRecipeList();

        recipeList = mockRecipeData.getRecipeList();
    }

    @Test
    public void repositoryCachesAfterFirstApiCall() {
        makeTwoCallsToRepository(loadRecipesCallback);

        verify(recipeRemoteRepository).getRecipes(any(RecipeRepository.LoadRecipesCallback.class));
    }

    private void makeTwoCallsToRepository(RecipeRepository.LoadRecipesCallback callback) {
        recipeRepositoryImpl.getRecipes(callback);

        verify(recipeLocalRepository).getRecipes(recipesCallbackArgumentCaptor.capture());

        recipesCallbackArgumentCaptor.getValue().onDataNotAvailable(failureMessage);

        verify(recipeRemoteRepository).getRecipes(recipesCallbackArgumentCaptor.capture());

        recipesCallbackArgumentCaptor.getValue().onRecipesLoaded(recipeList);

        recipeRepositoryImpl.getRecipes(callback);
    }
}
