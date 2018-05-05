package com.jarry.weibo.ui.presenter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.jarry.weibo.MyApp;
import com.jarry.weibo.bean.Status;
import com.jarry.weibo.bean.User;
import com.jarry.weibo.info.Constants;
import com.jarry.weibo.util.DataUtil;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.jarry.weibo.R;
import com.jarry.weibo.ui.adapter.WeiBoPhotoAdapter;
import com.jarry.weibo.ui.view.ISendView;
import com.sina.weibo.sdk.auth.AccessTokenKeeper;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.iwf.photopicker.PhotoPicker;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Jarry 2016/8/3.
 * <p>
 * <p>
 * Send Img with text
 */
public class SendPresenter extends BasePresenter<ISendView> {

    private Context context;
    private ISendView sendView;
    private List<String> photos = new ArrayList<>();
    private WeiBoPhotoAdapter photoAdapter;

    public SendPresenter(Context context) {
        this.context = context;
    }

    public void pickPhoto() {
        sendView = getView();
        photoAdapter = new WeiBoPhotoAdapter(context, photos);
        sendView.getPhotoGrid().setAdapter(photoAdapter);
        photos.clear();
        photoAdapter.paths.clear();
        sendView.permissionSetting();
    }

    //photo pick
    public void photoPick() {
        PhotoPicker.builder()
                .setPhotoCount(9)
                .setShowCamera(true)
                .setShowGif(true)
                .setPreviewEnabled(false)
                .start((Activity) context, PhotoPicker.REQUEST_CODE);
    }

    //更新数据适配器
    public void loadAdapter(ArrayList<String> paths) {
        if (photos == null) {
            photos = new ArrayList<>();
        }
        System.out.println("~~~" + paths.size());
        photos.clear();
        photos.addAll(paths);
        photoAdapter.notifyDataSetChanged();
    }

    String domainUrl = " http://www.baidu.com ";

    public void sendWeiBo(String weiboText) {
        //本地模拟数据
        //设置微博刷新
        //自己拼接一个Bean
        Status status = new Status();
        status.setText(weiboText);
        User user = getUserInfoFromDB();
        if (user == null) {
            user = User.getLoginUser();
            user.setScreen_name("张三");
            user.setName("张三");
            user.setProfile_image_url("https://timgsa.baidu.com/timg?image\u0026quality\u003d80\u0026size\u003db10000_10000\u0026sec\u003d1525236570\u0026di\u003de3abeecb6e1208c7b6290adb5ee8f99f\u0026src\u003dhttp://img.taopic.com/uploads/allimg/110314/1517-110314134R642.jpg");
        }
        status.setSource("\u003ca href\u003d\"http://www.baidu.com\" rel\u003d\"nofollow\"\u003eiPhone X\u003c/a\u003e");
        status.setUser(user);


        status.setCreated_at(DataUtil.convertGMTToLoacale(new Date()));
        status.setAttitudes_count("0");
        status.setComments_count("0");
        status.setReposts_count("0");
        status.setId(3980654229L);

        ArrayList<Status.ThumbnailPic> pic_urls = null;
        if (getPathFromAdapter() != null && getPathFromAdapter().size() > 0) {
            pic_urls = new ArrayList<>();
            for (String path : photos) {
                Status.ThumbnailPic thumbnailPic = new Status.ThumbnailPic();
                thumbnailPic.localPic = path;
                pic_urls.add(thumbnailPic);
            }
        }
        status.setPic_urls(pic_urls);

        Intent intent = new Intent();
        intent.putExtra("weibo", status);
        ((Activity) context).setResult(RESULT_OK, intent);
        getView().finishAndToast();
        //处理下安全域名
//        status += domainUrl;
//        if (photoAdapter != null && getPathFromAdapter().size() > 0) {
//            weiBoApi.sendWeiBoWithImg(getTokenStr(), getTextStr(status), getImage())
//                    .subscribeOn(Schedulers.io())
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .subscribe(Status -> {
//                        getView().finishAndToast();
//                    }, this::loadError);
//        } else {
//            Oauth2AccessToken token = readToken(context);
//            weiBoApi.shareWeiBo(getSendMap(token.getToken(), status))
//                    .subscribeOn(Schedulers.io())
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .subscribe(ShareBean -> {
//                        getView().finishAndToast();
//                    }, this::loadError);
//        }
    }

    private User getUserInfoFromDB() {
        ArrayList<User> query = MyApp.mDb.query(User.class);
        if (query.size() > 0) {
            return query.get(0);
        } else {
            return null;
        }
    }

    private void loadError(Throwable throwable) {
        throwable.printStackTrace();
        Toast.makeText(context, R.string.load_error, Toast.LENGTH_SHORT).show();
    }

    // get request params
    private RequestBody getTokenStr() {
        Oauth2AccessToken token = readToken(context);
        RequestBody accessBody = RequestBody.create(
                MediaType.parse("text/plain"),
                token.getToken());
        return accessBody;
    }

    private RequestBody getTextStr(String text) {
        RequestBody contentBody = RequestBody.create(
                MediaType.parse("text/plain"), text);
        return contentBody;
    }

    /**
     * 由于接口限制，只能上传一张图片
     *
     * @return
     */
    private RequestBody getImage() {
        List<String> stringList = getPathFromAdapter();
        File file = new File(stringList.get(0));
        RequestBody imageBody = RequestBody.create(
                MediaType.parse("multipart/form-data"),
                file);
        return imageBody;
    }

    private Map<String, Object> getSendMap(String token, String status) {
        Map<String, Object> map = new HashMap<>();
        map.put("access_token", token);
        map.put("status", status);
        return map;
    }

    private Oauth2AccessToken readToken(Context context) {
        return AccessTokenKeeper.readAccessToken(context);
    }

    private List<String> getPathFromAdapter() {
        List<String> paths = new ArrayList<>();
        if (photoAdapter != null) {
            List<Object> objects = photoAdapter.paths;
            for (Object object : objects) {
                if (object instanceof String) {
                    paths.add((String) object);
                }
            }
            return paths;
        } else {
            return null;
        }
    }

}
