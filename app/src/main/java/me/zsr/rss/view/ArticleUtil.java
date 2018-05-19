package me.zsr.rss.view;

import android.content.Context;

import me.zsr.rss.common.HtmlImageGetterEx;
import me.zsr.rss.common.StringUtil;
import me.zsr.rss.htmltextview.HtmlTextView;
import me.zsr.rss.model.Article;

public class ArticleUtil {

    // TODO: 1/27/17 context manager
    public static void setContent(Context context, Article article, HtmlTextView textView, String subscriptionName) {
        if (article == null || textView == null) {
            return;
        }

        try {
            if (StringUtil.isNullOrEmpty(article.getContent())) {
                if (!StringUtil.isNullOrEmpty(article.getDescription())) {
                    textView.setHtml(article.getDescription(), new HtmlImageGetterEx(textView, null, true));
                }
            } else {
                textView.setHtml(article.getContent(), new HtmlImageGetterEx(textView, null, true));
            }
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }

    public static String getContent(Article article) {
        if (!StringUtil.isNullOrEmpty(article.getContent())) {
            return article.getContent();
        }
        return article.getDescription();
    }
}
