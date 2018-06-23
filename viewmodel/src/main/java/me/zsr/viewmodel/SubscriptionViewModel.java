package me.zsr.viewmodel;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import me.zsr.common.LogUtil;
import me.zsr.common.ThreadManager;
import me.zsr.model.ModelAction;
import me.zsr.model.ModelObserver;
import me.zsr.bean.Subscription;
import me.zsr.model.SubscriptionModel;

public class SubscriptionViewModel {
    private ViewModelObserver<Subscription> mObserver;
    private Context mContext;
    private List<Subscription> mLiveDataList;
    private List<Subscription> mCacheDataList = new ArrayList<>();
    private ModelObserver<Subscription> mModelObserver = new ModelObserver<Subscription>() {
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
    };

    public SubscriptionViewModel(ViewModelObserver<Subscription> observer, Context context) {
        LogUtil.i("create SubscriptionViewModel " + this);
        mObserver = observer;
        mContext = context;

        SubscriptionModel.getInstance().registerObserver(mModelObserver);
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

    private void update(Subscription oldS, Subscription newS) {
        // TODO: 2018/5/19 update more
        oldS.setUnreadCount(newS.getUnreadCount());
        oldS.setTotalCount(newS.getTotalCount());
    }
}
