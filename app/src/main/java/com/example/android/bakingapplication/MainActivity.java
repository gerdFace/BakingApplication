package com.example.android.bakingapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.recipe_image)
    ImageView recipeImage;

    @BindView(R.id.dessert_name)
    TextView dessertName;

    @BindView(R.id.number_of_steps)
    TextView numberOfSteps;

    @BindView(R.id.number_of_ingredients)
    TextView numberOfIngredients;

    @BindView(R.id.number_of_servings)
    TextView numberOfServings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }
}
