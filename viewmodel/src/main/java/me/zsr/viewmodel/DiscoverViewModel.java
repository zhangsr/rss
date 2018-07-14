package me.zsr.viewmodel;

import android.content.Context;

import java.util.List;

import me.zsr.bean.Discover;
import me.zsr.common.LogUtil;

public class DiscoverViewModel {
    protected ViewModelObserver<Discover> mObserver;
    protected Context mContext;

    public DiscoverViewModel(ViewModelObserver<Discover> observer, Context context) {
        LogUtil.i("create DiscoverViewModel " + this);
        mObserver = observer;
        mContext = context;
    }

    public void onItemClick(List<Discover> dataList, int pos) {
    }

    public boolean onItemLongClick(List<Discover> dataList, int pos) {
        return false;
    }

    public void OnItemAddClick(List<Discover> dataList, int pos) {
        ModelProxy.addSubscriptionByUrl(dataList.get(pos).getUrl());
    }

}
