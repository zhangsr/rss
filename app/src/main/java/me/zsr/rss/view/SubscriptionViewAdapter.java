package me.zsr.rss.view;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import me.zsr.rss.R;
import me.zsr.rss.model.Subscription;

public class SubscriptionViewAdapter extends RecyclerView.Adapter<SubscriptionViewHolder> {
    private List<Subscription> mSubscriptionList;
    private RecycleViewObserver mObserver;

    public SubscriptionViewAdapter(List<Subscription> list, RecycleViewObserver observer) {
        mSubscriptionList = list;
        mObserver = observer;
    }

    @Override
    public SubscriptionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.subscription_item, parent, false);
        return new SubscriptionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SubscriptionViewHolder holder, final int position) {
        if (mSubscriptionList == null || position >= mSubscriptionList.size()) {
            return;
        }
        holder.bind(mSubscriptionList.get(position), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mObserver.onItemClick(v, mSubscriptionList, position);
            }
        }, new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return mObserver.onItemLongClick(v, mSubscriptionList, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mSubscriptionList.size();
    }
}
