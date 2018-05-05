package com.jarry.weibo.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.jarry.weibo.BuildConfig;
import com.jarry.weibo.R;
import com.jarry.weibo.ui.presenter.SplashPresenter;
import com.jarry.weibo.ui.base.MVPBaseActivity;
import com.jarry.weibo.ui.view.ISplashView;
import com.sina.weibo.sdk.auth.AccessTokenKeeper;
import com.jarry.weibo.widget.splash.SplashView;

import java.util.Random;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Jarry 2018/5/2.
 * <p>
 * <p>
 * SplashActivity like Twitter !
 */
public class SplashActivity extends MVPBaseActivity<ISplashView, SplashPresenter> implements ISplashView {

    private static final String TAG = "SplashActivity";

    private Handler mHandler = new Handler();

    @Bind(R.id.splash_view)
    SplashView splash_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        mPresenter = createPresenter();
    }

    @Override
    protected SplashPresenter createPresenter() {
        return new SplashPresenter(SplashActivity.this);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void onStart() {
        super.onStart();
        mPresenter.getUserInfo();
        startLoadingData();
    }

    /**
     * start splash animation
     */
    private void startLoadingData() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                onLoadingDataEnded();
            }
        }, 1500);
    }

    private void onLoadingDataEnded() {
        loginOrMain();
    }

    @Override
    public void loginOrMain() {
        Oauth2AccessToken token = AccessTokenKeeper.readAccessToken(SplashActivity.this);

        if (token.isSessionValid()) {
            if (BuildConfig.DEBUG) {
                Log.d(TAG, token.toString() + "--uid-" + token.getUid());
            }
            startActivity(new Intent(SplashActivity.this, MainActivity.class));
        } else {
            startActivity(new Intent(SplashActivity.this, LoginActivity.class));
        }
        finish();
    }

}
