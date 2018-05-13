package me.zsr.rss.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import me.zsr.rss.DBManager;
import me.zsr.rss.common.ThreadManager;

public class SubscriptionModel extends BaseModel {
    private static List<ModelObserver> mObserverList = new ArrayList<>();
    private static SubscriptionModel sLocalModel;
    protected static List<Subscription> mSubscriptionList = new ArrayList<>();

    public static SubscriptionModel getInstance() {
        if (sLocalModel == null) {
            sLocalModel = new SubscriptionModel();
        }
        return sLocalModel;
    }

    @Override
    public List<Subscription> getDataSource() {
        return mSubscriptionList;
    }

    public void insert(Subscription... subscriptions) {
        insert(Arrays.asList(subscriptions));
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
                        mSubscriptionList.addAll(subscriptions);
                        notifyObservers(ModelAction.ADD, subscriptions);
                    }
                });
            }
        });
    }
}
