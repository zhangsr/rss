package me.zsr.rss;

import android.content.Context;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import me.zsr.common.ThreadManager;

public class RefreshManager {
    private static ImageView mRefreshImageView;

    public static void init(ImageView targetView) {
        mRefreshImageView = targetView;
    }

    public static  boolean isShowingRefreshing() {
        return mRefreshImageView != null
                && mRefreshImageView.getAnimation() != null
                && mRefreshImageView.getAnimation().hasStarted();
    }

    public static void showRefreshing(Context context) {
        if (mRefreshImageView == null || isShowingRefreshing()) {
            return;
        }

        mRefreshImageView.startAnimation(AnimationUtils.loadAnimation(context, R.anim.rotate_indefinitely));
        Toast.makeText(context, "refresh", Toast.LENGTH_SHORT).show();
        ThreadManager.postDelay(new Runnable() {
            @Override
            public void run() {
                mRefreshImageView.clearAnimation();
            }
        }, 10 * 1000);
    }
}
