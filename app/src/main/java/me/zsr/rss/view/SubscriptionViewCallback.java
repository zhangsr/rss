package me.zsr.rss.view;

import me.zsr.rss.model.Subscription;

public interface SubscriptionViewCallback {
    void onSubscriptionClick(Subscription subscription);
}
