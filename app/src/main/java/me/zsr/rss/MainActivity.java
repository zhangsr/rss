package me.zsr.rss;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import me.zsr.common.ThreadManager;
import me.zsr.rss.view.DiscoverPage;
import me.zsr.rss.view.IPage;
import me.zsr.rss.view.InboxPage;
import me.zsr.rss.view.PersonPage;
import me.zsr.viewmodel.ModelProxy;
import me.zsr.viewmodel.PresetDiscoverViewModel;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    private FrameLayout mPageContainer;
    private Menu mMenu;
    private IPage mInboxPage;
    private IPage mDiscoverPage;
    private IPage mPersonPage;
    private IPage mCurrentPage;
    private ImageView mRefreshImageView;

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
                showOptionMenu(R.id.action_refresh);
                showOptionMenu(R.id.action_add);
                hideOptionMenu(R.id.action_search);
                break;
            case R.id.navigation_discover:
                mCurrentPage = getDiscoverPage();
                hideOptionMenu(R.id.action_refresh);
                hideOptionMenu(R.id.action_add);
                showOptionMenu(R.id.action_search);
                break;
            case R.id.navigation_person:
                mCurrentPage = getPersonPage();
                hideOptionMenu(R.id.action_refresh);
                hideOptionMenu(R.id.action_add);
                hideOptionMenu(R.id.action_search);
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
            DiscoverPage discoverPage = new DiscoverPage(this);
            PresetDiscoverViewModel viewModel = new PresetDiscoverViewModel(discoverPage, this);
            discoverPage.setViewModel(viewModel);
            viewModel.preloadData();
            mDiscoverPage = discoverPage;
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        mMenu = menu;
        getMenuInflater().inflate(R.menu.menu_main, menu);

        mRefreshImageView = new ImageView(this);
        mRefreshImageView.setImageResource(R.drawable.baseline_autorenew_white_24);
        mRefreshImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mRefreshImageView.getAnimation() == null || !mRefreshImageView.getAnimation().hasStarted()) {
                    Toast.makeText(MainActivity.this, "onRequestUpdate", Toast.LENGTH_SHORT).show();
                    ModelProxy.requestUpdateAll();

                    mRefreshImageView.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, R.anim.rotate_indefinitely));
                    ThreadManager.postDelay(new Runnable() {
                        @Override
                        public void run() {
                            mRefreshImageView.clearAnimation();
                        }
                    }, 5000);
                }
            }
        });
        menu.findItem(R.id.action_refresh).setActionView(mRefreshImageView);

        menu.findItem(R.id.action_add).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                DialogFactory.showCreateLocalAccountDialog(MainActivity.this);
                return true;
            }
        });

        menu.findItem(R.id.action_search).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                startActivity(new Intent(MainActivity.this, SearchActivity.class));
                return true;
            }
        });

        return true;
    }

    private void hideOptionMenu(int id) {
        MenuItem item = mMenu.findItem(id);
        item.setVisible(false);
    }

    private void showOptionMenu(int id) {
        MenuItem item = mMenu.findItem(id);
        item.setVisible(true);
    }
}
