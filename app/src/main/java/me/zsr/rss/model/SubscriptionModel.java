package me.zsr.rss.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import me.zsr.rss.DBManager;
import me.zsr.rss.common.ThreadManager;

public class SubscriptionModel extends BaseModel implements ModelObserver<Article> {
    private static List<ModelObserver> mObserverList = new ArrayList<>();
    private static SubscriptionModel sInstance;

    public static SubscriptionModel getInstance() {
        if (sInstance == null) {
            sInstance = new SubscriptionModel();
            ArticleModel.getInstance().registerObserver(sInstance);
        }
        return sInstance;
    }

    public void insert(Subscription... subscriptions) {
        insert(Arrays.asList(subscriptions));
    }

    public void loadAll() {
        ThreadManager.postInBackground(new Runnable() {
            @Override
            public void run() {
                final List<Subscription> list = DBManager.getSubscriptionDao().queryBuilder().list();
                ThreadManager.post(new Runnable() {
                    @Override
                    public void run() {
                        notifyObservers(ModelAction.ADD, list);
                    }
                });
            }
        });
    }

    public void fetchAll() {
        final List<Subscription> list = DBManager.getSubscriptionDao().queryBuilder().list();
        for (Subscription subscription : list) {
            ArticleModel.getInstance().requestNetwork(subscription);
        }
    }

    @Override
    public List<ModelObserver> getObserverList() {
        return mObserverList;
    }

    public void insert(final List<Subscription> subscriptions) {
        ThreadManager.postInBackground(new Runnable() {
            @Override
            public void run() {
                DBManager.getSubscriptionDao().insertOrReplaceInTx(subscriptions);
                ThreadManager.post(new Runnable() {
                    @Override
                    public void run() {
                        notifyObservers(ModelAction.ADD, subscriptions);
                    }
                });
            }
        });
    }

    @Override
    public void onDataChanged(ModelAction action, List<Article> dataList) {
        switch (action) {
            case ADD:
                List<Long> subscriptionIdList = new ArrayList<>();
                for (Article article : dataList) {
                    long subscriptionId = article.getSubscriptionId();
                    boolean contain = false;
                    for (Long id : subscriptionIdList) {
                        if (id == subscriptionId) {
                            contain = true;
                        }
                    }
                    if (!contain) {
                        subscriptionIdList.add(subscriptionId);
                    }
                }

                List<Subscription> updatedList = new ArrayList<>();
                for (Long id : subscriptionIdList) {
                    Subscription subscription = DBManager.getSubscriptionDao().queryBuilder().where(SubscriptionDao.Properties.Id.eq(id)).unique();
                    final long totalCount = DBManager.getArticleDao().queryBuilder().where(
                            ArticleDao.Properties.SubscriptionId.eq(id)).count();
                    final long unreadCount = DBManager.getArticleDao().queryBuilder().where(
                            ArticleDao.Properties.SubscriptionId.eq(id),
                            ArticleDao.Properties.Read.eq(false)).count();
                    // TODO: 2018/5/19 update other params if any
                    subscription.setTotalCount(totalCount);
                    subscription.setUnreadCount(unreadCount);
                    DBManager.getSubscriptionDao().insertOrReplace(subscription);
                    updatedList.add(subscription);
                }

                notifyObservers(ModelAction.MODIFY, updatedList);
                break;
        }
    }
}
