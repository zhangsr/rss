package me.zsr.viewmodel;

import android.content.Context;

import me.zsr.model.DBManager;

public class ViewModelManager {

    public static void init(Context context) {
        DBManager.init(context);
    }
}
