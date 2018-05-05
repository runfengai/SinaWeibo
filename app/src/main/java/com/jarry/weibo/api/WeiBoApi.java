package com.jarry.weibo.api;

import com.jarry.weibo.bean.CommentsTimeLine;
import com.jarry.weibo.bean.DetailStatus;
import com.jarry.weibo.bean.Favorites;
import com.jarry.weibo.bean.FriendShips;
import com.jarry.weibo.bean.FriendsTimeLine;
import com.jarry.weibo.bean.MentionComment;
import com.jarry.weibo.bean.PostComments;
import com.jarry.weibo.bean.SearchBean;
import com.jarry.weibo.bean.ShareBean;
import com.jarry.weibo.bean.Status;
import com.jarry.weibo.bean.User;

import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * Created by Jarry 2018/5/2.
 * <p>
 * <p>
 * get WeiBo with retrofit
 */
public interface WeiBoApi {

    @GET("users/show.json")
    Observable<User> getUserInfo(@QueryMap Map<String, String> params);

    @GET("statuses/friends_timeline.json")
    Observable<FriendsTimeLine> getFriendsTimeLine(@QueryMap Map<String, Object> params);

    @GET("statuses/show.json")
    Observable<DetailStatus> getDetail(@QueryMap Map<String, Object> params);

    @GET("search/topics.json")
    Observable<SearchBean> searchTopic(@QueryMap Map<String, Object> params);

    @GET("comments/show.json")
    Observable<CommentsTimeLine> getCommentsById(@QueryMap Map<String, Object> params);

    @GET("statuses/mentions.json")
    Observable<FriendsTimeLine> getMessageAtWeiBo(@QueryMap Map<String, Object> params);

    @GET("comments/mentions.json")
    Observable<MentionComment> getMessageAtComment(@QueryMap Map<String, Object> params);

    @GET("comments/to_me.json")
    Observable<MentionComment> getMessageGetComment(@QueryMap Map<String, Object> params);

    @GET("comments/by_me.json")
    Observable<MentionComment> getMessageSendComment(@QueryMap Map<String, Object> params);

    @GET("friendships/friends.json")
    Observable<FriendShips> getFriendById(@QueryMap Map<String, Object> params);

    @GET("friendships/followers.json")
    Observable<FriendShips> getFollowerById(@QueryMap Map<String, Object> params);

    @GET("friendships/friends/bilateral.json")
    Observable<FriendShips> getBilateralById(@QueryMap Map<String, Object> params);

    @GET("favorites.json")
    Observable<Favorites> getFavorites(@QueryMap Map<String, Object> params);

    @GET("statuses/user_timeline.json")
    Observable<FriendsTimeLine> getUserWeiBoTimeLine(@QueryMap Map<String, Object> params);

    @FormUrlEncoded
    @POST("comments/create.json")
    Observable<PostComments> setComment(@FieldMap Map<String, Object> params);

    @FormUrlEncoded
    @POST("favorites/create.json")
    Observable<PostComments> setCollect(@FieldMap Map<String, Object> params);

    @FormUrlEncoded
    @POST("favorites/destroy.json")
    Observable<PostComments> setCancelCollect(@FieldMap Map<String, Object> params);

    //    http://api.t.sina.com.cn/
    @FormUrlEncoded
    @POST("statuses/repost.json")
    Observable<PostComments> setRepost(@FieldMap Map<String, Object> params);

    @FormUrlEncoded
    @POST("comments/reply.json")
    Observable<PostComments> setCommentToReply(@FieldMap Map<String, Object> params);

    @FormUrlEncoded
    @POST("attitudes/create.json")
    Observable<PostComments> setLike(@FieldMap Map<String, Object> params);

    @Multipart
    @POST("statuses/upload.json")
    Observable<Status> sendWeiBoWithImg(
            @Part("access_token") RequestBody accessToken,
            @Part("status") RequestBody context,
            @Part("pic\";filename=\"file") RequestBody requestBody);

    @FormUrlEncoded
    @POST("statuses/update.json")
    Observable<Status> sendWeiBoWithText(@FieldMap Map<String, Object> params);
    @FormUrlEncoded
    @POST("statuses/share.json")
    Observable<ShareBean> shareWeiBo(@FieldMap Map<String, Object> params);
}
