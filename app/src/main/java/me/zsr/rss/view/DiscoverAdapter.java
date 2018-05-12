package me.zsr.rss.view;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import me.zsr.rss.R;
import me.zsr.rss.model.Discover;

public class DiscoverAdapter extends RecyclerView.Adapter<DiscoverViewHolder> {
    private List<Discover> mDiscoverList;
    private RecycleViewObserver mObserver;

    public DiscoverAdapter(List<Discover> list, RecycleViewObserver observer) {
        mDiscoverList = list;
        mObserver = observer;
    }

    @Override
    public DiscoverViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.discover_item, parent, false);
        return new DiscoverViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DiscoverViewHolder holder, final int position) {
        if (mDiscoverList == null || position >= mDiscoverList.size()) {
            return;
        }
        holder.bind(mDiscoverList.get(position), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mObserver.onItemClick(v, mDiscoverList, position);
            }
        }, new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return mObserver.onItemLongClick(v, mDiscoverList, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDiscoverList.size();
    }
}
