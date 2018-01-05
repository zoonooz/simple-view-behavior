package com.zoonref.viewbehavior;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by zoonooz on 5/1/2018 AD.
 * Base behavior for percentage driven behaviors.
 */
public abstract class PercentageViewBehavior<V extends View> extends CoordinatorLayout.Behavior<V> {

    static final int UNSPECIFIED_INT = Integer.MAX_VALUE;
    static final float UNSPECIFIED_FLOAT = Float.MAX_VALUE;

    /**
     * Depend on the dependency view height
     */
    public static final int DEPEND_TYPE_HEIGHT = 0;

    /**
     * Depend on the dependency view width
     */
    public static final int DEPEND_TYPE_WIDTH = 1;

    /**
     * Depend on the dependency view x position
     */
    public static final int DEPEND_TYPE_X = 2;

    /**
     * Depend on the dependency view y position
     */
    public static final int DEPEND_TYPE_Y = 3;

    private int mDependType;
    private int mDependViewId;

    private int mDependTarget;

    private int mDependStartX;
    private int mDependStartY;
    private int mDependStartWidth;
    private int mDependStartHeight;

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
    PercentageViewBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);

        // setting values
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.EasyCoordinatorView);
        mDependViewId = a.getResourceId(R.styleable.EasyCoordinatorView_dependsOn, 0);
        mDependType = a.getInt(R.styleable.EasyCoordinatorView_dependType, DEPEND_TYPE_WIDTH);
        mDependTarget = a.getDimensionPixelOffset(R.styleable.EasyCoordinatorView_dependTarget, UNSPECIFIED_INT);
        a.recycle();
    }

    PercentageViewBehavior(@NonNull Builder builder) {
        mDependViewId = builder.dependsOn;
        mDependType = builder.dependsType;
        mDependTarget = builder.targetValue;
    }

    /**
     * Call this before making any changes to the view to setup values
     *
     * @param parent     coordinator layout as parent
     * @param child      view that will move when dependency changed
     * @param dependency dependency view
     */
    void prepare(CoordinatorLayout parent, V child, View dependency) {
        mDependStartX = (int) dependency.getX();
        mDependStartY = (int) dependency.getY();
        mDependStartWidth = dependency.getWidth();
        mDependStartHeight = dependency.getHeight();

        isPrepared = true;
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, V child, View dependency) {
        // depend on the view that has the same id
        return dependency.getId() == mDependViewId;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, V child, View dependency) {
        // first time, prepare values before continue
        if (!isPrepared) {
            prepare(parent, child, dependency);
        }
        updateView(child, dependency);
        return false;
    }

    @Override
    public boolean onLayoutChild(CoordinatorLayout parent, V child, int layoutDirection) {
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
    void updateView(V child, View dependency) {
        float percent = 0;
        float start = 0;
        float current = 0;
        float end = UNSPECIFIED_INT;
        switch (mDependType) {
            case DEPEND_TYPE_WIDTH:
                start = mDependStartWidth;
                current = dependency.getWidth();
                end = mDependTarget;
                break;
            case DEPEND_TYPE_HEIGHT:
                start = mDependStartHeight;
                current = dependency.getHeight();
                end = mDependTarget;
                break;
            case DEPEND_TYPE_X:
                start = mDependStartX;
                current = dependency.getX();
                end = mDependTarget;
                break;
            case DEPEND_TYPE_Y:
                start = mDependStartY;
                current = dependency.getY();
                end = mDependTarget;
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
    abstract void updateViewWithPercent(V child, float percent);

    /**
     * Builder class
     */
    static abstract class Builder<T extends Builder> {

        private int dependsOn;
        private int dependsType = UNSPECIFIED_INT;
        private int targetValue = UNSPECIFIED_INT;

        abstract T getThis();

        T dependsOn(@IdRes int dependsOn, int dependsType) {
            this.dependsOn = dependsOn;
            this.dependsType = dependsType;
            return getThis();
        }

        T targetValue(int targetValue) {
            this.targetValue = targetValue;
            return getThis();
        }
    }
}
