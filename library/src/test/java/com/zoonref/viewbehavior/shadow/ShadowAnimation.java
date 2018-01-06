package com.zoonref.viewbehavior.shadow;

import android.view.animation.Animation;
import android.view.animation.Transformation;

import org.robolectric.annotation.Implementation;
import org.robolectric.annotation.Implements;
import org.robolectric.annotation.RealObject;

/**
 * Created by zoonooz on 6/1/2018 AD.
 * Custom Robolectric shadow for Animation
 */
@Implements(Animation.class)
public class ShadowAnimation {

    @RealObject
    private Animation realAnimation;

    // save the time of the last getTransformation() for test
    private long lastTimeGetTransform;

    @Implementation
    public boolean getTransformation(long currentTime, Transformation outTransformation) {
        lastTimeGetTransform = currentTime;
        return false;
    }

    public long getLastTimeGetTransform() {
        return lastTimeGetTransform;
    }
}
