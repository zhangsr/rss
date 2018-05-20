package me.zsr.rss.view;

import android.content.Context;

import java.util.List;

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

    public static long[] getIdArray(List<Article> dataList) {
        if (dataList == null || dataList.size() == 0) {
            return null;
        }
        long[] idArray = new long[dataList.size()];
        for (int i = 0; i < idArray.length; i++) {
            idArray[i] = dataList.get(i).getId();
        }
        return idArray;
    }
}
