package me.zsr.rss;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.github.chrisbanes.photoview.PhotoView;
import com.nostra13.universalimageloader.core.ImageLoader;

import me.zsr.rss.common.AnimationHelper;

// TODO: 3/19/17 Animate scale from origin location
// TODO: 3/19/17 slide down to exit
public class PicActivity extends AppCompatActivity implements View.OnClickListener,
        View.OnLongClickListener {
    private String mImageUrl;
    private PhotoView mPhotoView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pic);

        findViewById(R.id.root).setOnClickListener(this);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if (bundle == null) {
            finish();
            return;
        }

        mImageUrl = bundle.getString(Constants.KEY_BUNDLE_PIC_URL);

        mPhotoView = findViewById(R.id.photo_view);
        mPhotoView.setOnClickListener(this);
        mPhotoView.setOnLongClickListener(this);

        ImageLoader.getInstance().displayImage(mImageUrl, mPhotoView);
    }

    @Override
    public void onClick(View v) {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        AnimationHelper.setFadeTransition(this);
    }

    @Override
    public boolean onLongClick(View v) {
        // TODO: 3/19/17 share and save
        return false;
    }
}
