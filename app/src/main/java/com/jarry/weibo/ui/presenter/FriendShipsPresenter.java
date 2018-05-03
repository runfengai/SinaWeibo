package com.jarry.weibo.ui.presenter;

import android.content.Context;

import com.jarry.weibo.ui.view.IFriendsView;

/**
 * Created by Jarry 2016/8/17.
 *
 *
 */
public class FriendShipsPresenter extends BasePresenter<IFriendsView> {

    private Context context;

    public FriendShipsPresenter(Context context) {
        this.context = context;
    }
}
