package me.zsr.rss.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import me.zsr.rss.R;
import me.zsr.rss.model.Subscription;

public class SubscriptionViewHolder extends RecyclerView.ViewHolder {
    private View mItemView;
    private TextView mNameTextView;
    private ImageView mIconImageView;
    private TextView mCountTextView;

    public SubscriptionViewHolder(View itemView) {
        super(itemView);
        mItemView = itemView;
        mNameTextView = itemView.findViewById(R.id.subscription_name);
        mIconImageView = itemView.findViewById(R.id.subscription_icon);
        mCountTextView = itemView.findViewById(R.id.count_txt);
    }

    public void bind(Subscription subscription, final View.OnClickListener clickListener,
                     View.OnLongClickListener longClickListener) {
        if (subscription == null) {
            return;
        }
        mItemView.setOnClickListener(clickListener);
        mItemView.setOnLongClickListener(longClickListener);
        mNameTextView.setText(subscription.getTitle());
        mNameTextView.setSingleLine();
//        ImageLoader.getInstance().displayImage(discover.getIconUrl(), mIconImageView, ImageLoaderManager.getSubsciptionIconOptions(mItemView.getContext()));
//        if (discover.getUnreadCount() <= 0) {
//            mCountTextView.setText("");
//        } else {
//            mCountTextView.setText(String.valueOf(discover.getUnreadCount()));
//        }
    }
}
