package me.zsr.rss.view;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import me.zsr.bean.Discover;
import me.zsr.rss.R;

public class DiscoverViewAdapter extends RecyclerView.Adapter<DiscoverViewHolder> {
    private List<Discover> mDiscoverList;
    private DiscoverRVObserver mObserver;

    public DiscoverViewAdapter(List<Discover> list, DiscoverRVObserver observer) {
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
        }, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mObserver.onAddButtonClick(v, mDiscoverList, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDiscoverList.size();
    }
}
