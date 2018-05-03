package com.jarry.weibo.ui.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.jarry.weibo.R;
import com.jarry.weibo.ui.adapter.ViewPagerFgAdapter;
import com.jarry.weibo.ui.base.MVPBaseActivity;
import com.jarry.weibo.ui.base.MVPBaseFragment;
import com.jarry.weibo.ui.fragment.MessageFragment;
import com.jarry.weibo.ui.presenter.MessagePresenter;
import com.jarry.weibo.ui.view.IMessageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by Jarry 2016/8/11.
 *
 *
 * Message of Activity
 */
public class MessageActivity extends MVPBaseActivity<IMessageView,MessagePresenter> implements IMessageView {

    private List<MVPBaseFragment> fragmentList;

    @Bind(R.id.message_tabLayout)
    TabLayout tabLayout;
    @Bind(R.id.message_viewPager)
    ViewPager mViewPager;

    @Override
    protected MessagePresenter createPresenter() {
        return new MessagePresenter(this);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_message;
    }

    @Override
    public boolean canBack() {
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initTabView();
    }

    //初始化Tab滑动
    public void initTabView(){

        fragmentList = new ArrayList<>();
        fragmentList.add(MessageFragment.newInstance("at_weibo"));
        fragmentList.add(MessageFragment.newInstance("at_comment"));
        fragmentList.add(MessageFragment.newInstance("get_comment"));
        fragmentList.add(MessageFragment.newInstance("send_comment"));

        mViewPager.setAdapter(new ViewPagerFgAdapter(getSupportFragmentManager(),fragmentList,"Message"));//给ViewPager设置适配器
        tabLayout.setupWithViewPager(mViewPager);//将TabLayout和ViewPager关联起来
    }
}
