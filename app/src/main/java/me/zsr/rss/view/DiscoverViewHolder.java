package me.zsr.rss.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import me.zsr.bean.Discover;
import me.zsr.rss.R;

public class DiscoverViewHolder extends RecyclerView.ViewHolder {
    private View mItemView;
    private TextView mNameTextView;
    private ImageView mIconImageView;
    private TextView mRssUrlTextView;
    private ImageButton mAddButton;

    public DiscoverViewHolder(View itemView) {
        super(itemView);
        mItemView = itemView;
        mNameTextView = itemView.findViewById(R.id.discover_name);
        mIconImageView = itemView.findViewById(R.id.discover_icon);
        mRssUrlTextView = itemView.findViewById(R.id.rss_url);
        mAddButton = itemView.findViewById(R.id.add_btn);
    }

    public void bind(Discover discover, final View.OnClickListener clickListener,
                     View.OnLongClickListener longClickListener, View.OnClickListener addClickListener) {
        if (discover == null) {
            return;
        }
        mItemView.setOnClickListener(clickListener);
        mItemView.setOnLongClickListener(longClickListener);
        mNameTextView.setText(discover.getTitle());
        mRssUrlTextView.setText(shrinkUrl(discover.getUrl()));
//        ImageLoader.getInstance().displayImage(discover.getIconUrl(), mIconImageView, ImageLoaderManager.getSubsciptionIconOptions(mItemView.getContext()));
        mAddButton.setOnClickListener(addClickListener);
    }

    private String shrinkUrl(String url) {
        if (url.startsWith("http://www")) {
            return url.substring(7);
        } else if (url.startsWith("www")) {
            return url.substring(3);
        } else if (url.startsWith("http://")) {
            return url.substring(10);
        } else {
            return url;
        }
    }
}
