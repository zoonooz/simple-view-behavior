package com.zoonref.viewbehavior;

import android.animation.ArgbEvaluator;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Transformation;

/**
 * Created by zoonooz on 12/30/15 AD.
 * Easy CoordinatorLayout Behavior
 */
public class SimpleViewBehavior extends CoordinatorLayout.Behavior<View> {

    private static final int UNSPECIFIED_INT = Integer.MAX_VALUE;
    private static final float UNSPECIFIED_FLOAT = Float.MAX_VALUE;

    /**
     * Depend on the dependency view height
     */
    private static final int DEPEND_TYPE_HEIGHT = 0;

    /**
     * Depend on the dependency view width
     */
    private static final int DEPEND_TYPE_WIDTH = 1;

    /**
     * Depend on the dependency view x position
     */
    private static final int DEPEND_TYPE_X = 2;

    /**
     * Depend on the dependency view y position
     */
    private static final int DEPEND_TYPE_Y = 3;

    private int mDependType;
    private int mDependViewId;

    private int mDependTargetX;
    private int mDependTargetY;
    private int mDependTargetWidth;
    private int mDependTargetHeight;

    private int mDependStartX;
    private int mDependStartY;
    private int mDependStartWidth;
    private int mDependStartHeight;

    private int mStartX;
    private int mStartY;
    private int mStartWidth;
    private int mStartHeight;
    private int mStartBackgroundColor;
    private float mStartAlpha;
    private float mStartRotateX;
    private float mStartRotateY;

    public int targetX;
    public int targetY;
    public int targetWidth;
    public int targetHeight;
    public int targetBackgroundColor;
    public float targetAlpha;
    public float targetRotateX;
    public float targetRotateY;

    private int mAnimationId;
    private Animation mAnimation;

    /**
     * Is the values prepared to be use
     */
    private boolean isPrepared;

