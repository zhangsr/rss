package me.zsr.rss.view;

import android.content.Context;
import android.support.annotation.NonNull;

import me.zsr.rss.model.Article;
import me.zsr.rss.model.Subscription;

public class InboxPage extends IPage implements SubscriptionViewCallback, ArticleListViewCallback {
    private SubscriptionView mSubscriptionView;
    private ArticleListView mArticleListView;

    public InboxPage(@NonNull Context context) {
        super(context);

        mSubscriptionView = new SubscriptionView(context, this);
        mArticleListView = new ArticleListView(context, this);
        addView(mSubscriptionView);
    }

    @Override
    public void onSubscriptionClick(Subscription subscription) {
        removeView(mSubscriptionView);
        addView(mArticleListView);
        mArticleListView.showArticles(subscription);
    }

    @Override
    public void onArticleClick(Article article) {

    }

    @Override
    public boolean handleBackPress() {
        if (getChildAt(0) == mArticleListView) {
            removeView(mArticleListView);
            mArticleListView.clear();
            addView(mSubscriptionView);
            return true;
        } else {
            return false;
        }
    }
}
