package me.zsr.rss.view;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import me.zsr.bean.Article;
import me.zsr.rss.R;

public class ArticleListViewAdapter extends RecyclerView.Adapter<ArticleListViewHolder>{
    private List<Article> mArticleList;
    private RecycleViewObserver mObserver;

    public ArticleListViewAdapter(List<Article> list, RecycleViewObserver observer) {
        mArticleList = list;
        mObserver = observer;
    }

    @Override
    public ArticleListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.article_item, parent, false);
        return new ArticleListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ArticleListViewHolder holder, final int position) {
        if (mArticleList == null || position >= mArticleList.size()) {
            return;
        }
        holder.bind(mArticleList.get(position), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mObserver.onItemClick(v, mArticleList, position);
            }
        }, new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return mObserver.onItemLongClick(v, mArticleList, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mArticleList == null ? 0 : mArticleList.size();
    }
}
