package me.zsr.viewmodel;

import java.util.List;

import me.zsr.bean.Article;
import me.zsr.bean.ArticleDao;
import me.zsr.bean.Subscription;
import me.zsr.bean.SubscriptionDao;
import me.zsr.model.ArticleModel;
import me.zsr.model.DBManager;

public class ArticleViewModel {

    public void markAllRead(boolean read, Article... articles) {
        ArticleModel.getInstance().markAllRead(read, articles);
    }

    public void saveArticle(final Article article) {
        ArticleModel.getInstance().saveArticle(article);
    }

    public List<Article> queryArticles(long id) {
        return DBManager.getArticleDao().queryBuilder().where(
                ArticleDao.Properties.Id.eq(id)).list();
    }

    // bad smell : subscription in article model
    public List<Subscription> querySubscriptions(long id) {
        return DBManager.getSubscriptionDao().queryBuilder().where(
                SubscriptionDao.Properties.Id.eq(id)).list();
    }
}
