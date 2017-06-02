package com.hudboyz.android.bakingapplication;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.bumptech.glide.Glide;
import java.util.List;
import timber.log.Timber;

public class RecipeCardAdapter extends RecyclerView.Adapter<RecipeCardAdapter.RecipeViewHolder> {

    /* FEATHER IN YOUR CAP COMMENT:
     * Soon enough, JW's library Timber will be the default Android logging framework.
     *
     * One of the biggest selling points is not having to create TAG's.
     *
     * Not only is it wayyy better, but it also has several particularly cute puns.
     *
     * After you check out how it is set up and if you're open to giving it a try, you can delete Lines #6 + #29
     */
    public static final String TAG = RecipeCardAdapter.class.getSimpleName();

    /* FEATHER IN YOUR CAP COMMENT:
     *  Right now, there's not a whole lot that you can do to avoid passing around + storing the context like this.
     *  It's frowned upon, but like I said, a lot of times you don't really have a choice.
     *
     *  [Enter Dagger]
     */
    private Context context;
    private List<KRecipe> recipeList;

    /* FEATHER IN YOUR CAP COMMENT:
     * I think we've talked about this briefly before, definitely a matter of opinion, but I'd rather have this
      * as its own class.  Easier to find, separation of concerns, etc.
      *
      * As an added bonus, because of my OCD (big nod to Kotlin here), they've addressed Java's terrible problem of having:
      *     public    ==   public
      *     protected ==   protected
      *               ==   default
      *     private   ==   private
      *
      * I really hate this because it causes my eyes to play tricks on me, I just always want to write a visibility modifier
      * and leaving it blank for default makes me sad.
      *
      * Another random thought about this: If you do go with an inner class, I'm a fan of always having your inner classes at the very
      * bottom of the file.  That way, you always know WHERE they are and you don't have to worry about them living right next to the
      * outer classes' constructor and shit.
      *
      * tldr: moving it to a separate class will get rid of the warnings for "Access can be package private".
      *       me no rikey warnings if they can be avoided...
      *
     */

    public class RecipeViewHolder extends RecyclerView.ViewHolder {
        /* FEATHER IN YOUR CAP COMMENT:
         * Butterknife will clean this up a bit...
         */

        @BindView(R.id.dessert_name)
        public TextView dessertName;

        @BindView(R.id.number_of_steps)
        public TextView numberOfSteps;

        @BindView(R.id.number_of_ingredients)
        public TextView numberOfIngredients;

        @BindView(R.id.number_of_servings)
        public TextView numberOfServings;

        @BindView(R.id.recipe_image)
        public ImageView thumbnail;

        public RecipeViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        public void setCardText(KRecipe recipe) {
            if (recipe != null) {
                dessertName.setText(recipe.getDessertName());

                String stepsLabel = context.getResources().getString(R.string.steps);
                String ingredientsLabel = context.getResources().getString(R.string.ingredients);
                String servingsLabel = context.getResources().getString(R.string.servings);

                numberOfSteps.setText(String.format("%s %s", stepsLabel, recipe.getNumberOfSteps()));
                numberOfIngredients.setText(String.format("%s %s", ingredientsLabel, recipe.getNumberOfIngredients()));
                numberOfServings.setText(String.format("%s %s", servingsLabel, recipe.getNumberOfServings()));
            }
        }
    }

    public RecipeCardAdapter(Context mContext, List<KRecipe> recipeList) {
        this.context = mContext;
        this.recipeList = recipeList;
    }

    @Override
    public RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_card, parent, false);
        return new RecipeViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final RecipeViewHolder holder, int position) {
        KRecipe recipe = recipeList.get(position);
        holder.setCardText(recipe);

        /* FEATHER IN YOUR CAP COMMENT:
         * Calling context.getResources.getString() is superfluous, you can just use: context.getString()
         * https://stackoverflow.com/questions/8999591/difference-between-getstring-and-getresources-getstring
         */


        /* FEATHER IN YOUR CAP COMMENT:
         * Possible way to refactor this and clean it up. Not that it is particularly "dirty", just a possible way to refactor...
         *
         * This is another reason why I don't like having the holder and the adapter in the same class-- by it's very nature it's
         * violating Single Responsibility Principle (SRP).
         *
         * I would consider make the argument that a RecipeViewHolder should be responsible for taking a KRecipe and initializing its views.
         * I am a card-carrying member of the "Views should be dumb" club, but it's not like it's doing any kind of every processing/parsing
         * to go from KRecipe (POJO) ---> RecipeView
         *
         * Thoughts?
         */
        //holder.dessertName.setText(recipe.getDessertName());
        //holder.numberOfSteps.setText(String.format("%s %s", context.getResources().getString(R.string.steps), recipe.getNumberOfSteps()));
        //holder.numberOfIngredients.setText(String.format("%s %s", context.getResources().getString(R.string.ingredients), recipe.getNumberOfIngredients()));
        //holder.numberOfServings.setText(String.format("%s %s", context.getResources().getString(R.string.servings), recipe.getNumberOfServings()));

        Timber.d("Glide fetching thumbnail from: %d", recipe.getThumbnail());

        /* REVIEW: QUESTION--
         * When you first started with Glide, you were having problems with this...
         * What was the issue again?
         */
        Glide.with(context).load(recipe.getThumbnail()).into(holder.thumbnail);
    }

    @Override
    public int getItemCount() {
        return recipeList.size();
    }


}

