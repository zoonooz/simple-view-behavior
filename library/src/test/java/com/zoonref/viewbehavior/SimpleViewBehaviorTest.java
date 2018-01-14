package com.zoonref.viewbehavior;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.CoordinatorLayout;

import static junit.framework.Assert.*;

import org.junit.Test;

/**
 * Created by zoonooz on 5/1/2018 AD.
 * Test {@link SimpleViewBehavior}
 */
public class SimpleViewBehaviorTest extends BehaviorTest {

    @Test
    public void dependsOnX_targetXAndY() {
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

        // target X is 100, 50% = 50
        assertEquals(50f, secondView.getX());
        // target Y is 200, 50% = 100
        assertEquals(100f, secondView.getY());
    }

    @Test
    public void dependsOnY_targetXAndY() {
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

        // target X is 100, 50% = 50
        assertEquals(50f, secondView.getX());
        // target Y is 200, 50% = 100
        assertEquals(100f, secondView.getY());
    }

    @Test
    public void dependsOnWidth_targetXAndY() {
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

        // target X is 100
        assertEquals(100f, secondView.getX());
        // target Y is 200
        assertEquals(200f, secondView.getY());
    }

    @Test
    public void dependsOnHeight_targetXAndY() {
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

        // target X is 100
        assertEquals(100f, secondView.getX());
        // target Y is 200
        assertEquals(200f, secondView.getY());
    }

    @Test
    public void targetWidthAndHeight() {
        SimpleViewBehavior behavior = new SimpleViewBehavior.Builder()
                .dependsOn(firstView.getId(), SimpleViewBehavior.DEPEND_TYPE_Y)
                .targetValue(100)
                .targetWidth(160)
                .targetHeight(100)
                .build();
        CoordinatorLayout.LayoutParams params = new CoordinatorLayout.LayoutParams(320, 200);
        params.setBehavior(behavior);
        secondView.setLayoutParams(params);

        // move first view by 100%
        firstView.setY(100);
        coordinatorLayout.requestLayout();

        assertEquals(160f, secondView.getScaleX() * 320);
        assertEquals(100f, secondView.getScaleY() * 200);
    }

    @Test
    public void targetAlpha() {
        SimpleViewBehavior behavior = new SimpleViewBehavior.Builder()
                .dependsOn(firstView.getId(), SimpleViewBehavior.DEPEND_TYPE_Y)
                .targetValue(100)
                .targetAlpha(0)
                .build();
        CoordinatorLayout.LayoutParams params = new CoordinatorLayout.LayoutParams(320, 200);
        params.setBehavior(behavior);
        secondView.setLayoutParams(params);

        // move first view by 100%
        firstView.setY(100);
        coordinatorLayout.requestLayout();

        assertEquals(0f, secondView.getAlpha());
    }

    @Test
    public void targetBackgroundColor() {
        secondView.setBackgroundColor(Color.parseColor("#FFFFFF"));
        SimpleViewBehavior behavior = new SimpleViewBehavior.Builder()
                .dependsOn(firstView.getId(), SimpleViewBehavior.DEPEND_TYPE_Y)
                .targetValue(100)
                .targetBackgroundColor(Color.parseColor("#000000"))
                .build();
        CoordinatorLayout.LayoutParams params = new CoordinatorLayout.LayoutParams(320, 200);
        params.setBehavior(behavior);
        secondView.setLayoutParams(params);

        firstView.setY(100);
        coordinatorLayout.requestLayout();
        assertEquals(Color.parseColor("#000000"), ((ColorDrawable) secondView.getBackground()).getColor());
    }

    @Test
    public void targetRotateXAndY() {
        SimpleViewBehavior behavior = new SimpleViewBehavior.Builder()
                .dependsOn(firstView.getId(), SimpleViewBehavior.DEPEND_TYPE_Y)
                .targetValue(100)
                .targetRotateX(90)
                .targetRotateY(45)
                .build();
        CoordinatorLayout.LayoutParams params = new CoordinatorLayout.LayoutParams(320, 200);
        params.setBehavior(behavior);
        secondView.setLayoutParams(params);

        firstView.setY(100);
        coordinatorLayout.requestLayout();
        assertEquals(90f, secondView.getRotationX());
        assertEquals(45f, secondView.getRotationY());
    }
}
