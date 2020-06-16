package com.jscoolstar.accountremeber.utils;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.text.TextUtils;
import android.util.Log;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET;
import static android.content.Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED;

public class JavaUtils {
    public static long addressOf(Object o) {
        Object[] array = new Object[]{o};

        long objectAddress = -1;
        try {
            Class cls = Class.forName("sun.misc.Unsafe");
            Field field = cls.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            Object unsafe = field.get(null);
            Class unsafeCls = unsafe.getClass();
            Method arrayBaseOffset = unsafeCls.getMethod("arrayBaseOffset", Object.class.getClass());
            int baseOffset = (int) arrayBaseOffset.invoke(unsafe, Object[].class);
            Method size = unsafeCls.getMethod("addressSize");
            int addressSize = (int) size.invoke(unsafe);
            switch (addressSize) {
                case 4:
                    Method getInt = unsafeCls.getMethod("getInt", Object.class, long.class);
                    objectAddress = (int) getInt.invoke(unsafe, array, baseOffset);
                    break;
                case 8:
                    Method getLong = unsafeCls.getMethod("getLong", Object.class, long.class);
                    objectAddress = (long) getLong.invoke(unsafe, array, baseOffset);
                    break;
                default:
                    throw new Error("unsupported address size: " + addressSize);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return objectAddress;
    }



    public static Intent getAppOpenIntentByPackageName(Context context, String packageName) {
        String mainAct = null;
        PackageManager pkgMag = context.getPackageManager();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        intent.setFlags(FLAG_ACTIVITY_RESET_TASK_IF_NEEDED | Intent.FLAG_ACTIVITY_NEW_TASK);

        @SuppressLint("WrongConstant") List<ResolveInfo> list = pkgMag.queryIntentActivities(intent,
                PackageManager.GET_ACTIVITIES);
        for (int i = 0; i < list.size(); i++) {
            ResolveInfo info = list.get(i);
            if (info.activityInfo.packageName.equals(packageName)) {
                mainAct = info.activityInfo.name;
                break;
            }
        }
        if (TextUtils.isEmpty(mainAct)) {
            return null;
        }
//        Intent intent1 = pkgMag.getLaunchIntentForPackage(packageName);
        Intent intent1= new Intent();
        intent1.setFlags( FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
//        intent1.setFlags(Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
//        intent1.addFlags(Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
        intent1.setComponent(new ComponentName(packageName, mainAct+"2"));

       ResolveInfo info = pkgMag.resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY | 0);
        Log.d("sqc", "getAppOpenIntentByPackageName: "+info.activityInfo.packageName);
        return intent1;

    }
}