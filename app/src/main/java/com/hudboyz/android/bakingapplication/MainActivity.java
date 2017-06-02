package com.hudboyz.android.bakingapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getSimpleName();

    /* REVIEW: TIGHTEN UP COMMENT--
     * We always want to limit the scope of variables as much as possible... (minus my comment in RecipeCardAdapter #40, ;-) )
     * Boy Scout Rule when it comes to compiler/lint warnings.
     */
    private RecyclerView recyclerView;
    private RecipeCardAdapter recipeCardAdapter;
    private List<KRecipe> recipeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       /* REVIEW: QUESTION--
        * Initially you were started to use butterknife, now no butterknife? Is there a reason?
        * I'm cool either way, just curious.
        *
        * If we're out on butterknife but for this project, then let's try and keep our build.gradle file clean and remove it.
        */

       /* FEATHER IN YOUR CAP COMMENT:
        * I've seen lots of different approaches for naming resource id's, personally, I don't think you get all that much out
        * of using rv == recycler_view, tv == text_view, etc. etc.
        *
        * I'd rather just type it out and get my auto-complete on.
        *
        * Thoughts?
        */

        //        ButterKnife.bind(this);
        recyclerView = (RecyclerView) findViewById(R.id.rv_recipe);

        recipeList = new ArrayList<>();
        recipeCardAdapter = new RecipeCardAdapter(this, recipeList);

        /* REVIEW: TIGHTEN UP COMMENT--
         * Something something, blah blah, I don't like Hungarian notation.
         *
         * But like we've talked about, I'm cool as long as we're on the same page with one approach
         * and use that approach consistently.
         *
         * My real gripe here is that this isn't actually correct Hungarian notation.
         * the m is used to express that the variable in question is a member variable (field),
         * which, in this case, the layoutManager is a local variable.
         */
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(recipeCardAdapter);
        addFakeRecipes();
    }

    private void addFakeRecipes() {
        int[] thumbnails = new int[] {
            R.mipmap.nutella_pie,
            R.mipmap.brownies,
            R.mipmap.yellow_cake,
            R.mipmap.cheesecake
        };

        KRecipe testRecipe = new KRecipe("Nutella Pie", "6", "8", "8", thumbnails[0]);
        Log.d(TAG, "Image URL: " + thumbnails[0]);
        recipeList.add(testRecipe);

        testRecipe = new KRecipe("Brownies", "6", "10", "10", thumbnails[1]);
        recipeList.add(testRecipe);

        testRecipe = new KRecipe("Yellow Cake", "3", "10", "15", thumbnails[2]);
        recipeList.add(testRecipe);

        testRecipe = new KRecipe("Cheesecake", "10", "4", "6", thumbnails[3]);
        recipeList.add(testRecipe);

        recipeCardAdapter.notifyDataSetChanged();
    }
}
