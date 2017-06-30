package com.example.android.bakingapplication;

import com.example.android.bakingapplication.activity.BakingApplication;
import com.example.android.bakingapplication.activity.MainActivity;

import javax.inject.Singleton;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

@Component(modules = {ApplicationModule.class, ApplicationBindingModule.class, AndroidSupportInjectionModule.class})

@ApplicationScope
public interface ApplicationComponent extends AndroidInjector<BakingApplication> {
	
	@Component.Builder
	abstract class Builder extends AndroidInjector.Builder<BakingApplication> {
		
	}
	// void inject(BakingApplication target);
	// void inject(MainActivity target);
}
