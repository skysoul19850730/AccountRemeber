package com.jscoolstar.accountremeber;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.Window;

public class TestJava2Kotlin {

    public void test(Activity activity){
        if(Build.VERSION.SDK_INT >= 21) {
            Window window = activity.getWindow();
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
        int i = 0;
        if(i>0 || i<0){

        }
    }
}
