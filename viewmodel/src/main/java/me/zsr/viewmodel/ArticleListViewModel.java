package me.zsr.viewmodel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import me.zsr.common.ThreadManager;
import me.zsr.bean.Article;
import me.zsr.bean.Subscription;
import me.zsr.model.ArticleModel;
import me.zsr.model.ModelAction;
import me.zsr.model.ModelObserver;

public class ArticleListViewModel implements ModelObserver<Article> {
    private ViewModelObserver<Article> mObserver;
    private List<Subscription> mSubscriptionList;
    private List<Article> mLiveDataList;
    private List<Article> mCacheDataList = new ArrayList<>();
    private boolean mIsLoading;

    public ArticleListViewModel(ViewModelObserver<Article> observer, Subscription... subscriptions) {
        mObserver = observer;
        mSubscriptionList = Arrays.asList(subscriptions);
    }

    public void load() {
        if (mIsLoading) {
            return;
        }
        mIsLoading = true;

        ThreadManager.postInBackground(new Runnable() {
            @Override
            public void run() {
                mCacheDataList.clear();
                for (Subscription subscription : mSubscriptionList) {
                    mCacheDataList.addAll(ArticleModel.getInstance().queryBySubscriptionIdSync(subscription.getId()));
                }

                ThreadManager.post(new Runnable() {
                    @Override
                    public void run() {
                        mLiveDataList = new ArrayList<>();
                        for (Article article : mCacheDataList) {
                            mLiveDataList.add(article.clone());
                        }
                        mObserver.onDataChanged(mLiveDataList);
                        mIsLoading = false;
                    }
                });
            }
        });
    }

    @Override
    public void onDataChanged(ModelAction action, List<Article> dataList) {

    }

    public void onItemClick(List<Article> dataList, int pos) {
        if (dataList == null || pos >= dataList.size()) {
            return;
        }

        Article data = dataList.get(pos);
        if (!data.getRead()) {
            ArticleModel.getInstance().markAllRead(true, data);
        }

    }

}
