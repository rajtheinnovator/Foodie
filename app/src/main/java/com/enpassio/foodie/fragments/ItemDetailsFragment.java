package com.enpassio.foodie.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.enpassio.foodie.R;
import com.enpassio.foodie.adapters.StepsPagerAdapter;
import com.enpassio.foodie.model.Ingredient;
import com.enpassio.foodie.model.Step;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ABHISHEK RAJ on 8/14/2017.
 */

public class ItemDetailsFragment extends Fragment {


    StepsPagerAdapter adapterViewPager;
    String mIngredientString;
    Bundle bundle;

    public ItemDetailsFragment() {
        //required empty constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bundle = getArguments();
        //initialize the variable mIngredientString
        mIngredientString = "";
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
                /* Inflate the layout for this fragment */
        View rootView = inflater.inflate(R.layout.fragment_item_details, container, false);

        TextView ingredientTextView = rootView.findViewById(R.id.ingredientTextView);
        List<Ingredient> ingredients = bundle.getParcelableArrayList("myIngredients");

        for (Ingredient ingredient : ingredients) {
            if (!(ingredient.equals(null) || ingredient.equals("null")))
                mIngredientString += "\u25CF" + " " + ingredient.getIngredient().trim() + "\n";
        }
        ingredientTextView.setText(mIngredientString);
        ImageButton previousImageButton = rootView.findViewById(R.id.prevImageButton);
        ImageButton nextImageButton = rootView.findViewById(R.id.nextImageButton);

       /* Find and set a ViewPager so that main screen/poster screen can be inflated with different fragments */
        final ViewPager viewPager = rootView.findViewById(R.id.viewPager);
        ArrayList<Step> steps = bundle.getParcelableArrayList("mySteps");
        adapterViewPager = new StepsPagerAdapter(this.getChildFragmentManager());
        adapterViewPager.setPAGE_COUNT(steps.size());
        adapterViewPager.setSteps(steps);
        viewPager.setAdapter(adapterViewPager);
        viewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                viewPager.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });
        return rootView;
    }
}
