package me.zsr.viewmodel;

import android.content.Context;
import android.text.TextUtils;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import me.zsr.common.FileUtil;
import me.zsr.common.LogUtil;
import me.zsr.common.ThreadManager;
import me.zsr.common.VolleySingleton;
import me.zsr.bean.Discover;
import me.zsr.bean.Subscription;
import me.zsr.model.SubscriptionModel;
import me.zsr.model.SubscriptionRequest;

public class DiscoverViewModel {
    private ViewModelObserver<Discover> mObserver;
    private Context mContext;
    private List<Discover> mLiveDataList;
    private List<Discover> mCacheDataList;

    public DiscoverViewModel(ViewModelObserver<Discover> observer, Context context) {
        LogUtil.i("create DiscoverViewModel " + this);
        mObserver = observer;
        mContext = context;
        preloadData();
    }

    private void preloadData() {
        ThreadManager.postInBackground(new Runnable() {
            @Override
            public void run() {
                String presetJson = FileUtil.readAssetFie(mContext, "preset_discover.json");
                try {
                    JSONObject jsonObject = new JSONObject(presetJson);
                    String listStr = jsonObject.getString("list");
                    Type listType = new TypeToken<List<Discover>>() {}.getType();
                    mCacheDataList = new Gson().fromJson(listStr, listType);

                    mLiveDataList = new ArrayList<>();
                    for (Discover discover : mCacheDataList) {
                        mLiveDataList.add(discover.clone());
                    }

                    ThreadManager.post(new Runnable() {
                        @Override
                        public void run() {
                            if (mObserver != null) {
                                mObserver.onDataChanged(mLiveDataList);
                            }
                        }
                    });

                    LogUtil.i("preloadData " + mCacheDataList.size());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
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

    public void onItemClick(List<Discover> dataList, int pos) {
    }

    public boolean onItemLongClick(List<Discover> dataList, int pos) {
        return false;
    }

    public void OnItemAddClick(List<Discover> dataList, int pos) {
        addSubscriptionByUrl(dataList.get(pos).getUrl());
    }
}
