package com.jarry.weibo.ui.view;

import com.jarry.weibo.bean.DetailStatus;

/**
 * Created by Jarry 2016/8/2.
 */
public interface IWeiBoDetailView {
    void finishAndToast();

    void setDetailStatus(DetailStatus detailStatus);
}
