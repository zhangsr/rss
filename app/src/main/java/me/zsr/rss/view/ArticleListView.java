package me.zsr.rss.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;

import java.util.List;

import me.zsr.rss.model.Article;
import me.zsr.rss.model.Subscription;
import me.zsr.rss.vm.ArticleListViewModel;
import me.zsr.rss.vm.ViewModelObserver;

public class ArticleListView extends FrameLayout implements ViewModelObserver<Article>, RecycleViewObserver<Article> {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArticleListViewCallback mUICallback;
    private ArticleListViewModel mViewModel;

    public ArticleListView(@NonNull Context context, ArticleListViewCallback callback) {
        super(context);
        mUICallback = callback;
        mRecyclerView = new RecyclerView(context);

        mLayoutManager = new LinearLayoutManager(context);
        mRecyclerView.setLayoutManager(mLayoutManager);
        addView(mRecyclerView);

    }

    public void showArticles(Subscription... subscriptions) {
       mViewModel = new ArticleListViewModel(this, subscriptions);
       mViewModel.load();
    }

    @Override
    public void onDataChanged(List<Article> dataList) {
        mAdapter = new ArticleListViewAdapter(dataList, this);
        mRecyclerView.setAdapter(mAdapter);
    }

    public void clear() {
        onDataChanged(null);
    }

    @Override
    public void onRequestUpdate() {

    }

    @Override
    public void onItemClick(View view, List<Article> dataList, int pos) {
        mViewModel.onItemClick(dataList, pos);
        mUICallback.onArticleClick(dataList, pos);
    }

    @Override
    public boolean onItemLongClick(View view, List<Article> dataList, int pos) {
        return false;
    }
}
