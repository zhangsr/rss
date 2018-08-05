package me.zsr.viewmodel;

import android.text.TextUtils;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import java.util.List;

import me.zsr.bean.Article;
import me.zsr.bean.ArticleDao;
import me.zsr.bean.Subscription;
import me.zsr.bean.SubscriptionDao;
import me.zsr.common.LogUtil;
import me.zsr.common.VolleySingleton;
import me.zsr.model.ArticleModel;
import me.zsr.model.DBManager;
import me.zsr.model.SubscriptionModel;
import me.zsr.model.SubscriptionRequest;

public class ModelProxy {

    public static void markAllRead(boolean read, Article... articles) {
        ArticleModel.getInstance().markAllRead(read, articles);
    }

    public static void markAllRead(boolean read, List<Article> articles) {
        ArticleModel.getInstance().markAllRead(read, articles);
    }

    public static void markAllRead(final boolean read, final long subscriptionId) {
        ArticleModel.getInstance().markAllRead(read, subscriptionId);
    }

    public static void deleteSubscription(Subscription... subscriptions) {
        SubscriptionModel.getInstance().delete(subscriptions);
    }

    public void saveArticle(final Article article) {
        ArticleModel.getInstance().saveArticle(article);
    }

    public List<Article> queryArticles(long id) {
        return DBManager.getArticleDao().queryBuilder().where(
                ArticleDao.Properties.Id.eq(id)).list();
    }

    // bad smell : subscription in article model
    public List<Subscription> querySubscriptions(long id) {
        return DBManager.getSubscriptionDao().queryBuilder().where(
                SubscriptionDao.Properties.Id.eq(id)).list();
    }

    public static void addSubscriptionByUrl(final String url) {
        if (TextUtils.isEmpty(url)) {
            return;
        }
        VolleySingleton.getInstance().getRequestQueue().cancelAll(url);
        SubscriptionRequest request = new SubscriptionRequest(url, new Response.Listener<Subscription>() {
            @Override
            public void onResponse(Subscription response) {
                LogUtil.i("onResponse=" + url);
                if (response == null) {
                    return;
                }
                response.setUrl(url);
                SubscriptionModel.getInstance().insert(response);
                // TODO: 2018/5/13 reload inbox
                // TODO: 2018/5/13 fetch this subscription
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO: 2/24/17 optimize retry by adding "feed" suffix
                LogUtil.e("onErrorResponse=" + error.getMessage());
            }
        });
        request.setTag(url);
        LogUtil.i("request=" + url);
        VolleySingleton.getInstance().addToRequestQueue(request);
    }

    public static void requestUpdateAll() {
        SubscriptionModel.getInstance().fetchAll();
    }
}
