package com.jarry.weibo.ui.presenter;

import android.app.Activity;
import android.content.Context;
import com.litesuits.orm.db.model.ConflictAlgorithm;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.jarry.weibo.MyApp;
import com.jarry.weibo.bean.User;
import com.jarry.weibo.ui.view.ISplashView;
import com.sina.weibo.sdk.auth.AccessTokenKeeper;
import com.jarry.weibo.util.StateUtils;
import java.util.HashMap;
import java.util.Map;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Jarry 2018/5/2.
 *
 *
 */
public class SplashPresenter extends BasePresenter<ISplashView> {

    private Activity activity;

    public SplashPresenter(Activity activity) {
        this.activity = activity;
    }

    public void getUserInfo(){
        Oauth2AccessToken token = readToken(activity);
        if(token.isSessionValid()){
            String tokenStr = token.getToken();
            String uId = token.getUid();
            weiBoApi.getUserInfo(getRequestMap(tokenStr,uId))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnNext(this::saveUserInfo)
                    .subscribe(user -> {

                    }, this::loadError);
        }
    }


    private void loadError(Throwable throwable) {
        throwable.printStackTrace();
        if(StateUtils.isNetworkAvailable(activity)){
            requestDataRefresh();
        }
    }


    public void requestDataRefresh() {
        getUserInfo();
    }

    private Oauth2AccessToken readToken(Context context){
        return AccessTokenKeeper.readAccessToken(context);
    }

    private Map<String,String> getRequestMap(String token,String uId){
        Map<String,String> map = new HashMap<>();
        map.put("access_token",token);
        map.put("uid",uId);
        return map;
    }

    private void saveUserInfo(User OAuthUser){
        MyApp.mDb.insert(OAuthUser, ConflictAlgorithm.Replace);
    }
}
