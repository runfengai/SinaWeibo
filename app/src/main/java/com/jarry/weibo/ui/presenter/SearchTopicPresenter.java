package com.jarry.weibo.ui.presenter;


import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.jarry.weibo.BuildConfig;
import com.jarry.weibo.R;
import com.jarry.weibo.bean.SearchBean;
import com.jarry.weibo.bean.SearchBean;
import com.jarry.weibo.bean.Status;
import com.jarry.weibo.ui.adapter.TopicListAdapter;
import com.jarry.weibo.ui.view.ISearchTopicView;
import com.sina.weibo.sdk.auth.AccessTokenKeeper;
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
 * <p>
 * <p>
 * MainPresenter init some data
 */
public class SearchTopicPresenter extends BasePresenter<ISearchTopicView> {

    private static final String TAG = "SearchTopicPresenter";

    private Context context;
    private ISearchTopicView homeView;
    private RecyclerView recyclerView;
    private TopicListAdapter adapter;
    private List<Status> list = new ArrayList<>();
    private LinearLayoutManager layoutManager;
    private int lastVisibleItem;
    private boolean isLoadMore = false; // 是否加载过更多
    String key = "";

    int page = 1;
    final static int count = 10;

    public SearchTopicPresenter(Context ctx) {
        this.context = ctx;
    }

    /**
     * 刷新
     *
     * @param key
     */
    public void getWeiBoTimeLine(String key) {
        this.key = key;
        page = 1;
        homeView = getHomeView();
        if (homeView != null) {
            recyclerView = homeView.getRecyclerView();
            layoutManager = homeView.getLayoutManager();

            Oauth2AccessToken token = readToken(context);

            if (token.isSessionValid()) {
                String tokenStr = token.getToken();
                weiBoApi.searchTopic(getRequestMap(tokenStr))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(searchBean ->
                                {
                                    disPlayWeiBoList(searchBean, context, homeView, recyclerView);
                                }
                                , this::loadError);
//                        .subscribe(searchBean -> {
//                            disPlayWeiBoList(searchBean, context, homeView, recyclerView);
//                        }, this::loadError);
            }
        }
    }

    // 点赞POST请求
    public void setLike(String id) {
        Oauth2AccessToken token = readToken(context);
        weiBoApi.setLike(getLikeMap(token.getToken(), id))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(postComments -> {

                });
    }

    private ISearchTopicView getHomeView() {
        if (isViewAttached()) {
            return getView();
        } else {
            return null;
        }
    }

    private void loadError(Throwable throwable) {
        throwable.printStackTrace();
        Toast.makeText(context, R.string.load_error, Toast.LENGTH_SHORT).show();
        if (homeView != null) {
            homeView.setDataRefresh(false);
        }
    }

    private Oauth2AccessToken readToken(Context context) {
        return AccessTokenKeeper.readAccessToken(context);
    }

    String max_id;

    // get request params
    private Map<String, Object> getRequestMap(String token) {
        Map<String, Object> map = new HashMap<>();
        map.put("access_token", token);
        map.put("q", key);
        map.put("count", count);
        map.put("page", page);

        return map;
    }

    private Map<String, Object> getLikeMap(String token, String id) {
        Map<String, Object> map = new HashMap<>();
        map.put("access_token", token);
        map.put("attitude", "simle");
        map.put("id", id);
        return map;
    }

    // refresh data
    private void disPlayWeiBoList(SearchBean friendsTimeLine, Context context, ISearchTopicView homeView, RecyclerView recyclerView) {
        if (BuildConfig.DEBUG) {
            Log.d(TAG, friendsTimeLine.toString());
        }
        if (isLoadMore) {
            if (max_id.equals("0")) {
                adapter.updateLoadStatus(adapter.LOAD_NONE);
                return;
            }
            list.addAll(getStatusData(friendsTimeLine));
            adapter.notifyDataSetChanged();
        } else {
            list = getStatusData(friendsTimeLine);
            adapter = new TopicListAdapter(context, list, "home_fg");
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
        homeView.setDataRefresh(false);
    }

    private List<Status> getStatusData(SearchBean searchBean) {
        return searchBean.getStatuses();
    }

    /**
     * recyclerView Scroll listener , maybe in here is wrong ?
     */
    public void scrollRecycleView() {
        if (recyclerView == null && getHomeView() != null)
            recyclerView = getHomeView().getRecyclerView();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (adapter == null) return;
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    lastVisibleItem = layoutManager
                            .findLastVisibleItemPosition();
                    if (lastVisibleItem + 1 == layoutManager
                            .getItemCount()) {//加载更多
                        adapter.updateLoadStatus(adapter.LOAD_PULL_TO);
                        isLoadMore = true;
                        adapter.updateLoadStatus(adapter.LOAD_MORE);
                        new Handler().postDelayed(() -> getWeiBoTimeLine(key), 1000);
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItem = layoutManager.findLastVisibleItemPosition();
            }
        });
    }

}
