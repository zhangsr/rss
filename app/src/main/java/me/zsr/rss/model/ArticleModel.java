package me.zsr.rss.model;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.ArrayList;
import java.util.List;

import me.zsr.rss.DBManager;
import me.zsr.rss.common.ArticleListRequest;
import me.zsr.rss.common.LogUtil;
import me.zsr.rss.common.ThreadManager;
import me.zsr.rss.common.VolleySingleton;

public class ArticleModel extends BaseModel {
    private static List<ModelObserver> mObserverList = new ArrayList<>();
    private static ArticleModel sInstance;

    public static ArticleModel getInstance() {
        if (sInstance == null) {
            sInstance = new ArticleModel();
        }
        return sInstance;
    }

    @Override
    public List<ModelObserver> getObserverList() {
        return mObserverList;
    }

    public List<Article> queryBySubscriptionIdSync(long subscriptionId) {
        QueryBuilder qb = DBManager.getArticleDao().queryBuilder();
        qb.where(ArticleDao.Properties.SubscriptionId.eq(subscriptionId),
                ArticleDao.Properties.Trash.eq(false),
                qb.or(ArticleDao.Properties.Favorite.eq(false),
                        ArticleDao.Properties.Favorite.isNull()))
                .orderDesc(ArticleDao.Properties.Published);
        return qb.list();
    }

    public void requestNetwork(final Subscription subscription) {
        LOG_MA("requestNetwork name=" + subscription.getTitle());
        if (subscription == null) {
            return;
        }
        ArticleListRequest request = new ArticleListRequest(
                subscription,
                new Response.Listener<List<Article>>() {
                    @Override
                    public void onResponse(List<Article> response) {
                        LogUtil.d("onResponse : " + subscription.getTitle());
                        insertToDB(subscription.getId(), response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );
        VolleySingleton.getInstance().addToRequestQueue(request);
    }


    private void insertToDB(final long subscriptionId, final List<Article> articleList) {
        if (articleList == null) {
            return;
        }
        ThreadManager.postInBackground(new Runnable() {
            @Override
            public void run() {
                List<Article> dbArticleList = DBManager.getArticleDao().queryBuilder().where(
                        ArticleDao.Properties.SubscriptionId.eq(subscriptionId)).list();
                List<Article> newArticleList = new ArrayList<>();
                for (Article article : articleList) {
                    if (!dbArticleList.contains(article)) {
                        newArticleList.add(article);
                    }
                }
                if (newArticleList.size() != 0) {
                    DBManager.getArticleDao().insertInTx(newArticleList);
                    notifyObservers(ModelAction.ADD, newArticleList);
                }
            }
        });
    }
}
