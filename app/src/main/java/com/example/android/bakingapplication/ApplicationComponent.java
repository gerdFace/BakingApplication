package com.example.android.bakingapplication;

import com.example.android.bakingapplication.activity.BakingApplication;
import com.example.android.bakingapplication.activity.MainActivity;

import javax.inject.Singleton;
import dagger.Component;

@Singleton @Component(modules = {ApplicationModule.class})
public interface ApplicationComponent {
	void inject(BakingApplication target);
	void inject(MainActivity target);
}
