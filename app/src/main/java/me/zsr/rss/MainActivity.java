package me.zsr.rss;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.FrameLayout;

import me.zsr.rss.view.DiscoverView;
import me.zsr.rss.view.IPage;
import me.zsr.rss.view.SubscriptionView;
import me.zsr.rss.view.PersonPage;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    private FrameLayout mPageContainer;
    private IPage mSubscriptionPage;
    private IPage mDiscoverPage;
    private IPage mPersonPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPageContainer = findViewById(R.id.page_container);

        BottomNavigationView bottomNavigationView = findViewById(R.id.navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);

        setupInitPage();
    }

    public void setupInitPage() {
        mPageContainer.addView(getSubscriptionPage());
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        IPage targetPage = null;
        switch (item.getItemId()) {
            case R.id.navigation_subscription:
                targetPage = getSubscriptionPage();
                break;
            case R.id.navigation_discover:
                targetPage = getDiscoverPage();
                break;
            case R.id.navigation_person:
                targetPage = getPersonPage();
                break;
        }

        if (targetPage != null) {
            mPageContainer.removeAllViews();
            mPageContainer.addView(targetPage);
            return true;
        }
        return false;
    }

    private IPage getSubscriptionPage() {
        if (mSubscriptionPage == null) {
            mSubscriptionPage = new SubscriptionView(this);
        }
        return mSubscriptionPage;
    }

    private IPage getDiscoverPage() {
        if (mDiscoverPage == null) {
            mDiscoverPage = new DiscoverView(this);
        }
        return mDiscoverPage;
    }
    private IPage getPersonPage() {
        if (mPersonPage == null) {
            mPersonPage = new PersonPage(this);
        }
        return mPersonPage;
    }
}
