package me.zsr.rss.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import java.util.List;

import me.zsr.bean.Subscription;
import me.zsr.viewmodel.SubscriptionViewModel;
import me.zsr.viewmodel.ViewModelObserver;

public class SubscriptionView extends FrameLayout implements ViewModelObserver<Subscription>, RecycleViewObserver<Subscription> {
    private SubscriptionViewModel mViewModel;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
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
        // TODO: 2018/5/12 new every time?
        mAdapter = new SubscriptionViewAdapter(dataList, this);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onRequestUpdate() {
        Toast.makeText(getContext(), "onRequestUpdate", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemClick(View view, List<Subscription> dataList, int pos) {
        mViewModel.onItemClick(dataList, pos);
        mUICallback.onSubscriptionClick(dataList.get(pos));
    }

    @Override
    public boolean onItemLongClick(View view, List<Subscription> dataList, int pos) {
        return mViewModel.onItemLongClick(dataList, pos);
    }
}
