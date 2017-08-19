package com.enpassio.foodie;

import android.support.test.rule.ActivityTestRule;

import com.enpassio.foodie.activities.MainActivity;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static com.enpassio.foodie.R.id.menuListRecyclerView;

/**
 * Created by ABHISHEK RAJ on 8/17/2017.
 */

public class MenuClickTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);


//referenced from the link: https://spin.atomicobject.com/2016/04/15/espresso-testing-recyclerviews/


    // Convenience helper
    public static RecyclerViewMatcher withRecyclerView(final int recyclerViewId) {
        return new RecyclerViewMatcher(recyclerViewId);
    }


    //referenced from the link: https://stackoverflow.com/a/31475962/5770629
    @Test
    public void clickRecyclerViewItemStartsTheDetailsActivity() {
        onView(withRecyclerView(menuListRecyclerView).atPosition(0)).perform(click());
    }
}