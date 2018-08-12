package me.zsr.rss.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;

import java.util.List;

import me.zsr.bean.Subscription;
import me.zsr.rss.RefreshManager;
import me.zsr.viewmodel.SubscriptionViewModel;
import me.zsr.viewmodel.ViewModelObserver;

public class SubscriptionView extends FrameLayout implements ViewModelObserver<Subscription>, RecycleViewObserver<Subscription> {
    private SubscriptionViewModel mViewModel;
    private RecyclerView mRecyclerView;
    private SubscriptionViewAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private SubscriptionViewCallback mUICallback;

    public SubscriptionView(@NonNull Context context, SubscriptionViewCallback callback) {
        super(context);
        mUICallback = callback;

        mRecyclerView = new RecyclerView(context);
        mLayoutManager = new LinearLayoutManager(context);
        mRecyclerView.setLayoutManager(mLayoutManager);
        addView(mRecyclerView);

        mViewModel = new SubscriptionViewModel(this, getContext());
    }

    @Override
    public void onDataChanged(List<Subscription> dataList) {
        if (mAdapter == null || mAdapter.getData() != dataList) {
            mAdapter = new SubscriptionViewAdapter(dataList, this);
            mRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onRequestUpdate() {
        RefreshManager.showRefreshing(getContext());
    }

    @Override
    public void onItemClick(View view, List<Subscription> dataList, int pos) {
        mViewModel.onItemClick(dataList, pos);
        mUICallback.onSubscriptionClick(dataList.get(pos));
    }

    @Override
    public boolean onItemLongClick(View view, List<Subscription> dataList, int pos) {
        mUICallback.onSubscriptionLongClick(dataList.get(pos));
        return mViewModel.onItemLongClick(dataList, pos);
    }
}
