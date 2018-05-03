package com.jarry.weibo.ui.view;

import android.support.v7.widget.RecyclerView;

/**
 * Created by Jarry 2016/8/3.
 *
 *
 */
public interface ISendView {
    void permissionSetting();
    RecyclerView getPhotoGrid();
    void finishAndToast();
}
