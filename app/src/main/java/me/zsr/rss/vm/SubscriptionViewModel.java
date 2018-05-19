package me.zsr.rss.vm;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import me.zsr.rss.common.LogUtil;
import me.zsr.rss.common.ThreadManager;
import me.zsr.rss.model.ModelAction;
import me.zsr.rss.model.ModelObserver;
import me.zsr.rss.model.Subscription;
import me.zsr.rss.model.SubscriptionModel;

public class SubscriptionViewModel implements ModelObserver<Subscription> {
    private ViewModelObserver<Subscription> mObserver;
    private Context mContext;
    private List<Subscription> mLiveDataList;
    private List<Subscription> mCacheDataList = new ArrayList<>();

    public SubscriptionViewModel(ViewModelObserver<Subscription> observer, Context context) {
        LogUtil.i("create SubscriptionViewModel " + this);
        mObserver = observer;
        mContext = context;

        SubscriptionModel.getInstance().registerObserver(this);
        initLoad();

        ThreadManager.postDelay(new Runnable() {
            @Override
            public void run() {
                requestUpdate();
            }
        }, 3000);
    }

    private void initLoad() {
        SubscriptionModel.getInstance().loadAll();
    }

    private void requestUpdate() {
        mObserver.onRequestUpdate();
        SubscriptionModel.getInstance().fetchAll();
    }

    public void onItemClick(List<Subscription> dataList, int pos) {
    }

    public boolean onItemLongClick(List<Subscription> dataList, int pos) {
        return false;
    }

    @Override
    public void onDataChanged(ModelAction action, List<Subscription> dataList) {
        switch (action) {
            case ADD:
                mCacheDataList.addAll(dataList);
                mLiveDataList = new ArrayList<>();
                for (Subscription subscription : mCacheDataList) {
                    mLiveDataList.add(subscription.clone());
                }
                mObserver.onDataChanged(mLiveDataList);
                break;
            case MODIFY:
                for (Subscription modifiedSubscription : dataList) {
                    for (Subscription liveSubscription : mLiveDataList) {
                        if (modifiedSubscription.getId().equals(liveSubscription.getId())) {
                            update(liveSubscription, modifiedSubscription);
                        }
                    }
                }
                mObserver.onDataChanged(mLiveDataList);
                break;
        }
    }

    private void update(Subscription oldS, Subscription newS) {
        // TODO: 2018/5/19 update more
        oldS.setUnreadCount(newS.getUnreadCount());
        oldS.setTotalCount(newS.getTotalCount());
    }
}
