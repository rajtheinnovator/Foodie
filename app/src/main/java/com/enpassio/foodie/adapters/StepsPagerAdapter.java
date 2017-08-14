package com.enpassio.foodie.adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import com.enpassio.foodie.fragments.StepsFragment;
import com.enpassio.foodie.model.Step;

import java.util.ArrayList;

/**
 * Created by ABHISHEK RAJ on 8/14/2017.
 */

public class StepsPagerAdapter extends FragmentPagerAdapter {
    /* Total number of pages/fragments in the MainActivity */
    int PAGE_COUNT;
    StepsFragment stepsFragment;
    private Context context;
    private ArrayList<Step> steps;


    public StepsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public void setPAGE_COUNT(int page_count) {
        PAGE_COUNT = page_count;
    }

    public void setSteps(ArrayList<Step> step) {
        steps = step;
    }

    /* set the position of fragments */
    @Override
    public Fragment getItem(int position) {

        stepsFragment = new StepsFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("position", position);
        Log.v("my_tag", "inside StepsPagerAdapter ItemDetailsFragment size is: " + steps.size());
        bundle.putParcelableArrayList("mySteps", steps);
        stepsFragment.setArguments(bundle);


        return stepsFragment;
    }

    /* get total fragment count for the activity */
    @Override
    public int getCount() {
        Log.v("my_tag", "inside StepsPagerAdapter getCount size is: " + PAGE_COUNT);

        return PAGE_COUNT;
    }
}