package me.zsr.rss;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setupStatusBar();
    }

    private void setupStatusBar() {
        Window window = getWindow();
        if (supportConfigStatusBarTextColor()) {
            View decorView = window.getDecorView();
            int vis = decorView.getSystemUiVisibility();
            vis |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            decorView.setSystemUiVisibility(vis);
        } else if (supportConfigStatusBarColor()) {
            window.setStatusBarColor(Color.parseColor("#33000000"));
        }

    }

    private boolean supportConfigStatusBarTextColor() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }

    private boolean supportConfigStatusBarColor() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }
}
