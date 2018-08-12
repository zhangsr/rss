package me.zsr.rss;

import android.app.Application;

import com.umeng.commonsdk.UMConfigure;

import me.zsr.common.LogUtil;
import me.zsr.common.SPManager;
import me.zsr.common.ThreadManager;
import me.zsr.common.VolleySingleton;
import me.zsr.viewmodel.ViewModelManager;

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

        UMConfigure.init(this, "5b7019f68f4a9d06fc000798", "default", UMConfigure.DEVICE_TYPE_PHONE, "");

        //**************** No dependence end *****************

        ThreadManager.init();

        ViewModelManager.init(this);
    }
}
