package com.zoonref.viewbehavior;

import android.support.design.widget.CoordinatorLayout;
import android.view.animation.ScaleAnimation;

import com.zoonref.viewbehavior.shadow.ShadowAnimation;

import static junit.framework.Assert.*;

import org.junit.Test;
import org.robolectric.annotation.Config;
import org.robolectric.shadow.api.Shadow;

/**
 * Created by zoonooz on 6/1/2018 AD.
 * Test {@link AnimationViewBehavior}
 */
@Config(shadows = {ShadowAnimation.class})
public class AnimationViewBehaviorTest extends BehaviorTest {

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
