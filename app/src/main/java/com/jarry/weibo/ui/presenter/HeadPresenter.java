package com.jarry.weibo.ui.presenter;

import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.jarry.weibo.BuildConfig;
import com.jarry.weibo.R;
import com.jarry.weibo.bean.FriendsTimeLine;
import com.jarry.weibo.bean.Status;
import com.jarry.weibo.ui.adapter.WeiBoListAdapter;
import com.jarry.weibo.ui.view.IHeadView;
import com.jarry.weibo.ui.view.IHeadView;
import com.jarry.weibo.util.AccessTokenKeeper;
import com.jarry.weibo.util.PrefUtils;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Jarry 2018/5/2.
 */
public class HeadPresenter extends BasePresenter<IHeadView> {

    private static final String TAG = "HeadPresenter";

    private Context context;
    private IHeadView headView;

    public HeadPresenter(Context ctx) {
        this.context = ctx;
    }
}
