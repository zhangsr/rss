package me.zsr.rss;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.FrameLayout;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    private FrameLayout mPageContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPageContainer = findViewById(R.id.page_container);

        BottomNavigationView bottomNavigationView = findViewById(R.id.navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.navigation_inbox:
                mPageContainer.setBackgroundColor(Color.RED);
                return true;
            case R.id.navigation_discover:
                mPageContainer.setBackgroundColor(Color.YELLOW);
                return true;
            case R.id.navigation_person:
                mPageContainer.setBackgroundColor(Color.BLUE);
                return true;
        }
        return false;
    }
}
