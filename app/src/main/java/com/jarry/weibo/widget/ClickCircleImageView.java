package com.jarry.weibo.widget;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import com.bumptech.glide.Glide;
import com.jarry.weibo.bean.User;
import com.jarry.weibo.ui.activity.UserActivity;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Jarry 2018/5/2.
 *
 */
public class ClickCircleImageView extends CircleImageView implements View.OnClickListener {

    private boolean isAttachedToWindow;
    private User user;
    private String icon_url;

    public ClickCircleImageView(Context context) {
        super(context);
        setOnClickListener(this);
    }

    public ClickCircleImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOnClickListener(this);
    }

    public ClickCircleImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setOnClickListener(this);
    }

    @Override
    public void onAttachedToWindow() {
        isAttachedToWindow = true;
        setUserImage(user);
        super.onAttachedToWindow();
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        Glide.clear(this);
    }

    public void setUserImage(User user) {
        this.user = user;
        if(user !=null){
            icon_url = user.getProfile_image_url();
            if (!TextUtils.isEmpty(icon_url)) {
                if (isAttachedToWindow) {
                    // 防止出现 You cannot start a load for a destroyed activity
                    Glide.with(getContext().getApplicationContext()).load(icon_url).centerCrop().into(this);
                }
            }
        }
    }

    @Override
    public void onClick(View v) {
        getContext().startActivity(UserActivity.newIntent(getContext(),user));
    }
}
