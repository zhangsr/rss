package me.zsr.rss.model;

import java.util.List;

public interface ModelObserver<T> {
    void onDataChanged(ModelAction action, List<T> dataList);
}
