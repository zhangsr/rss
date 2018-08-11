package me.zsr.rss.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;

import com.afollestad.materialdialogs.MaterialDialog;

import java.util.ArrayList;
import java.util.List;

import me.zsr.bean.Subscription;
import me.zsr.rss.ArticleListActivity;
import me.zsr.rss.Constants;
import me.zsr.rss.R;
import me.zsr.viewmodel.ModelProxy;

public class InboxPage extends IPage implements SubscriptionViewCallback {
    private SubscriptionView mSubscriptionView;
    private Context mContext;

    public InboxPage(@NonNull Context context) {
        super(context);
        mContext = context;

        mSubscriptionView = new SubscriptionView(context, this);
        addView(mSubscriptionView);
    }

    @Override
    public void onSubscriptionClick(Subscription subscription) {
        Intent intent = new Intent(mContext, ArticleListActivity.class);
        Bundle bundle = new Bundle();
        bundle.putLongArray(Constants.KEY_BUNDLE_SUBSCRIPTION_ID, new long[]{subscription.getId()});
        bundle.putString(Constants.KEY_BUNDLE_TITLE, subscription.getTitle());
        intent.putExtras(bundle);
        mContext.startActivity(intent);
    }

    @Override
    public void onSubscriptionLongClick(final Subscription subscription) {
        List<CharSequence> menuList = new ArrayList<>();
        menuList.add(getContext().getResources().getString(R.string.mark_as_read));
        menuList.add(getContext().getResources().getString(R.string.remove_subscription));
        new MaterialDialog.Builder(getContext())
                .title(subscription.getTitle())
                .items(menuList.toArray(new CharSequence[menuList.size()]))
                .itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog materialDialog, View view, int i,
                                            CharSequence charSequence) {
                        switch (i) {
                            case 0:
                                ModelProxy.markAllRead(true, subscription.getId());
                                break;
                            case 1:
                                ModelProxy.deleteSubscription(subscription);
                                break;
                        }
                    }
                }).show();
    }
}
