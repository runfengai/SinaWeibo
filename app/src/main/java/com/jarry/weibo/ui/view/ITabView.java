package com.jarry.weibo.ui.view;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by Jarry 2016/8/2.
 *
 *
 */
public interface ITabView {

    String getWeiBoId();
    RecyclerView getRecyclerView();
    LinearLayoutManager getLayoutManager();
}
