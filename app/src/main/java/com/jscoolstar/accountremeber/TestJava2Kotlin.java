package com.jscoolstar.accountremeber;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.Window;

import com.jscoolstar.accountremeber.utils.Util;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class TestJava2Kotlin {

//    public void test(Activity activity){
//        if(Build.VERSION.SDK_INT >= 21) {
//            Window window = activity.getWindow();
//            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
//            window.setStatusBarColor(Color.TRANSPARENT);
//        }
//        int i = 0;
//        int[] a = new int[]{};
//        if(i>0 || i<0){
//
//        }
//    }

//    private static volatile   TestJava2Kotlin instance;

    void testBlock(){
        new Util().testBlock(new Function1<Boolean, Unit>() {
            @Override
            public Unit invoke(Boolean aBoolean) {
                return null;
            }
        });
    }
}
