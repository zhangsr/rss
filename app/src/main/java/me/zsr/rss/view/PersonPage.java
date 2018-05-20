package me.zsr.rss.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import me.zsr.rss.R;
import me.zsr.rss.common.SPManager;

import static me.zsr.rss.Constants.*;

public class PersonPage extends IPage {
    private View mRootView;
    private ImageView mPersonImageView;
    private ImageView mFavImageView;
    private ImageView mFontSizeImageView;
    private TextView mFontSizeTextView;

    public PersonPage(@NonNull Context context) {
        super(context);

        mRootView = LayoutInflater.from(context).inflate(R.layout.page_person, this);
        mPersonImageView = mRootView.findViewById(R.id.person_img);
        mPersonImageView.setColorFilter(getResources().getColor(R.color.main_grey_normal));
        mFavImageView = mRootView.findViewById(R.id.fav_img);
        mFavImageView.setColorFilter(getResources().getColor(R.color.main_grey_normal));
        mFontSizeImageView = mRootView.findViewById(R.id.font_size_img);
        mFontSizeImageView.setColorFilter(getResources().getColor(R.color.main_grey_normal));
        mFontSizeTextView = mRootView.findViewById(R.id.font_size_txt);
        setFontSizeText(SPManager.getInt(KEY_FONT_SIZE, FONT_SIZE_MEDIUM));
    }

    private void setFontSizeText(int fontSize) {
        switch (fontSize) {
            case FONT_SIZE_SMALL:
                mFontSizeTextView.setText(R.string.small);
                break;
            case FONT_SIZE_MEDIUM:
                mFontSizeTextView.setText(R.string.medium);
                break;
            case FONT_SIZE_BIG:
                mFontSizeTextView.setText(R.string.big);
                break;
        }
    }
}
