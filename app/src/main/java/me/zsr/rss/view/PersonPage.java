package me.zsr.rss.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;

import me.zsr.common.SPManager;
import me.zsr.rss.R;

import static me.zsr.rss.Constants.*;

public class PersonPage extends IPage {
    private View mRootView;
    private ImageView mFavImageView;
    private ImageView mFontSizeImageView;
    private TextView mFontSizeTextView;

    public PersonPage(@NonNull Context context) {
        super(context);

        mRootView = LayoutInflater.from(context).inflate(R.layout.page_person, this);
        mFavImageView = mRootView.findViewById(R.id.fav_img);
        mFavImageView.setColorFilter(getResources().getColor(R.color.main_grey_normal));
        mFontSizeImageView = mRootView.findViewById(R.id.font_size_img);
        mFontSizeImageView.setColorFilter(getResources().getColor(R.color.main_grey_normal));
        mFontSizeTextView = mRootView.findViewById(R.id.font_size_txt);
        mRootView.findViewById(R.id.font_size_layout).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                showFontSizeChoice();
            }
        });
        setFontSizeText(SPManager.getInt(KEY_FONT_SIZE, FONT_SIZE_MEDIUM));
    }

    private void showFontSizeChoice() {
        // Index map to size value
        int currentSize = SPManager.getInt(KEY_FONT_SIZE, FONT_SIZE_MEDIUM);
        new MaterialDialog.Builder(getContext())
                .title(R.string.font)
                .items(R.array.font_size)
                .itemsCallbackSingleChoice(currentSize, new MaterialDialog.ListCallbackSingleChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                        SPManager.setInt(KEY_FONT_SIZE, which);
                        setFontSizeText(which);
                        dialog.dismiss();
                        return true; // allow selection
                    }
                })
                .show();
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
