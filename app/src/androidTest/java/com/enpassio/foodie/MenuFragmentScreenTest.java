package com.enpassio.foodie;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.enpassio.foodie.activities.MainActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.enpassio.foodie.R.id.textMenuItem;

/**
 * Created by ABHISHEK RAJ on 8/17/2017.
 */


@RunWith(AndroidJUnit4.class)
public class MenuFragmentScreenTest {


    public static final String RECIPE_NAME = "Nutella Pie";


    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);


    // Convenience helper
    public static RecyclerViewMatcher withRecyclerView(final int recyclerViewId) {
        return new RecyclerViewMatcher(recyclerViewId);
    }

    //referenced from the link: https://stackoverflow.com/a/31475962/5770629
    @Test
    public void clickGridViewItem_OpensOrderActivity() {
        // Check item at position 3 has "Some content"
        onView(withRecyclerView(R.id.menuListRecyclerView).atPositionOnView(0, textMenuItem))
                .check(matches(withText(RECIPE_NAME)));
    }


}
