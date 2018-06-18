package me.zsr.rss.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;

import java.util.List;

import me.zsr.bean.Article;
import me.zsr.bean.Subscription;
import me.zsr.rss.ArticleActivity;
import me.zsr.rss.Constants;

public class InboxPage extends IPage implements SubscriptionViewCallback, ArticleListViewCallback {
    private SubscriptionView mSubscriptionView;
    private ArticleListView mArticleListView;
    private Context mContext;

    public InboxPage(@NonNull Context context) {
        super(context);
        mContext = context;

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

    @Override
    public void onArticleClick(List<Article> articleList, int pos) {
        // TODO: 11/10/16 if no content and desc, shake then stay, and upload source

        Intent intent = new Intent(mContext, ArticleActivity.class);
        Bundle bundle = new Bundle();
        bundle.putLongArray(Constants.KEY_BUNDLE_ARTICLE_ID, ArticleUtil.getIdArray(articleList));
        bundle.putInt(Constants.KEY_BUNDLE_ARTICLE_INDEX, pos);
        intent.putExtras(bundle);
        mContext.startActivity(intent);
    }
}
