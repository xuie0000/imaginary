package com.xuie.imaginaryandroid.data;

/**
 * Created by xuie on 17-8-15.
 */
public class 瞎推荐Bean {
    /**
     * _id : 5982c768421aa97de5c7ca1a
     * createdAt : 2017-08-03T14:49:12.819Z
     * desc : 今天，距离我从腾讯离职刚好130天了,也许会继续找个朝九晚五的工作，也许会告别职场、换种活法。
     * publishedAt : 2017-08-15T13:32:36.998Z
     * source : web
     * type : 瞎推荐
     * url : https://community.clouderwork.com/article/view/597fec5795d46.html
     * used : true
     * who : null
     */

    private String _id;
    private String createdAt;
    private String desc;
    private String publishedAt;
    private String source;
    private String type;
    private String url;
    private boolean used;
    private Object who;

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

    public Object getWho() {
        return who;
    }

    public void setWho(Object who) {
        this.who = who;
    }

    @Override
    public String toString() {
        return "瞎推荐Bean{" +
                "_id='" + _id + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", desc='" + desc + '\'' +
                ", publishedAt='" + publishedAt + '\'' +
                ", source='" + source + '\'' +
                ", type='" + type + '\'' +
                ", url='" + url + '\'' +
                ", used=" + used +
                ", who=" + who +
                '}';
    }
}
