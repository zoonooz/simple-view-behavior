package com.zoonref.viewbehavior;

import android.app.Activity;
import android.support.design.widget.CoordinatorLayout;
import android.view.View;
import android.view.animation.ScaleAnimation;

import com.zoonref.viewbehavior.shadow.ShadowAnimation;

import static junit.framework.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadow.api.Shadow;

/**
 * Created by zoonooz on 6/1/2018 AD.
 * Test {@link AnimationViewBehavior}
 */
@RunWith(RobolectricTestRunner.class)
@Config(shadows = {ShadowAnimation.class})
public class AnimationViewBehaviorTest {

    private CoordinatorLayout coordinatorLayout;
    private View firstView;
    private View secondView;

    @Before
    public void setup() {
        Activity activity = Robolectric.setupActivity(Activity.class);
        activity.setTheme(R.style.Theme_AppCompat);
        coordinatorLayout = new CoordinatorLayout(activity);
        activity.setContentView(coordinatorLayout);
        firstView = new View(activity);
        secondView = new View(activity);

        CoordinatorLayout.LayoutParams params = new CoordinatorLayout.LayoutParams(320, 200);
        coordinatorLayout.addView(firstView, params);
        coordinatorLayout.addView(secondView, params);
    }

    @Test
    public void animations() {
        ScaleAnimation animation = new ScaleAnimation(1f, 0f, 1f, 0f);
        AnimationViewBehavior behavior = new AnimationViewBehavior.Builder()
                .dependsOn(firstView.getId(), SimpleViewBehavior.DEPEND_TYPE_Y)
                .targetValue(100)
                .animation(animation)
                .build();

        CoordinatorLayout.LayoutParams params = new CoordinatorLayout.LayoutParams(320, 200);
        params.setBehavior(behavior);
        secondView.setLayoutParams(params);

        ShadowAnimation shadowAnimation = Shadow.extract(animation);

        firstView.setY(50);
        coordinatorLayout.requestLayout();
        assertEquals(500L, shadowAnimation.getLastTimeGetTransform());

        firstView.setY(100);
        coordinatorLayout.requestLayout();
        assertEquals(1000L, shadowAnimation.getLastTimeGetTransform());
    }
}
