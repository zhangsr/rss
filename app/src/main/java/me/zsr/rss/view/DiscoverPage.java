package me.zsr.rss.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.List;

import me.zsr.rss.model.Discover;
import me.zsr.rss.vm.DiscoverViewModel;
import me.zsr.rss.vm.ViewModelObserver;

public class DiscoverPage extends IPage implements ViewModelObserver<Discover>, RecycleViewObserver<Discover> {
    private DiscoverViewModel mViewModel;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    public DiscoverPage(@NonNull Context context) {
        super(context);

        mRecyclerView = new RecyclerView(context);
        mLayoutManager = new LinearLayoutManager(context);
        mRecyclerView.setLayoutManager(mLayoutManager);
        addView(mRecyclerView);

        mViewModel = new DiscoverViewModel(this, getContext());
    }

    @Override
    public void onDataChanged(List<Discover> dataList) {
        // TODO: 2018/5/12 new every time?
        mAdapter = new DiscoverAdapter(dataList, this);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onItemClick(View view, List<Discover> dataList, int pos) {

    }

    @Override
    public boolean onItemLongClick(View view, List<Discover> dataList, int pos) {
        return false;
    }
}
