package me.zsr.viewmodel;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import me.zsr.common.LogUtil;
import me.zsr.model.ModelAction;
import me.zsr.model.ModelObserver;
import me.zsr.bean.Subscription;
import me.zsr.model.SubscriptionModel;

public class SubscriptionViewModel {
    private ViewModelObserver<Subscription> mObserver;
    private Context mContext;
    private List<Subscription> mLiveDataList = new ArrayList<>();
    private ModelObserver<Subscription> mModelObserver = new ModelObserver<Subscription>() {
        @Override
        public void onDataChanged(ModelAction action, List<Subscription> dataList) {
            switch (action) {
                case ADD:
                    for (Subscription subscription : dataList) {
                        mLiveDataList.add(subscription.clone());
                    }
                    mObserver.onDataChanged(mLiveDataList);
                    requestUpdate();
                    break;
                case MODIFY:
                    for (Subscription modifiedSubscription : dataList) {
                        for (Subscription liveSubscription : mLiveDataList) {
                            if (modifiedSubscription.getId().equals(liveSubscription.getId())) {
                                update(liveSubscription, modifiedSubscription);
                            }
                        }
                    }
                    break;
                case DELETE:
                    mLiveDataList.removeAll(dataList);
                    break;
            }

            mObserver.onDataChanged(mLiveDataList);
        }
    };

    public SubscriptionViewModel(ViewModelObserver<Subscription> observer, Context context) {
        LogUtil.i("create SubscriptionViewModel " + this);
        mObserver = observer;
        mContext = context;

        SubscriptionModel.getInstance().registerObserver(mModelObserver);
        initLoad();
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
