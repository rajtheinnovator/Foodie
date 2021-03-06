package com.enpassio.foodie.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.enpassio.foodie.R;
import com.enpassio.foodie.activities.ItemDetailsActivity;
import com.enpassio.foodie.activities.MainActivity;
import com.enpassio.foodie.fragments.ItemDetailsFragment;
import com.enpassio.foodie.model.MenuImages;
import com.enpassio.foodie.model.RecepieList;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import static com.enpassio.foodie.util.Constants.ITEMS_DETAILS_BUNDLE_KEY;
import static com.enpassio.foodie.util.Constants.MY_INGREDIENT_ARRAYLIST__KEY;
import static com.enpassio.foodie.util.Constants.MY_SERVING_KEY;
import static com.enpassio.foodie.util.Constants.MY_STEPS_ARRAYLIST_KEY;


public class MenuListAdapter extends RecyclerView.Adapter<MenuListAdapter.ViewHolder> {

    /* Store a member variable for the popularMovies */
    private static List<RecepieList> mCurrentRecepie;
    /*check if it's two pane layout or not */
    private static boolean myBool;
    private static boolean mTwoPane = false;
    /* Store the context for easy access */
    private Context mContext;

    /* Pass in the popularMovies array into the constructor */
    public MenuListAdapter(Context context, List<RecepieList> recepieLists) {
        mCurrentRecepie = recepieLists;
        mContext = context;
    }

    /* Easy access to the context object in the recyclerview */
    private Context getContext() {
        return mContext;
    }

    @Override
    public MenuListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        /* Inflate the custom layout */
        View moviesView = inflater.inflate(R.layout.item_menu, parent, false);

        /* Return a new holder instance */
        ViewHolder viewHolder = new MenuListAdapter.ViewHolder(context, moviesView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MenuListAdapter.ViewHolder viewHolder, int position) {
        /* Get the data model based on position */
        RecepieList currentRecipeLists = mCurrentRecepie.get(position);
        /*
        Set item views based on your views and data model
         */
        viewHolder.menuItemTextView.setText(currentRecipeLists.getName());
        ArrayList<String> allImageUrls = MenuImages.getMenuImages();
        String imageUrl = allImageUrls.get(position);
        Picasso.with(mContext)
                .load(imageUrl)
                .into(viewHolder.menuItemImageView);
        if (mTwoPane) {
            ItemDetailsFragment itemDetailsFragment = new ItemDetailsFragment();
            RecepieList currentRecipeList = mCurrentRecepie.get(0);
            Bundle itemDetailsBundle = new Bundle();
            itemDetailsBundle.putParcelableArrayList(MY_INGREDIENT_ARRAYLIST__KEY, (ArrayList) currentRecipeList.getIngredients());
            itemDetailsBundle.putParcelableArrayList(MY_STEPS_ARRAYLIST_KEY, (ArrayList) currentRecipeList.getSteps());
            itemDetailsBundle.putString(MY_SERVING_KEY, String.valueOf(currentRecipeList.getServings()));
            itemDetailsFragment.setArguments(itemDetailsBundle);
            android.support.v4.app.FragmentManager manager = ((MainActivity) mContext).getSupportFragmentManager();
            manager.beginTransaction()
                    .replace(R.id.menuDetailFrame, itemDetailsFragment)
                    .commit();
        }

    }

    @Override
    public int getItemCount() {
        return mCurrentRecepie.size();
    }

    public void setRecipeData(List<RecepieList> recipeData) {
        mCurrentRecepie = recipeData;
        notifyDataSetChanged();
    }

    public void setTwoPaneStatus(boolean twoPaneStatus) {
        mTwoPane = twoPaneStatus;
    }

    /*
     Provide a direct reference to each of the views within a data item
     Used to cache the views within the item layout for fast access
     */
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        /*
        Your holder should contain a member variable
        for any view that will be set as you render a row
        */
        public final TextView menuItemTextView;
        public final ImageView menuItemImageView;
        private Context context;
        private FrameLayout menuDetailFrame;

        /*
        We also create a constructor that accepts the entire item row
        and does the view lookups to find each subview
        */
        public ViewHolder(Context context, View itemView) {
            /*
            Stores the itemView in a public final member variable that can be used
            to access the context from any ViewHolder instance.
            */
            super(itemView);
            menuItemTextView = itemView.findViewById(R.id.textMenuItem);
            menuItemImageView = itemView.findViewById(R.id.imageMenuItem);
            this.context = context;
            if (mTwoPane) {
                menuDetailFrame = itemView.findViewById(R.id.menuDetailFrame);
            }
            /* Attach a click listener to the entire row view */
            itemView.setOnClickListener(this);
        }

        /*The codes below and at some other places throughout the app related to RecyclerView has been
        * taken from multiple sources on the web including from the following @link:
        * "https://guides.codepath.com/android/using-the-recyclerview
        */
        /* Handles the row being being clicked */
        @Override
        public void onClick(View view) {
            int position = getAdapterPosition(); // gets item position
            if (!mTwoPane) {
                if (position != RecyclerView.NO_POSITION) { // Check if an item was deleted, but the user clicked it before the UI removed it
                    RecepieList currentRecipeList = mCurrentRecepie.get(position);
                    Intent itemDetailsIntent = new Intent(context, ItemDetailsActivity.class);
                    Bundle itemDetailsBundle = new Bundle();
                    itemDetailsBundle.putParcelableArrayList(MY_INGREDIENT_ARRAYLIST__KEY, (ArrayList) currentRecipeList.getIngredients());
                    itemDetailsBundle.putParcelableArrayList(MY_STEPS_ARRAYLIST_KEY, (ArrayList) currentRecipeList.getSteps());
                    itemDetailsBundle.putString(MY_SERVING_KEY, String.valueOf(currentRecipeList.getServings()));
                    itemDetailsIntent.putExtra(ITEMS_DETAILS_BUNDLE_KEY, itemDetailsBundle);
                    context.startActivity(itemDetailsIntent);
                }
            } else {
                if (position != RecyclerView.NO_POSITION) {
                    ItemDetailsFragment itemDetailsFragment = new ItemDetailsFragment();
                    RecepieList currentRecipeList = mCurrentRecepie.get(position);
                    Bundle itemDetailsBundle = new Bundle();
                    itemDetailsBundle.putParcelableArrayList(MY_INGREDIENT_ARRAYLIST__KEY, (ArrayList) currentRecipeList.getIngredients());
                    itemDetailsBundle.putParcelableArrayList(MY_STEPS_ARRAYLIST_KEY, (ArrayList) currentRecipeList.getSteps());
                    itemDetailsBundle.putString(MY_SERVING_KEY, String.valueOf(currentRecipeList.getServings()));
                    itemDetailsFragment.setArguments(itemDetailsBundle);
                    android.support.v4.app.FragmentManager manager = ((MainActivity) context).getSupportFragmentManager();
                    manager.beginTransaction()
                            .replace(R.id.menuDetailFrame, itemDetailsFragment)
                            .commit();
                }
            }

        }
    }
}
