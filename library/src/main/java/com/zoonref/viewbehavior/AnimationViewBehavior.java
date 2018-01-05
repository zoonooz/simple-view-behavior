package com.zoonref.viewbehavior;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Transformation;

/**
 * Created by zoonooz on 5/1/2018 AD.
 * Behavior use with animation XML
 */
public class AnimationViewBehavior extends PercentageViewBehavior<View> {

    private int mAnimationId;
    private Animation mAnimation;
    private BehaviorAnimation mTransformAnimation;

    public AnimationViewBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.EasyCoordinatorView);
        mAnimationId = a.getResourceId(R.styleable.EasyCoordinatorView_animation, 0);
        a.recycle();
    }

    @Override
    void prepare(CoordinatorLayout parent, View child, View dependency) {
        super.prepare(parent, child, dependency);

        // if there is animation id, load it and initialize
        if (mAnimationId != 0) {
            mAnimation = AnimationUtils.loadAnimation(child.getContext(), mAnimationId);
            mAnimation.initialize(child.getWidth(), child.getHeight(), parent.getWidth(), parent.getHeight());
            mAnimation.setStartTime(0);
        }
    }

    @Override
    void updateViewWithPercent(View child, float percent) {
        if (mAnimation != null) {
            // get the transform at the specify time in progress
            Transformation transformation = new Transformation();
            mAnimation.getTransformation((long) (percent * mAnimation.getDuration()), transformation);

            // start new animation from transformation we got
            if (mTransformAnimation == null) {
                mTransformAnimation = new BehaviorAnimation();
            }
            mTransformAnimation.setTransformation(transformation);
            child.setAnimation(mTransformAnimation);
        }
    }

    /**
     * Custom animation class to apply Transformation to the view
     */
    private static class BehaviorAnimation extends Animation {

        private Transformation mTransformation;

        BehaviorAnimation() {
            // always set duration to zero and fill after
            setDuration(0);
            setFillAfter(true);
        }

        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            if (mTransformation != null) {
                t.compose(mTransformation);
            }
            super.applyTransformation(interpolatedTime, t);
        }

        void setTransformation(Transformation transformation) {
            this.mTransformation = transformation;
        }
    }
}
