package com.enpassio.foodie.adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.widget.ImageButton;

import com.enpassio.foodie.fragments.StepsFragment;
import com.enpassio.foodie.model.Step;

import java.util.ArrayList;

import static com.enpassio.foodie.util.Constants.MY_POSITION_KEY;
import static com.enpassio.foodie.util.Constants.MY_STEPS_ARRAYLIST_KEY;


public class StepsPagerAdapter extends FragmentPagerAdapter {
    /* Total number of pages/fragments in the MainActivity */
    static int PAGE_COUNT;
    StepsFragment stepsFragment;
    private Context context;
    private ArrayList<Step> steps;
    private int currentPosition;
    private ImageButton previousImageButton;
    private ImageButton nextImageButton;
    private int size;


    public StepsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public void setPAGE_COUNT(int page_count) {
        PAGE_COUNT = page_count;
    }

    public void setSteps(ArrayList<Step> step) {
        steps = step;
    }

    public int getCurrentPosition() {
        return currentPosition;
    }

    public void setImageButtonVisibility(ImageButton previousImageButton, ImageButton nextImageButton, int size) {
        this.previousImageButton = previousImageButton;
        this.nextImageButton = nextImageButton;
        this.size = size;
    }

    /* set the position of fragments */
    @Override
    public Fragment getItem(int position) {
        currentPosition = position;
        stepsFragment = new StepsFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(MY_POSITION_KEY, position);
        bundle.putParcelableArrayList(MY_STEPS_ARRAYLIST_KEY, steps);
        stepsFragment.setArguments(bundle);
        return stepsFragment;
    }

    @Override
    public float getPageWidth(int position) {
        //show that there are more fragments to the right
        return (0.95f);
    }

    /* get total fragment count for the activity */
    @Override
    public int getCount() {
        return PAGE_COUNT;
    }
}