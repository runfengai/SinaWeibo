package com.jarry.weibo.bean;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Jarry 2018/5/2.
 * <p>
 * <p>
 * every weibo info
 */
public class Status implements Serializable {

    private String created_at;
    private long id;
    private String idstr;
    private String text;
    private String source;
    private String thumbnail_pic;
    private String bmiddle_pic;
    private String original_pic;
    private String reposts_count;
    private String comments_count;
    private String attitudes_count;
    private Status retweeted_status;
    private ArrayList<ThumbnailPic> pic_urls;

    private User user;

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setIdstr(String idstr) {
        this.idstr = idstr;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public void setThumbnail_pic(String thumbnail_pic) {
        this.thumbnail_pic = thumbnail_pic;
    }

    public void setBmiddle_pic(String bmiddle_pic) {
        this.bmiddle_pic = bmiddle_pic;
    }

    public void setOriginal_pic(String original_pic) {
        this.original_pic = original_pic;
    }

    public void setReposts_count(String reposts_count) {
        this.reposts_count = reposts_count;
    }

    public void setComments_count(String comments_count) {
        this.comments_count = comments_count;
    }

    public void setAttitudes_count(String attitudes_count) {
        this.attitudes_count = attitudes_count;
    }

    public void setRetweeted_status(Status retweeted_status) {
        this.retweeted_status = retweeted_status;
    }

    public void setPic_urls(ArrayList<ThumbnailPic> pic_urls) {
        this.pic_urls = pic_urls;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getCreated_at() {
        return created_at;
    }

    public long getId() {
        return id;
    }

    public String getIdstr() {
        return idstr;
    }

    public Status getRetweeted_status() {
        return retweeted_status;
    }

    public String getAttitudes_count() {
        return attitudes_count;
    }

    public String getComments_count() {
        return comments_count;
    }

    public String getReposts_count() {
        return reposts_count;
    }

    public String getOriginal_pic() {
        return original_pic;
    }

    public String getBmiddle_pic() {
        return bmiddle_pic;
    }

    public String getThumbnail_pic() {
        return thumbnail_pic;
    }

    public String getSource() {
        return source;
    }

    public String getText() {
        return text;
    }

    public ArrayList<ThumbnailPic> getPic_urls() {
        return pic_urls;
    }

    public User getUser() {
        return user;
    }


    public static class ThumbnailPic implements Serializable {
        private String thumbnail_pic;
        public String localPic = "";

        public String getLargeImg() {
            return thumbnail_pic.replace("thumbnail", "large");
        }

        public String getSmallImg() {
            try {
                return thumbnail_pic.replace("thumbnail", "small");
            } catch (Exception e) {
                return localPic;
            }
        }

        public String getThumbnail_pic() {
            return thumbnail_pic;
        }

        public String getImage() {
            try {
                if (getThumbnail_pic().contains("thumbnail")) {
                    return getThumbnail_pic().replace("thumbnail", "large");
                } else {
                    return getThumbnail_pic().replace("small", "large");
                }
            } catch (Exception e) {
                return localPic == null ? "" : localPic;
            }

        }
    }


    @Override
    public String toString() {
        return "Status{" +
                "created_at='" + created_at + '\'' +
                ", id=" + id +
                ", idstr='" + idstr + '\'' +
                ", text='" + text + '\'' +
                ", source='" + source + '\'' +
                ", thumbnail_pic='" + thumbnail_pic + '\'' +
                ", bmiddle_pic='" + bmiddle_pic + '\'' +
                ", original_pic='" + original_pic + '\'' +
                ", reposts_count='" + reposts_count + '\'' +
                ", comments_count='" + comments_count + '\'' +
                ", attitudes_count='" + attitudes_count + '\'' +
                ", retweeted_status=" + retweeted_status +
                ", pic_urls=" + pic_urls +
                ", user=" + user +
                '}';
    }
}
