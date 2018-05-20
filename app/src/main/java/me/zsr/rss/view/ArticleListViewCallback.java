package me.zsr.rss.view;

import java.util.List;

import me.zsr.rss.model.Article;

public interface ArticleListViewCallback {
    void onArticleClick(List<Article> articleList, int pos);
}
