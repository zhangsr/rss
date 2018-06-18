package me.zsr.rss.view;

import android.view.View;

import java.util.List;

import me.zsr.bean.Discover;

public interface DiscoverRVObserver extends  RecycleViewObserver<Discover>{
    void onAddButtonClick(View view, List<Discover> dataList, int pos);
}
