package me.zsr.rss;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import me.zsr.common.ThreadManager;
import me.zsr.rss.view.DiscoverPage;
import me.zsr.rss.view.IPage;
import me.zsr.rss.view.InboxPage;
import me.zsr.rss.view.PersonPage;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    private FrameLayout mPageContainer;
    private IPage mInboxPage;
    private IPage mDiscoverPage;
    private IPage mPersonPage;
    private IPage mCurrentPage;

    private boolean mCanBackExit;

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
        mCurrentPage = getInboxPage();
        mPageContainer.addView(getInboxPage());
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.navigation_inbox:
                mCurrentPage = getInboxPage();
                break;
            case R.id.navigation_discover:
                mCurrentPage = getDiscoverPage();
                break;
            case R.id.navigation_person:
                mCurrentPage = getPersonPage();
                break;
        }

        if (mCurrentPage != null) {
            mPageContainer.removeAllViews();
            mPageContainer.addView(mCurrentPage);
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

    @Override
    public void onBackPressed() {
        if (!mCurrentPage.handleBackPress()) {
            if (mCanBackExit) {
                super.onBackPressed();
            } else {
                mCanBackExit = true;
                Toast.makeText(this, R.string.back_exit_hint, Toast.LENGTH_SHORT).show();
                ThreadManager.postDelay(new Runnable() {
                    @Override
                    public void run() {
                        mCanBackExit = false;
                    }
                }, 5000);
            }
        }
    }
}
