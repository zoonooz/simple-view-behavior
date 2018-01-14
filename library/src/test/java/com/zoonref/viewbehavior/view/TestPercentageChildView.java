package com.zoonref.viewbehavior.view;

import android.content.Context;
import android.view.View;

import com.zoonref.viewbehavior.PercentageChildView;
import com.zoonref.viewbehavior.PercentageViewBehavior;

/**
 * Created by zoonooz on 15/1/2018 AD.
 * {@link PercentageChildView} implementation for testing
 */

public class TestPercentageChildView extends View implements PercentageChildView {

    private float currentPercent;

    public TestPercentageChildView(Context context) {
        super(context);
    }

    @Override
    public void onPercentageBehaviorChange(PercentageViewBehavior behavior, float progress) {
        currentPercent = progress;
    }

    public float getCurrentPercent() {
        return currentPercent;
    }
}
