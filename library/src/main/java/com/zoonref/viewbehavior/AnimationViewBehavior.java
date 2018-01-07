package com.zoonref.viewbehavior;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.AnimRes;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Transformation;

/**
 * Created by zoonooz on 5/1/2018 AD.
 * Behavior use with {@link Animation}
 */
public class AnimationViewBehavior extends PercentageViewBehavior<View> {

    private Animation mAnimation;
    private BehaviorAnimation mTransformAnimation;

    public AnimationViewBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ViewBehavior);
        int animationId = a.getResourceId(R.styleable.ViewBehavior_behavior_animation, 0);
        a.recycle();

        if (animationId != 0) {
            mAnimation = AnimationUtils.loadAnimation(context, animationId);
        }
    }

    private AnimationViewBehavior(Builder builder) {
        super(builder);
        mAnimation = builder.animation;
    }

    @Override
    void prepare(CoordinatorLayout parent, View child, View dependency) {
        super.prepare(parent, child, dependency);

        if (mAnimation != null) {
            mAnimation.initialize(child.getWidth(), child.getHeight(), parent.getWidth(), parent.getHeight());
            mAnimation.setStartTime(0);
            mAnimation.setDuration(1000);
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

    /**
     * Builder
     */
    public static class Builder extends PercentageViewBehavior.Builder<Builder> {

        private Animation animation;

        @Override
        Builder getThis() {
            return this;
        }

        public Builder animation(Animation animation) {
            this.animation = animation;
            return this;
        }

        public Builder animationId(Context context, @AnimRes int animationId) {
            if (animationId != 0) {
                this.animation = AnimationUtils.loadAnimation(context, animationId);
            }
            return this;
        }

        public AnimationViewBehavior build() {
            return new AnimationViewBehavior(this);
        }
    }
}
