package com.jarry.weibo.ui.view;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.jarry.weibo.widget.ClickCircleImageView;

/**
 * Created by Jarry 2018/5/2.
 *
 *
 * interface of mainActivity
 */
public interface ISearchTopicView {


    void setDataRefresh(Boolean refresh);
    RecyclerView getRecyclerView();
    LinearLayoutManager getLayoutManager();
}
