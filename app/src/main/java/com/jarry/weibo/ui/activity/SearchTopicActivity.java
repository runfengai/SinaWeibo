package com.jarry.weibo.ui.activity;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;

import com.jarry.weibo.R;
import com.jarry.weibo.ui.base.MVPBaseActivity;
import com.jarry.weibo.ui.presenter.SearchTopicPresenter;
import com.jarry.weibo.ui.view.ISearchTopicView;

import butterknife.Bind;

public class SearchTopicActivity extends MVPBaseActivity<ISearchTopicView, SearchTopicPresenter> implements ISearchTopicView {
    static final String TAG = "SearchTopicActivity";
    private LinearLayoutManager mLayoutManager;
    @Bind(R.id.content_list)
    RecyclerView mRecyclerView;

    String key = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        Intent intent = getIntent();
        if (intent != null) {
            key = intent.getStringExtra(SearchManager.QUERY);
            if (!TextUtils.isEmpty(key)) {//此处刷新关键字
                Log.e(TAG, "KEY=" + key);
                requestDataRefresh();
            }

        }
    }

    @Override
    protected SearchTopicPresenter createPresenter() {
        return new SearchTopicPresenter(this);
    }

    protected void initView() {
        setTitle("搜索话题");
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        setDataRefresh(false);
        mPresenter.scrollRecycleView();
    }

    @Override
    public void requestDataRefresh() {
        super.requestDataRefresh();
        setDataRefresh(true);
        mPresenter.getWeiBoTimeLine(key);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_search;
    }


    @Override
    public void setDataRefresh(Boolean refresh) {
        setRefresh(refresh);
    }

    @Override
    public RecyclerView getRecyclerView() {
        return mRecyclerView;
    }

    @Override
    public LinearLayoutManager getLayoutManager() {
        return mLayoutManager;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.ab_search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));
        return true;
    }
}
