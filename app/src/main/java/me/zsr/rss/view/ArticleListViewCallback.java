package me.zsr.rss.view;

import java.util.List;

import me.zsr.bean.Article;

public interface ArticleListViewCallback {
    void onArticleClick(List<Article> articleList, int pos);
}
