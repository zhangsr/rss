package me.zsr.rss.vm;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import me.zsr.rss.common.FileUtil;
import me.zsr.rss.common.LogUtil;
import me.zsr.rss.common.ThreadManager;
import me.zsr.rss.model.Discover;

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
}
