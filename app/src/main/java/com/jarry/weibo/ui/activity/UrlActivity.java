package com.jarry.weibo.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.jarry.weibo.R;
import com.jarry.weibo.ui.presenter.UrlPresenter;
import com.jarry.weibo.ui.base.MVPBaseActivity;
import com.jarry.weibo.ui.view.IUrlView;

import butterknife.Bind;

/**
 * Created by Jarry 2016/8/1.
 *
 *
 */
public class UrlActivity extends MVPBaseActivity<IUrlView,UrlPresenter> implements IUrlView {

    public static final String WEIBO_URL = "weibo_url";

    @Bind(R.id.pb_progress)
    ProgressBar pb_progress;
    @Bind(R.id.url_web)
    WebView url_web;

    String mWeibo_url;

    @Override
    protected UrlPresenter createPresenter() {
        return new UrlPresenter(this);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_url;
    }

    @Override
    public boolean canBack() {
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        parseIntent();
        mPresenter.setWebView(mWeibo_url);
    }

    public static Intent newIntent(Context context, String url) {
        Intent intent = new Intent(context, UrlActivity.class);
        intent.putExtra(UrlActivity.WEIBO_URL, url);
        return intent;
    }

    /**
     * 得到Intent传递的数据
     */
    private void parseIntent() {
        mWeibo_url = getIntent().getStringExtra(WEIBO_URL);
    }

    @Override
    public ProgressBar getProgressBar() {
        return pb_progress;
    }

    @Override
    public WebView getWebView() {
        return url_web;
    }
}
