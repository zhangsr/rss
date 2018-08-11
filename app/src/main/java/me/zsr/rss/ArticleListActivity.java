package me.zsr.rss;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;

import java.util.List;

import me.zsr.bean.Article;
import me.zsr.common.NumberUtil;
import me.zsr.rss.view.ArticleListView;
import me.zsr.rss.view.ArticleListViewCallback;
import me.zsr.rss.view.ArticleUtil;

public class ArticleListActivity extends BaseActivity implements ArticleListViewCallback{
    private LinearLayout mRootView;
    private Toolbar mToolbar;
    private ArticleListView mArticleListView;
    private long[] mIdArray;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_list);
        mRootView = findViewById(R.id.root);

        initToolbar();
        initContent();
    }

    private void initToolbar() {
        mToolbar = findViewById(R.id.toolbar);
        mToolbar.setNavigationIcon(getResources().getDrawable(R.drawable.round_arrow_back_black_24));
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        String title = getIntent().getExtras().getString(Constants.KEY_BUNDLE_TITLE);
        mToolbar.setTitle(title);
        mToolbar.setTitleTextColor(getResources().getColor(R.color.main_grey_dark));
    }

    private void initContent() {
        mArticleListView = new ArticleListView(this, this);
        mRootView.addView(mArticleListView);

        mIdArray = getIntent().getExtras().getLongArray(Constants.KEY_BUNDLE_SUBSCRIPTION_ID);
        mArticleListView.showArticles(NumberUtil.toLongArray(mIdArray));
    }

    @Override
    public void onArticleClick(List<Article> articleList, int pos) {
        Intent intent = new Intent(this, ArticleActivity.class);
        Bundle bundle = new Bundle();
        bundle.putLongArray(Constants.KEY_BUNDLE_ARTICLE_ID, ArticleUtil.getIdArray(articleList));
        bundle.putInt(Constants.KEY_BUNDLE_ARTICLE_INDEX, pos);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
