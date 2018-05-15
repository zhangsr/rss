package me.zsr.rss;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.FrameLayout;

import me.zsr.rss.view.DiscoverPage;
import me.zsr.rss.view.IPage;
import me.zsr.rss.view.InboxPage;
import me.zsr.rss.view.PersonPage;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    private FrameLayout mPageContainer;
    private IPage mInboxPage;
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
        mPageContainer.addView(getInboxPage());
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        IPage targetPage = null;
        switch (item.getItemId()) {
            case R.id.navigation_inbox:
                targetPage = getInboxPage();
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

    private IPage getInboxPage() {
        if (mInboxPage == null) {
            mInboxPage = new InboxPage(this);
        }
        return mInboxPage;
    }

    private IPage getDiscoverPage() {
        if (mDiscoverPage == null) {
            mDiscoverPage = new DiscoverPage(this);
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
