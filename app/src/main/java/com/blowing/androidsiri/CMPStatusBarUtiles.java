package com.blowing.androidsiri;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by wangxk on 2017-11-29.
 */

public class CMPStatusBarUtiles {

    /**
     * CMP 设置状态栏颜色的公共方法
     *
     * @param activity
     * @param colorPref
     */
    public static void setStatusBarBackgroundColor(Activity activity, final String colorPref) {
        if (colorPref != null && !colorPref.isEmpty()) {
            psetStatusBarBackgroundColor(activity, Color.parseColor(colorPref), false);
        }
    }

    /**
     * CMP 设置状态栏颜色的公共方法
     *
     * @param activity
     * @param colorPref
     */
    public static void setStatusBarBackgroundColor(Activity activity, final String colorPref, boolean isLight) throws Exception {
        if (colorPref != null && !colorPref.isEmpty()) {
            try {
                psetStatusBarBackgroundColor(activity, Color.parseColor(colorPref), isLight);
            } catch (Exception e) {
                throw e;
            }
        }
    }


    /**
     * CMP 设置状态栏颜色的公共方法
     *
     * @param activity
     * @param colorResId 颜色值
     */
    public static void setStatusBarBackgroundColor(Activity activity, @ColorRes final int colorResId, boolean isLight) {
        psetStatusBarBackgroundColor(activity, activity.getResources().getColor(colorResId), isLight);
    }

    /**
     * CMP 设置状态栏颜色的公共方法
     *
     * @param activity
     * @param colorResId 颜色值
     */
    public static void setStatusBarBackgroundColor(Activity activity, final int colorResId) {
        psetStatusBarBackgroundColor(activity, activity.getResources().getColor(colorResId), false);
    }

    /**
     * CMP 设置状态栏颜色的公共方法
     *
     * @param activity
     * @param colorPref 颜色值
     */
    public static void psetStatusBarBackgroundColor(Activity activity, @ColorInt final int colorPref, boolean isLight) {
        if (Build.VERSION.SDK_INT >= 23) {
            final Window window = activity.getWindow();
            // Method and constants not available on all SDKs but we want to be able to compile this code with any SDK
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS); // SDK 19: WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);// SDK 21:
            int visibility = window.getDecorView().getSystemUiVisibility();
//            if (isLight) {
////                window.getDecorView().setSystemUiVisibility(visibility | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
////            } else {
////                window.getDecorView().setSystemUiVisibility(visibility ^ View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
////            }
            window.setStatusBarColor(colorPref);
        }
    }

    public static void setStatusBarAutoHide(Activity activity) {
        if (Build.VERSION.SDK_INT >= 19 ) {
            View decorView = activity.getWindow().getDecorView();
            int visibility = decorView.getSystemUiVisibility();
            activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
            decorView.setSystemUiVisibility(
                    visibility
                            | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION//隐藏nav栏
                            | View.SYSTEM_UI_FLAG_FULLSCREEN//隐藏状态栏
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
            );
        }
    }

    public static void cancelStatusBarAutoHide(Activity activity, boolean originFullScreen) {
        if (Build.VERSION.SDK_INT >= 19) {
            View decorView = activity.getWindow().getDecorView();
            int visibility = decorView.getSystemUiVisibility();
            if ((visibility & View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION) > 0)
                visibility = visibility ^ View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION;
            if ((visibility & View.SYSTEM_UI_FLAG_HIDE_NAVIGATION) > 0)
                visibility = visibility ^ View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
            if ((visibility & View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY) > 0)
                visibility = visibility ^ View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
            if ((visibility & View.SYSTEM_UI_FLAG_FULLSCREEN) > 0)
                visibility = visibility ^ View.SYSTEM_UI_FLAG_FULLSCREEN;
            if (!originFullScreen) {
                if ((visibility & View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN) > 0)
                    visibility = visibility ^ View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
                if ((visibility & View.SYSTEM_UI_FLAG_LAYOUT_STABLE) > 0)
                    visibility = visibility ^ View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            }
            decorView.setSystemUiVisibility(visibility);
        }
    }

    /**
     * CMP 设置状态栏颜色的公共方法
     *
     * @param dialog
     * @param colorResId 颜色值
     */
    public static void setStatusBarBackgroundColor(Dialog dialog, final int colorResId, boolean isLight) {
        psetStatusBarBackgroundColor(dialog, dialog.getContext().getResources().getColor(colorResId), isLight);
    }


    /**
     * CMP 设置状态栏颜色的公共方法
     *
     * @param dialog
     * @param colorPref 颜色值
     */
    public static void psetStatusBarBackgroundColor(Dialog dialog, @ColorInt final int colorPref, boolean isLight) {
        if (Build.VERSION.SDK_INT >= 23) {
            final Window window = dialog.getWindow();
            // Method and constants not available on all SDKs but we want to be able to compile this code with any SDK
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS); // SDK 19: WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);// SDK 21:
            int visibility = window.getDecorView().getSystemUiVisibility();
            if (isLight) {
                window.getDecorView().setSystemUiVisibility(visibility | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            } else {
                window.getDecorView().setSystemUiVisibility(visibility ^ View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            }
            window.setStatusBarColor(colorPref);
        }
    }

    /**
     * 设置全屏
     */
    public static void setFullSceen(Activity activity) {
        // SYSTEM_UI_FLAG_FULLSCREEN is available since JellyBean, but we
        // use KitKat here to be aligned with "Fullscreen"  preference
        final Window window = activity.getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }
}
