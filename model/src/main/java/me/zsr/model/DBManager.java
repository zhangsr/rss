package me.zsr.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import me.zsr.bean.ArticleDao;
import me.zsr.bean.DaoMaster;
import me.zsr.bean.DaoSession;
import me.zsr.bean.DiscoverDao;
import me.zsr.bean.SubscriptionDao;

public class DBManager {
    private static final String DB_NAME = "rss";
    private static DaoSession sDaoSession;

    public static void init(Context context) {
        DaoMaster.OpenHelper helper = new DaoMaster.OpenHelper(context, DB_NAME, null){};
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        sDaoSession = daoMaster.newSession();
    }

    public static DiscoverDao getDiscoverDao() {
        return sDaoSession.getDiscoverDao();
    }

    public static SubscriptionDao getSubscriptionDao() {
        return sDaoSession.getSubscriptionDao();
    }

    public static ArticleDao getArticleDao() {
        return sDaoSession.getArticleDao();
    }
}
