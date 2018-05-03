package com.jarry.weibo.ui.view;

import com.sina.weibo.sdk.auth.Oauth2AccessToken;

/**
 * Created by Jarry 2018/5/2.
 *
 *
 */
public interface ILoginView {

    void showTokenInfo(boolean hasExisted,Oauth2AccessToken mAccessToken);
    void toMainActivity();
}
