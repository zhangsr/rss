package me.zsr.rss.view;

import me.zsr.rss.model.Article;

public interface ArticleListViewCallback {
    void onArticleClick(Article article);
}
