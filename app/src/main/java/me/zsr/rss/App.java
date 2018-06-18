package me.zsr.rss;

import android.app.Application;

import me.zsr.common.LogUtil;
import me.zsr.common.SPManager;
import me.zsr.common.ThreadManager;
import me.zsr.common.VolleySingleton;
import me.zsr.model.DBManager;

/**
 * Architecture : MVVM
 * Network : Volley
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

        //**************** No dependence start *****************

        LogUtil.enable(BuildConfig.DEBUG);

        VolleySingleton.init(this);

        ImageLoaderManager.init(this);

        SPManager.init(this);

        //**************** No dependence end *****************

        ThreadManager.init();

        DBManager.init(this);
    }
}
