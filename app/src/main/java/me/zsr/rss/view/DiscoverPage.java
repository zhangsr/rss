package me.zsr.rss.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import java.util.List;

import me.zsr.bean.Discover;
import me.zsr.viewmodel.DiscoverViewModel;
import me.zsr.viewmodel.ViewModelObserver;

public class DiscoverPage extends IPage implements ViewModelObserver<Discover>, DiscoverRVObserver {
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
        mAdapter = new DiscoverViewAdapter(dataList, this);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onRequestUpdate() {

    }

    @Override
    public boolean onItemLongClick(View view, List<Discover> dataList, int pos) {
        return mViewModel.onItemLongClick(dataList, pos);
    }

    @Override
    public void onAddButtonClick(View view, List<Discover> dataList, int pos) {
        mViewModel.OnItemAddClick(dataList, pos);
        Toast.makeText(getContext(), "成功订阅 " + dataList.get(pos).getTitle(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemClick(View view, List<Discover> dataList, int pos) {

    }
}
