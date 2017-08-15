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

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
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
    private RecipeRepository.LoadRecipesCallback loadRecipesCallback;

    @Mock
    private RecipeRepository.GetRecipeCallback getRecipeCallback;

    @Mock
    private RecipeRepository.GetStepsCallback getStepsCallback;

    @Mock
    private RecipeRepository.GetIngredientsCallback getIngredientsCallback;

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
    public void shouldGetRecipesFromLocalDataSourceIfDatabaseIsNotEmpty() {
        recipeRepositoryImpl.getRecipes(loadRecipesCallback);

        verify(localDataSource).getRecipes(any(RecipeRepository.LoadRecipesCallback.class));
    }

    @Test
    public void checkRepositoryCachesAfterFirstApiCall() {
        makeTwoCallsToRepository(loadRecipesCallback);

        verify(networkDataSource).getRecipes(any(RecipeRepository.LoadRecipesCallback.class));

        verify(localDataSource).getRecipes(any(RecipeRepository.LoadRecipesCallback.class));

        // Redundant check for data caching
        assertThat(recipeList, is(new ArrayList<>(recipeRepositoryImpl.cachedRecipes.values())));
    }

    @Test
    public void shouldGetRecipesFromNetworkDataSourceWhenLocalDataSourceUnavailable() {
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

    @Test
    public void shouldGetRecipeFromLocalDataSourceWithRecipeId() {
        recipeRepositoryImpl.getRecipe(0, getRecipeCallback);

        verify(localDataSource).getRecipe(eq(0), any(RecipeRepository.GetRecipeCallback.class));
    }

    @Test
    public void shouldGetRecipeFromCacheWithRecipeId() {
        // Load recipes
        recipeRepositoryImpl.getRecipes(loadRecipesCallback);
        setRecipesAvailable(localDataSource, recipeList);

        // Get single recipe from repository with ID
        recipeRepositoryImpl.getRecipe(0, getRecipeCallback);

        // Check that database was not accessed
        verify(localDataSource, never()).getRecipe(anyInt(), any(RecipeRepository.GetRecipeCallback.class));

        // Get cached recipes
        List<RecipeData> recipe = new ArrayList<>(recipeRepositoryImpl.cachedRecipes.values());

        // Verify cached recipe has expected data
        assertThat((recipeList.get(0)), is(recipe.get(0)));
    }

    @Test
    public void shouldGetStepsFromDatabaseIfCacheIsEmpty() {
        // Load recipes
        recipeRepositoryImpl.getRecipes(loadRecipesCallback);
        setRecipesAvailable(localDataSource, recipeList);

        // Nullify cached recipes
        recipeRepositoryImpl.cachedRecipes = null;

        recipeRepositoryImpl.getSteps(0, getStepsCallback);

        verify(localDataSource).getSteps(anyInt(), any(RecipeRepository.GetStepsCallback.class));
    }

    @Test
    public void shouldGetStepsFromCacheIfCacheIsNotEmpty() {
        // Load recipes
        recipeRepositoryImpl.getRecipes(loadRecipesCallback);
        setRecipesAvailable(localDataSource, recipeList);

        recipeRepositoryImpl.getSteps(0, getStepsCallback);

        verify(localDataSource, never()).getSteps(anyInt(), any(RecipeRepository.GetStepsCallback.class));
    }

    @Test
    public void shouldGetIngredientsFromDatabaseIfCacheIsEmpty() {
        // Load recipes
        recipeRepositoryImpl.getRecipes(loadRecipesCallback);
        setRecipesAvailable(localDataSource, recipeList);

        // Nullify cached recipes
        recipeRepositoryImpl.cachedRecipes = null;

        // Load ingredients from repository
        recipeRepositoryImpl.getIngredients(0, getIngredientsCallback);

        // Confirm getIngredients method in localDataSource is called
        verify(localDataSource).getIngredients(anyInt(), any(RecipeRepository.GetIngredientsCallback.class));
    }

    @Test
    public void shouldGetIngredientsFromCacheIfCacheIsNotEmpty() {
        // Load recipes
        recipeRepositoryImpl.getRecipes(loadRecipesCallback);
        setRecipesAvailable(localDataSource, recipeList);

        // Load ingredients from repository
        recipeRepositoryImpl.getSteps(0, getStepsCallback);

        // Confirm getIngredients method in localDataSource is never called
        verify(localDataSource, never()).getSteps(anyInt(), any(RecipeRepository.GetStepsCallback.class));
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
