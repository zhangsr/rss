package me.zsr.rss.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;

import com.afollestad.materialdialogs.MaterialDialog;

import java.util.ArrayList;
import java.util.List;

import me.zsr.bean.Article;
import me.zsr.bean.Subscription;
import me.zsr.rss.R;
import me.zsr.viewmodel.ArticleListViewModel;
import me.zsr.viewmodel.ModelProxy;
import me.zsr.viewmodel.ViewModelObserver;

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
    public boolean onItemLongClick(View view, final List<Article> dataList, final int pos) {
        final Article data = dataList.get(pos);
        List<CharSequence> menuList = new ArrayList<>();
        if (pos != 0) {
            menuList.add(view.getResources().getString(R.string.mark_all_above_as_read));
        }
        if (data.getRead()) {
            menuList.add(view.getResources().getString(R.string.mark_as_unread));
        }
        if (pos != dataList.size() - 1) {
            menuList.add(view.getResources().getString(R.string.mark_all_below_as_read));
        }
        if (menuList.size() == 0) {
            return true;
        }

        new MaterialDialog.Builder(getContext())
                .title(data.getTitle())
                .items(menuList.toArray(new CharSequence[menuList.size()]))
                .itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog materialDialog, View view, int i,
                                            CharSequence charSequence) {
                        if (charSequence.equals(view.getResources().getString(R.string.mark_all_above_as_read))) {
                            if (pos == 0 || pos >= dataList.size()) {
                                return;
                            }
                            ModelProxy.markAllRead(true, dataList.subList(0, pos));
                        } else if (charSequence.equals(view.getResources().getString(R.string.mark_as_read))){
                            ModelProxy.markAllRead(true, data);
                        } else if (charSequence.equals(view.getResources().getString(R.string.mark_all_below_as_read))){
                            if (pos + 1 > dataList.size()) {
                                return;
                            }
                            ModelProxy.markAllRead(true, dataList.subList(pos + 1, dataList.size()));
                        }
                    }
                }).show();
        return true;
    }
}
