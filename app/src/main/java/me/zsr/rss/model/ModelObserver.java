package me.zsr.rss.model;

import java.util.List;

public interface ModelObserver {
    void onDataChanged(ModelAction action, List<?> dataList);
}
