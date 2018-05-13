package me.zsr.rss.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.List;

import me.zsr.rss.model.Subscription;
import me.zsr.rss.vm.SubscriptionViewModel;
import me.zsr.rss.vm.ViewModelObserver;

public class SubscriptionView extends IPage implements ViewModelObserver<Subscription>, RecycleViewObserver<Subscription> {
    private SubscriptionViewModel mViewModel;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    public SubscriptionView(@NonNull Context context) {
        super(context);

        mRecyclerView = new RecyclerView(context);
        mLayoutManager = new LinearLayoutManager(context);
        mRecyclerView.setLayoutManager(mLayoutManager);
        addView(mRecyclerView);

        mViewModel = new SubscriptionViewModel(this, getContext());
    }

    @Override
    public void onDataChanged(List<Subscription> dataList) {
        // TODO: 2018/5/12 new every time?
        mAdapter = new SubscriptionViewAdapter(dataList, this);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onItemClick(View view, List<Subscription> dataList, int pos) {
        mViewModel.onItemClick(dataList, pos);
    }

    @Override
    public boolean onItemLongClick(View view, List<Subscription> dataList, int pos) {
        return mViewModel.onItemLongClick(dataList, pos);
    }
}
