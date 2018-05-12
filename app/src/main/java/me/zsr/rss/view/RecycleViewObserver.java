package me.zsr.rss.view;

import android.view.View;

import java.util.List;

public interface RecycleViewObserver<T> {
    void onItemClick(View view, List<T> dataList, int pos);
    boolean onItemLongClick(View view, List<T> dataList, int pos);
}
