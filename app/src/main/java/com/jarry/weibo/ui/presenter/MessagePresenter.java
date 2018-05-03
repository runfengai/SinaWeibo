package com.jarry.weibo.ui.presenter;

import android.content.Context;

import com.jarry.weibo.ui.view.IMessageView;

/**
 * Created by Jarry 2016/8/11.
 *
 *
 */
public class MessagePresenter extends BasePresenter<IMessageView> {

    private Context context;

    public MessagePresenter(Context context) {
        this.context = context;
    }
}
