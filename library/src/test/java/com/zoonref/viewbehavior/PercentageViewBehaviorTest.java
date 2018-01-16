package com.zoonref.viewbehavior;

import android.support.design.widget.CoordinatorLayout;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * Created by zoonooz on 15/1/2018 AD.
 * Test {@link PercentageViewBehavior}
 */
public class PercentageViewBehaviorTest extends BehaviorTest {

    @Test
    public void dependsOnX() {
        SimpleViewBehavior behavior = new SimpleViewBehavior.Builder()
                .dependsOn(firstView.getId(), SimpleViewBehavior.DEPEND_TYPE_X)
                .targetValue(100)
                .targetX(100)
                .targetY(200)
                .build();
        CoordinatorLayout.LayoutParams params = new CoordinatorLayout.LayoutParams(320, 200);
        params.setBehavior(behavior);
        secondView.setLayoutParams(params);

        // move first view by 50%
        firstView.setX(50);
        coordinatorLayout.requestLayout();
        assertEquals(0.5f, secondView.getCurrentPercent());
    }

    @Test
    public void dependsOnY() {
        SimpleViewBehavior behavior = new SimpleViewBehavior.Builder()
                .dependsOn(firstView.getId(), SimpleViewBehavior.DEPEND_TYPE_Y)
                .targetValue(100)
                .targetX(100)
                .targetY(200)
                .build();
        CoordinatorLayout.LayoutParams params = new CoordinatorLayout.LayoutParams(320, 200);
        params.setBehavior(behavior);
        secondView.setLayoutParams(params);

        // move first view by 50%
        firstView.setY(50);
        coordinatorLayout.requestLayout();
        assertEquals(0.5f, secondView.getCurrentPercent());
    }

    @Test
    public void dependsOnWidth() {
        SimpleViewBehavior behavior = new SimpleViewBehavior.Builder()
                .dependsOn(firstView.getId(), SimpleViewBehavior.DEPEND_TYPE_WIDTH)
                .targetValue(160)
                .targetX(100)
                .targetY(200)
                .build();
        CoordinatorLayout.LayoutParams params = new CoordinatorLayout.LayoutParams(320, 200);
        params.setBehavior(behavior);
        secondView.setLayoutParams(params);

        // resize first view's width
        CoordinatorLayout.LayoutParams resizeParams = new CoordinatorLayout.LayoutParams(firstView.getLayoutParams());
        resizeParams.width = 160;
        firstView.setLayoutParams(resizeParams);
        coordinatorLayout.requestLayout();
        assertEquals(1.0f, secondView.getCurrentPercent());
    }

    @Test
    public void dependsOnHeight() {
        SimpleViewBehavior behavior = new SimpleViewBehavior.Builder()
                .dependsOn(firstView.getId(), SimpleViewBehavior.DEPEND_TYPE_HEIGHT)
                .targetValue(100)
                .targetX(100)
                .targetY(200)
                .build();
        CoordinatorLayout.LayoutParams params = new CoordinatorLayout.LayoutParams(320, 200);
        params.setBehavior(behavior);
        secondView.setLayoutParams(params);

        // resize first view's height
        CoordinatorLayout.LayoutParams resizeParams = new CoordinatorLayout.LayoutParams(firstView.getLayoutParams());
        resizeParams.height = 100;
        firstView.setLayoutParams(resizeParams);
        coordinatorLayout.requestLayout();
        assertEquals(1.0f, secondView.getCurrentPercent());
    }
}
