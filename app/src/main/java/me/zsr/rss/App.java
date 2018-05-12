package me.zsr.rss;

import android.app.Application;

import me.zsr.rss.common.LogUtil;
import me.zsr.rss.common.ThreadManager;

/**
 * Architecture : MVVM
 * Network :
 * Image Loading :
 * ORM : GreenDao
 * JSON Parser : Gson
 * Event Route :
 * Stat : LeanCloud
 * Test :
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        // No dependence
        LogUtil.enable(BuildConfig.DEBUG);

        ThreadManager.init();

        DBManager.init(this);
    }
}
