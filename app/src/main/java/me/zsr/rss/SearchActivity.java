package me.zsr.rss;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.miguelcatalan.materialsearchview.MaterialSearchView;

import me.zsr.rss.view.DiscoverPage;
import me.zsr.viewmodel.SearchDiscoverViewModel;

public class SearchActivity extends AppCompatActivity {
    private ViewGroup mRootView;
    private MaterialSearchView mSearchView;
    private DiscoverPage mDiscoverPage;
    private SearchDiscoverViewModel mViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        mRootView = findViewById(R.id.root_search);

        setupSearch();

        setupResultArea();
    }

    public void setupSearch() {
        mSearchView = findViewById(R.id.search_view);
        mSearchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {

            }

            @Override
            public void onSearchViewClosed() {
                finish();
            }
        });
        mSearchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mViewModel.search(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        mSearchView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
        mSearchView.showSearch(false);
    }

    private void setupResultArea() {
        mDiscoverPage = new DiscoverPage(this);
        mViewModel = new SearchDiscoverViewModel(mDiscoverPage, this);
        mDiscoverPage.setViewModel(mViewModel);
        mRootView.addView(mDiscoverPage);
    }
}
