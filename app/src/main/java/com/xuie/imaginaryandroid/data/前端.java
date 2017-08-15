package com.xuie.imaginaryandroid.data;

/**
 * Created by xuie on 17-8-8.
 */

public class 前端 {

    /**
     * _id : 5985387d421aa90ca209c566
     * createdAt : 2017-08-05T11:16:13.188Z
     * desc : 一个轻量级模板引擎，可快速实现数据与ui绑定（数据变动，UI自动变动），快速实现事件绑定和处理，不依赖任何第三方库,仅仅8k。
     * publishedAt : 2017-08-08T11:34:20.37Z
     * source : web
     * type : 前端
     * url : https://github.com/epaii/epii.js
     * used : true
     * who : MrRen
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
