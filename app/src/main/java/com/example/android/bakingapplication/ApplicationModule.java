package com.example.android.bakingapplication;

import android.app.Application;
import android.content.Context;
import com.example.android.bakingapplication.model.FakeRecipeData;
import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {
	
	private Application application;
	
	public ApplicationModule(Application application) {
		this.application = application;
	}
	
	@Provides
	@Singleton
	public Context provideContext() {
		return application;
	}
	
	@Provides
	@Singleton
	public FakeRecipeData provideFakeRecipeData() {
		return FakeRecipeData.get();
	}
}

