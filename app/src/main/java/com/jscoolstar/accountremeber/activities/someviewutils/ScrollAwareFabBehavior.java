package com.jscoolstar.accountremeber.activities.someviewutils;

import android.content.Context;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.core.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by bshn on 2016/11/3.
 */

public class ScrollAwareFabBehavior extends FloatingActionButton.Behavior {

    public ScrollAwareFabBehavior(Context context, AttributeSet attrs){
        super();
    }

    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, FloatingActionButton child, View directTargetChild, View target, int nestedScrollAxes) {
        return nestedScrollAxes == ViewCompat.SCROLL_AXIS_VERTICAL;
    }

    @Override
    public void onNestedScroll(CoordinatorLayout coordinatorLayout, FloatingActionButton child, View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed);
//        Print.i("Behavior","Behavior","onNestedScroll  "+ dyConsumed);
        if ( child.getVisibility() == View.VISIBLE) {
//            Print.i("Behavior","Behavior","onNestedScroll1  ");
            child.hide();
        }
//        if (dyConsumed > 0 && child.getVisibility() == View.VISIBLE) {
//            Print.i("Behavior","Behavior","onNestedScroll1  ");
//            child.hide();
//        } else if (dyConsumed < 0 && child.getVisibility() != View.VISIBLE) {
//            Print.i("Behavior","Behavior","onNestedScroll2  ");
//            child.show();
//        }
    }

    @Override
    public void onStopNestedScroll(CoordinatorLayout coordinatorLayout, final FloatingActionButton child, View target) {
        super.onStopNestedScroll(coordinatorLayout, child, target);
        child.postDelayed(new Runnable() {
            @Override
            public void run() {
//                Print.i("Behavior","Behavior","onStopNestedScroll  "+child.getVisibility());
                if (child.getVisibility() == View.GONE) {
                    child.show();
                }
            }
        },1000);

    }
}
