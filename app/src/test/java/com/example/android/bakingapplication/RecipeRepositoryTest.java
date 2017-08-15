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

    private String failureMessage = "Could not load recipe data";

    private RecipeRepositoryImpl recipeRepositoryImpl;

    @Mock
    private RecipeRepository networkDataSource;

    @Mock
    private RecipeRepository localDataSource;

    @Mock
    private Context context;

    @Mock
    private RecipeRepository.GetRecipeCallback getRecipeCallback;

    @Mock
    private RecipeRepository.LoadRecipesCallback loadRecipesCallback;

    @Captor
    private ArgumentCaptor<RecipeRepository.LoadRecipesCallback> recipesCallbackCaptor;

    @Captor
    private ArgumentCaptor<RecipeRepository.GetRecipeCallback> recipeCallbackCaptor;

    @Before
    public void setupRecipeRepository() {
        MockitoAnnotations.initMocks(this);

        recipeRepositoryImpl = new RecipeRepositoryImpl(localDataSource, networkDataSource);

        mockRecipeData.createRecipeList();

        recipeList = mockRecipeData.getRecipeList();
    }

    @Test
    public void repositoryCachesAfterFirstApiCall() {
        makeTwoCallsToRepository(loadRecipesCallback);

        verify(networkDataSource).getRecipes(any(RecipeRepository.LoadRecipesCallback.class));
    }

    @Test
    public void getRecipesFromNetworkDataSourceWhenLocalDataSourceUnavailable() {
        recipeRepositoryImpl.getRecipes(loadRecipesCallback);

        setRecipesNotAvailable(localDataSource);

        setRecipesAvailable(networkDataSource, recipeList);

        verify(loadRecipesCallback).onRecipesLoaded(recipeList);
    }

    @Test
    public void shouldGetOnDataNotAvailableCallbackWhenBothDataSourcesUnavailable() {
        recipeRepositoryImpl.getRecipes(loadRecipesCallback);

        setRecipesNotAvailable(localDataSource);

        setRecipesNotAvailable(networkDataSource);

        verify(loadRecipesCallback).onDataNotAvailable(failureMessage);
    }

    private void makeTwoCallsToRepository(RecipeRepository.LoadRecipesCallback callback) {
        recipeRepositoryImpl.getRecipes(callback);

        verify(localDataSource).getRecipes(recipesCallbackCaptor.capture());

        recipesCallbackCaptor.getValue().onDataNotAvailable(failureMessage);

        verify(networkDataSource).getRecipes(recipesCallbackCaptor.capture());

        recipesCallbackCaptor.getValue().onRecipesLoaded(recipeList);

        recipeRepositoryImpl.getRecipes(callback);
    }

    private void setRecipesNotAvailable(RecipeRepository recipeRepository) {
        verify(recipeRepository).getRecipes(recipesCallbackCaptor.capture());
        recipesCallbackCaptor.getValue().onDataNotAvailable(failureMessage);
    }

    private void setRecipesAvailable(RecipeRepository recipeRepository, List<RecipeData> recipes) {
        verify(recipeRepository).getRecipes(recipesCallbackCaptor.capture());
        recipesCallbackCaptor.getValue().onRecipesLoaded(recipes);
    }
}
