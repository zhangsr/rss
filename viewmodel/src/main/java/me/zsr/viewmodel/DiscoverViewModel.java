package me.zsr.viewmodel;

import android.content.Context;
import android.text.TextUtils;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import java.util.List;

import me.zsr.bean.Discover;
import me.zsr.bean.Subscription;
import me.zsr.common.LogUtil;
import me.zsr.common.VolleySingleton;
import me.zsr.model.SubscriptionModel;
import me.zsr.model.SubscriptionRequest;

public class DiscoverViewModel {
    protected ViewModelObserver<Discover> mObserver;
    protected Context mContext;

    public DiscoverViewModel(ViewModelObserver<Discover> observer, Context context) {
        LogUtil.i("create DiscoverViewModel " + this);
        mObserver = observer;
        mContext = context;
    }

    public void onItemClick(List<Discover> dataList, int pos) {
    }

    public boolean onItemLongClick(List<Discover> dataList, int pos) {
        return false;
    }

    public void OnItemAddClick(List<Discover> dataList, int pos) {
        addSubscriptionByUrl(dataList.get(pos).getUrl());
    }

    private void addSubscriptionByUrl(final String url) {
        if (TextUtils.isEmpty(url)) {
            return;
        }
        VolleySingleton.getInstance().getRequestQueue().cancelAll(this);
        SubscriptionRequest request = new SubscriptionRequest(url, new Response.Listener<Subscription>() {
            @Override
            public void onResponse(Subscription response) {
                LogUtil.i("onResponse=" + url);
                if (response == null) {
                    return;
                }
                response.setUrl(url);
                SubscriptionModel.getInstance().insert(response);
                // TODO: 2018/5/13 reload inbox
                // TODO: 2018/5/13 fetch this subscription
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO: 2/24/17 optimize retry by adding "feed" suffix
                LogUtil.i("onErrorResponse=" + url);
            }
        });
        request.setTag(this);
        LogUtil.i("request=" + url);
        VolleySingleton.getInstance().addToRequestQueue(request);
    }
}
