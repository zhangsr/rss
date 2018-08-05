package me.zsr.rss.view;

import me.zsr.bean.Subscription;

public interface SubscriptionViewCallback {
    void onSubscriptionClick(Subscription subscription);
    void onSubscriptionLongClick(Subscription subscription);
}
