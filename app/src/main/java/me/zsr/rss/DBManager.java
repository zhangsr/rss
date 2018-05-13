package me.zsr.rss;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import me.zsr.rss.model.ArticleDao;
import me.zsr.rss.model.DaoMaster;
import me.zsr.rss.model.DaoSession;
import me.zsr.rss.model.DiscoverDao;
import me.zsr.rss.model.SubscriptionDao;

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
