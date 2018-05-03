package com.jarry.weibo.ui.view;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by Jarry 2016/8/17.
 *
 *
 */
public interface IFAPView {
    void setDataRefresh(Boolean refresh);
    RecyclerView getRecyclerView();
    LinearLayoutManager getLayoutManager();
    GridLayoutManager getGridLayoutManager();
    String getTag();
}
