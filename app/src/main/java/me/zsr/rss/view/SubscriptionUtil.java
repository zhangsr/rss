package me.zsr.rss.view;

import java.util.List;

import me.zsr.bean.Subscription;

public class SubscriptionUtil {

    public static long[] getIdArray(List<Subscription> dataList) {
        if (dataList == null || dataList.size() == 0) {
            return null;
        }
        long[] idArray = new long[dataList.size()];
        for (int i = 0; i < idArray.length; i++) {
            idArray[i] = dataList.get(i).getId();
        }
        return idArray;
    }
}
