package me.zsr.rss.vm;

import java.util.List;

public interface ViewModelObserver<T> {
    void onDataChanged(List<T> dataList);
    void onRequestUpdate();
}
