package com.jarry.weibo.ui.view;

import android.widget.ImageView;
import android.widget.TextView;

import com.jarry.weibo.widget.ClickCircleImageView;

/**
 * Created by Jarry 2018/5/2.
 *
 *
 * interface of mainActivity
 */
public interface IMainView {

    ImageView getUserBackGround();
    ClickCircleImageView getUserIcon();
    TextView getUserName();
}
