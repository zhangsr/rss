package me.zsr.common;

import android.app.Activity;
import android.os.Build;

public class AnimationHelper {

    public static void setFadeTransition(Activity activity) {
        if (activity == null) {
            return;
        }
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
            activity.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        }
    }
}
