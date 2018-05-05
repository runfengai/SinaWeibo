package com.jarry.weibo.ui.presenter;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.jarry.weibo.R;
import com.jarry.weibo.bean.DetailStatus;
import com.jarry.weibo.ui.activity.WeiBoDetailActivity;
import com.jarry.weibo.ui.view.IWeiBoDetailView;
import com.sina.weibo.sdk.auth.AccessTokenKeeper;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;

import java.util.HashMap;
import java.util.Map;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Jarry 2016/8/2.
 */
public class WeiBoDetailPresenter extends BasePresenter<IWeiBoDetailView> {

    private Context context;

    public WeiBoDetailPresenter(Context context) {
        this.context = context;
    }
//Sorry, current status can only be seen by login user in official client/website!
    public void getDetail(String id) {
        Oauth2AccessToken token = readToken(context);
        Log.e("==", "token=" + token.getToken());
        weiBoApi.getDetail(getCollectMap(token.getToken(), id))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(detail -> {
//                    getView().setDetailStatus(detail);
                }, this::loadError0);

    }
    private void loadError0(Throwable throwable) {
        ((WeiBoDetailActivity) context).isFavourited = false;
        throwable.printStackTrace();
        Toast.makeText(context, R.string.load_error, Toast.LENGTH_SHORT).show();
    }
    public void postCollect(String id, boolean toFavourite) {
        Oauth2AccessToken token = readToken(context);
        if (toFavourite)
            weiBoApi.setCollect(getCollectMap(token.getToken(), id))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(postComments -> {
                        Toast.makeText(context, "收藏成功", Toast.LENGTH_SHORT).show();
                    }, this::loadError);
        else {
            weiBoApi.setCancelCollect(getCollectMap(token.getToken(), id))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(postComments -> {
                        Toast.makeText(context, "取消收藏成功", Toast.LENGTH_SHORT).show();
                    }, this::loadError);
        }
    }  // get request params

    private Map<String, Object> getCollectMap1(String token, String id) {
        Map<String, Object> map = new HashMap<>();
        map.put("access_token", token);
        map.put("id", Long.valueOf(id));
        return map;
    }

    private Map<String, Object> getCollectMap(String token, String id) {
        Map<String, Object> map = new HashMap<>();
        map.put("access_token", token);
        map.put("id", id);
        return map;
    }

    private void loadError(Throwable throwable) {
        ((WeiBoDetailActivity) context).isFavourited = false;
        throwable.printStackTrace();
        Toast.makeText(context, R.string.load_error, Toast.LENGTH_SHORT).show();
    }

    private void loadError2(Throwable throwable) {
        ((WeiBoDetailActivity) context).isFavourited = true;
        throwable.printStackTrace();
        Toast.makeText(context, R.string.load_error, Toast.LENGTH_SHORT).show();
    }

    private Oauth2AccessToken readToken(Context context) {
        return AccessTokenKeeper.readAccessToken(context);
    }
}
