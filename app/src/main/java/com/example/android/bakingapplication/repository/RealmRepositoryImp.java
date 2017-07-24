package com.example.android.bakingapplication.repository;

import com.example.android.bakingapplication.model.RecipeData;

import io.realm.Realm;
import io.realm.RealmResults;

public class RealmRepositoryImp implements RealmRecipeRepository {
    private Realm realm;

    @Override
    public RealmResults<RecipeData> getAllRecipes() {
        try {
            realm = Realm.getDefaultInstance();
            realm.executeTransaction(realmObject -> {
            final RealmResults<RecipeData> results = realmObject.where(RecipeData.class).findAll();
            });
        } finally {
            realm.close();
        }
    }

    @Override
    public RecipeData getSelectedRecipe() {
        try {
            realm = Realm.getDefaultInstance();
            return realm.where(RecipeData.class).findFirst();
        } finally {
            realm.close();
        }
    }

    @Override
    public void setSelectedRecipe(final RecipeData recipe) {
        try {
            realm = Realm.getDefaultInstance();
            realm.executeTransaction(realmObject -> {
            final RealmResults<RecipeData> results = realmObject.where(RecipeData.class).findAll();
            results.deleteAllFromRealm();
            realmObject.copyToRealm(recipe);

            });
        } finally {
            realm.close();
        }
    }
}