    /**
     * Creates a new behavior whose parameters come from the specified context and
     * attributes set.
     *
     * @param context the application environment
     * @param attrs   the set of attributes holding the target and animation parameters
     */
    public SimpleViewBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);

        // setting values
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.EasyCoordinatorView);
        mDependViewId = a.getResourceId(R.styleable.EasyCoordinatorView_dependsOn, 0);
        mDependType = a.getInt(R.styleable.EasyCoordinatorView_dependType, DEPEND_TYPE_WIDTH);
        mDependTargetX = a.getDimensionPixelOffset(R.styleable.EasyCoordinatorView_dependTargetX, UNSPECIFIED_INT);
        mDependTargetY = a.getDimensionPixelOffset(R.styleable.EasyCoordinatorView_dependTargetY, UNSPECIFIED_INT);
        mDependTargetWidth = a.getDimensionPixelOffset(R.styleable.EasyCoordinatorView_dependTargetWidth, UNSPECIFIED_INT);
        mDependTargetHeight = a.getDimensionPixelOffset(R.styleable.EasyCoordinatorView_dependTargetHeight, UNSPECIFIED_INT);
        targetX = a.getDimensionPixelOffset(R.styleable.EasyCoordinatorView_targetX, UNSPECIFIED_INT);
        targetY = a.getDimensionPixelOffset(R.styleable.EasyCoordinatorView_targetY, UNSPECIFIED_INT);
        targetWidth = a.getDimensionPixelOffset(R.styleable.EasyCoordinatorView_targetWidth, UNSPECIFIED_INT);
        targetHeight = a.getDimensionPixelOffset(R.styleable.EasyCoordinatorView_targetHeight, UNSPECIFIED_INT);
        targetBackgroundColor = a.getColor(R.styleable.EasyCoordinatorView_targetBackgroundColor, UNSPECIFIED_INT);
        targetAlpha = a.getFloat(R.styleable.EasyCoordinatorView_targetAlpha, UNSPECIFIED_FLOAT);
        targetRotateX = a.getFloat(R.styleable.EasyCoordinatorView_targetRotateX, UNSPECIFIED_FLOAT);
        targetRotateY = a.getFloat(R.styleable.EasyCoordinatorView_targetRotateY, UNSPECIFIED_FLOAT);
        mAnimationId = a.getResourceId(R.styleable.EasyCoordinatorView_animation, 0);
        a.recycle();
    }

    /**
     * Call this before making any changes to the view to setup values
     *
     * @param parent     coordinator layout as parent
     * @param child      view that will move when dependency changed
     * @param dependency dependency view
     */
    void prepare(CoordinatorLayout parent, View child, View dependency) {
        mDependStartX = (int) dependency.getX();
        mDependStartY = (int) dependency.getY();
        mDependStartWidth = dependency.getWidth();
        mDependStartHeight = dependency.getHeight();
        mStartX = (int) child.getX();
        mStartY = (int) child.getY();
        mStartWidth = child.getWidth();
        mStartHeight = child.getHeight();
        mStartAlpha = child.getAlpha();
        mStartRotateX = child.getRotationX();
        mStartRotateY = child.getRotationY();

        // only set the start background color when the background is color drawable
        Drawable background = child.getBackground();
        if (background instanceof ColorDrawable) {
            mStartBackgroundColor = ((ColorDrawable) background).getColor();
        }

        // if there is animation id, load it and initialize
        if (mAnimationId != 0) {
            mAnimation = AnimationUtils.loadAnimation(child.getContext(), mAnimationId);
            mAnimation.initialize(mStartWidth, mStartHeight, parent.getWidth(), parent.getHeight());
        }

        // if parent fitsSystemWindows is true, add status bar height to target y if specified
        if (Build.VERSION.SDK_INT > 16 && parent.getFitsSystemWindows() && targetY != UNSPECIFIED_INT) {
            int result = 0;
            Resources resources = parent.getContext().getResources();
            int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
            if (resourceId > 0) {
                result = resources.getDimensionPixelSize(resourceId);
            }
            targetY += result;
        }

        isPrepared = true;
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        // depend on the view that has the same id
        return dependency.getId() == mDependViewId;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {
        // first time, prepare values before continue
        if (!isPrepared) {
            prepare(parent, child, dependency);
        }
        updateView(child, dependency);
        return false;
    }

    @Override
    public boolean onLayoutChild(CoordinatorLayout parent, View child, int layoutDirection) {
        boolean bool = super.onLayoutChild(parent, child, layoutDirection);
        if (isPrepared) {
            updateView(child, parent.getDependencies(child).get(0));
        }
        return bool;
    }

    /**
     * Update the child view from the dependency states
     *
     * @param child      child view
     * @param dependency dependency view
     */
    void updateView(View child, View dependency) {
        float percent = 0;
        float start = 0;
        float current = 0;
        float end = UNSPECIFIED_INT;
        switch (mDependType) {
            case DEPEND_TYPE_WIDTH:
                start = mDependStartWidth;
                current = dependency.getWidth();
                end = mDependTargetWidth;
                break;
            case DEPEND_TYPE_HEIGHT:
                start = mDependStartHeight;
                current = dependency.getHeight();
                end = mDependTargetHeight;
                break;
            case DEPEND_TYPE_X:
                start = mDependStartX;
                current = dependency.getX();
                end = mDependTargetX;
                break;
            case DEPEND_TYPE_Y:
                start = mDependStartY;
                current = dependency.getY();
                end = mDependTargetY;
                break;
        }

        // need to define target value according to the depend type, if not then skip
        if (end != UNSPECIFIED_INT) {
            percent = Math.abs(current - start) / Math.abs(end - start);
        }
        updateViewWithPercent(child, percent > 1 ? 1 : percent);
    }

    /**
     * Update the child view from the progress of changed dependency view
     *
     * @param child   child view
     * @param percent progress of dependency changed 0.0f to 1.0f
     */
    void updateViewWithPercent(View child, float percent) {
        // if there is no animation set, use the attr options
        if (mAnimation == null) {
            float newX = targetX == UNSPECIFIED_INT ? 0 : (targetX - mStartX) * percent;
            float newY = targetY == UNSPECIFIED_INT ? 0 : (targetY - mStartY) * percent;

            // set scale
            if (targetWidth != UNSPECIFIED_INT || targetHeight != UNSPECIFIED_INT) {
                float newWidth = mStartWidth + ((targetWidth - mStartWidth) * percent);
                float newHeight = mStartHeight + ((targetHeight - mStartHeight) * percent);

                child.setScaleX(newWidth / mStartWidth);
                child.setScaleY(newHeight / mStartHeight);
                // make up position for scale change
                newX -= (mStartWidth - newWidth) / 2;
                newY -= (mStartHeight - newHeight) / 2;
            }

            // set new position
            child.setTranslationX(newX);
            child.setTranslationY(newY);

            // set alpha
            if (targetAlpha != UNSPECIFIED_FLOAT) {
                child.setAlpha(mStartAlpha + (targetAlpha - mStartAlpha) * percent);
            }

            // set background color
            if (targetBackgroundColor != UNSPECIFIED_INT && mStartBackgroundColor != 0) {
                ArgbEvaluator evaluator = new ArgbEvaluator();
                int color = (int) evaluator.evaluate(percent, mStartBackgroundColor, targetBackgroundColor);
                child.setBackgroundColor(color);
            }

            // set rotation
            if (targetRotateX != UNSPECIFIED_FLOAT) {
                child.setRotationX(mStartRotateX + (targetRotateX - mStartRotateX) * percent);
            }
            if (targetRotateY != UNSPECIFIED_FLOAT) {
                child.setRotationX(mStartRotateY + (targetRotateY - mStartRotateY) * percent);
            }
        } else {
            // get the transform at the specify time in progress
            mAnimation.setStartTime(0);
            mAnimation.restrictDuration(100);
            Transformation transformation = new Transformation();
            mAnimation.getTransformation((long) (percent * 100), transformation);
            // start new animation from transformation we got
            BehaviorAnimation animation = new BehaviorAnimation(transformation);
            child.startAnimation(animation);
        }

        child.requestLayout();
    }

    /**
     * Custom animation class to apply Transformation to the view
     */
    private static class BehaviorAnimation extends Animation {

        private Transformation mTransformation;

        BehaviorAnimation(Transformation transformation) {
            mTransformation = transformation;
            // always set duration to zero and fill after
            setDuration(0);
            setFillAfter(true);
        }

        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            t.compose(mTransformation);
            super.applyTransformation(interpolatedTime, t);
        }
    }
}
