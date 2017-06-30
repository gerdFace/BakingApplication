package com.example.android.bakingapplication;

import com.example.android.bakingapplication.activity.MainActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
abstract class ApplicationBindingModule {
	
	@ContributesAndroidInjector
	abstract MainActivity mainActivity();
}
