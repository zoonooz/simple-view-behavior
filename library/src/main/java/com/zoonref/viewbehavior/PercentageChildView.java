package com.zoonref.viewbehavior;

/**
 * Created by zoonooz on 15/1/2018 AD.
 * Interface for child view to let behavior send the progress
 */

public interface PercentageChildView {

    /**
     * This will be called on behavior updated.
     * @param behavior Changing behavior object
     * @param progress 0.0 to 1.0 progress of behavior animation
     */
    void onPercentageBehaviorChange(PercentageViewBehavior behavior, float progress);
}
