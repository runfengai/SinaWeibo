package com.jarry.weibo.ui.presenter;

import android.content.Context;

import com.jarry.weibo.ui.view.IWeiBoDetailView;

/**
 * Created by Jarry 2016/8/2.
 *
 *
 */
public class WeiBoDetailPresenter extends BasePresenter<IWeiBoDetailView> {

    private Context context;

    public WeiBoDetailPresenter(Context context) {
        this.context = context;
    }
}
