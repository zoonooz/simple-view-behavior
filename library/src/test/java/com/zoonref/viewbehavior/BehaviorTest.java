package com.zoonref.viewbehavior;

import android.app.Activity;
import android.support.design.widget.CoordinatorLayout;
import android.view.View;

import com.zoonref.viewbehavior.view.TestPercentageChildView;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

/**
 * Created by zoonooz on 15/1/2018 AD.
 * Best test class
 */
@RunWith(RobolectricTestRunner.class)
public abstract class BehaviorTest {

    CoordinatorLayout coordinatorLayout;
    View firstView;
    TestPercentageChildView secondView;

    @Before
    public void setup() {
        Activity activity = Robolectric.setupActivity(Activity.class);
        activity.setTheme(R.style.Theme_AppCompat);
        coordinatorLayout = new CoordinatorLayout(activity);
        activity.setContentView(coordinatorLayout);
        firstView = new View(activity);
        secondView = new TestPercentageChildView(activity);

        CoordinatorLayout.LayoutParams params = new CoordinatorLayout.LayoutParams(320, 200);
        coordinatorLayout.addView(firstView, params);
        coordinatorLayout.addView(secondView, params);
    }
}
