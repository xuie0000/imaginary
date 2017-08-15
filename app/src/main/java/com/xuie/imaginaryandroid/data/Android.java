package com.xuie.imaginaryandroid.data;

/**
 * Created by xuie on 17-8-8.
 */

public class Android {

    /**
     * _id : 5985bdb6421aa97de5c7ca30
     * createdAt : 2017-08-05T20:44:38.849Z
     * desc : 由于RxJava发布了一个订阅后，由于没有及时取消，会导致内存泄露，本文介绍Rxlifecycle机制
     * publishedAt : 2017-08-08T11:34:20.37Z
     * source : web
     * type : Android
     * url : http://url.cn/4Es2B0l
     * used : true
     * who : Tamic (码小白)
     */

    private String _id;
    private String createdAt;
    private String desc;
    private String publishedAt;
    private String source;
    private String type;
    private String url;
    private boolean used;
    private String who;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    public String getWho() {
        return who;
    }

    public void setWho(String who) {
        this.who = who;
    }
}
