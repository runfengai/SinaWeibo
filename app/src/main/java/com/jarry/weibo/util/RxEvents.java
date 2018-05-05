package com.jarry.weibo.util;


import com.jarry.weibo.bean.Status;

/**
 * Created by Jarry 2018/5/2.
 * <p>
 * <p>
 * RxBus异步响应事件类型
 */
public class RxEvents {

    /**
     * 返回刷新点击事件
     */
    public static class UpRefreshClick {
    }

    public static class AddedWeibo {
        public Status status;

        public AddedWeibo(Status status) {
            this.status = status;
        }
    }

    /**
     * 微博点赞
     */
    public static class WeiBoSetLike {

        private boolean isLike;
        private String id;

        public WeiBoSetLike(boolean isLike, String id) {
            this.isLike = isLike;
            this.id = id;
        }

        public String getId() {
            return id;
        }

        public boolean isLike() {
            return isLike;
        }
    }

}
