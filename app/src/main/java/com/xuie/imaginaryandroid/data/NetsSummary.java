package com.xuie.imaginaryandroid.data;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.List;

/**
 * Created by xuie on 17-8-16.
 */

public class NetsSummary implements MultiItemEntity {
    public static final int IMG_ONE = 1;
    public static final int IMG_MULTI = 2;

    /**
     * imgextra : [{"imgsrc":"http://cms-bucket.nosdn.127.net/e2ab56d95eb342e9b2d851bb3885158720170817074439.jpeg"},{"imgsrc":"http://cms-bucket.nosdn.127.net/9a36161866174b1186077a94db40ac2120170817074439.jpeg"}]
     * template : normal1
     * skipID : 00AP0001|2271371
     * lmodify : 2017-08-17 08:43:55
     * postid : PHOT25A4B000100A
     * source : 视觉中国
     * title : 探访合肥被扣共享单车存放点 堆积如山
     * mtime : 2017-08-17 08:43:55
     * hasImg : 1
     * topic_background : http://img2.cache.netease.com/m/newsapp/reading/cover1/C1348646712614.jpg
     * digest :
     * photosetID : 00AP0001|2271371
     * boardid : photoview_bbs
     * alias : Top News
     * hasAD : 1
     * imgsrc : http://cms-bucket.nosdn.127.net/9cfbe0dcab3c475e868ff6a270217e2620170817074438.jpeg
     * ptime : 2017-08-17 07:44:48
     * daynum : 17395
     * hasHead : 1
     * order : 1
     * votecount : 30724
     * hasCover : false
     * docid : 9IG74V5H00963VRO_CS1BB7DNbjjikeupdateDoc
     * tname : 头条
     * priority : 354
     * ads : [{"subtitle":"","skipType":"photoset","skipID":"00AO0001|2271377","tag":"photoset","title":"为防暴乱 美国连夜拆除内战将领雕像","imgsrc":"http://cms-bucket.nosdn.127.net/78585eed3e3546598853f9863d148eae20170817083623.jpeg","url":"00AO0001|2271377"},{"subtitle":"","skipType":"photoset","skipID":"00AO0001|2271376","tag":"photoset","title":"迪拜漂浮别墅悄然走红 售价约2100万元","imgsrc":"http://cms-bucket.nosdn.127.net/24e66ad03a664ed1b796905476cbae2f20170817081824.jpeg","url":"00AO0001|2271376"},{"subtitle":"","skipType":"photoset","skipID":"00AP0001|2271378","tag":"photoset","title":"城市下水管道蛙人 给管道\u201c做胃镜\u201d","imgsrc":"http://cms-bucket.nosdn.127.net/fd7ee7e85f1c45b6a290b1272bab9cdc20170817083720.jpeg","url":"00AP0001|2271378"},{"subtitle":"","skipType":"photoset","skipID":"00AN0001|2271365","tag":"photoset","title":"山东烟台武警开展跨区反恐综合演练","imgsrc":"http://cms-bucket.nosdn.127.net/7b83603ecaae4e3d89e5a1528d3fe36020170817025717.jpeg","url":"00AN0001|2271365"},{"subtitle":"","skipType":"photoset","skipID":"00AN0001|2271364","tag":"photoset","title":"西安一家餐馆起火 附近住户被紧急疏散","imgsrc":"http://cms-bucket.nosdn.127.net/0ca22190e7bb495f8e58f9735907d10420170817021601.jpeg","url":"00AN0001|2271364"}]
     * ename : iosnews
     * replyCount : 31907
     * imgsum : 6
     * hasIcon : true
     * skipType : photoset
     * cid : C1348646712614
     */

    private String template;
    private String skipID;
    private String lmodify;
    private String postid;
    private String source;
    private String title;
    private String mtime;
    private int hasImg;
    private String topic_background;
    private String digest;
    private String photosetID;
    private String boardid;
    private String alias;
    private int hasAD;
    private String imgsrc;
    private String ptime;
    private String daynum;
    private int hasHead;
    private int order;
    private int votecount;
    private boolean hasCover;
    private String docid;
    private String tname;
    private int priority;
    private String ename;
    private int replyCount;
    private int imgsum;
    private boolean hasIcon;
    private String skipType;
    private String cid;
    private List<ImgextraBean> imgextra;
    private List<AdsBean> ads;

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public String getSkipID() {
        return skipID;
    }

    public void setSkipID(String skipID) {
        this.skipID = skipID;
    }

    public String getLmodify() {
        return lmodify;
    }

    public void setLmodify(String lmodify) {
        this.lmodify = lmodify;
    }

    public String getPostid() {
        return postid;
    }

    public void setPostid(String postid) {
        this.postid = postid;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMtime() {
        return mtime;
    }

    public void setMtime(String mtime) {
        this.mtime = mtime;
    }

    public int getHasImg() {
        return hasImg;
    }

    public void setHasImg(int hasImg) {
        this.hasImg = hasImg;
    }

    public String getTopic_background() {
        return topic_background;
    }

    public void setTopic_background(String topic_background) {
        this.topic_background = topic_background;
    }

    public String getDigest() {
        return digest;
    }

    public void setDigest(String digest) {
        this.digest = digest;
    }

    public String getPhotosetID() {
        return photosetID;
    }

    public void setPhotosetID(String photosetID) {
        this.photosetID = photosetID;
    }

    public String getBoardid() {
        return boardid;
    }

    public void setBoardid(String boardid) {
        this.boardid = boardid;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public int getHasAD() {
        return hasAD;
    }

    public void setHasAD(int hasAD) {
        this.hasAD = hasAD;
    }

    public String getImgsrc() {
        return imgsrc;
    }

    public void setImgsrc(String imgsrc) {
        this.imgsrc = imgsrc;
    }

    public String getPtime() {
        return ptime;
    }

    public void setPtime(String ptime) {
        this.ptime = ptime;
    }

    public String getDaynum() {
        return daynum;
    }

    public void setDaynum(String daynum) {
        this.daynum = daynum;
    }

    public int getHasHead() {
        return hasHead;
    }

    public void setHasHead(int hasHead) {
        this.hasHead = hasHead;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public int getVotecount() {
        return votecount;
    }

    public void setVotecount(int votecount) {
        this.votecount = votecount;
    }

    public boolean isHasCover() {
        return hasCover;
    }

    public void setHasCover(boolean hasCover) {
        this.hasCover = hasCover;
    }

    public String getDocid() {
        return docid;
    }

    public void setDocid(String docid) {
        this.docid = docid;
    }

    public String getTname() {
        return tname;
    }

    public void setTname(String tname) {
        this.tname = tname;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getEname() {
        return ename;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }

    public int getReplyCount() {
        return replyCount;
    }

    public void setReplyCount(int replyCount) {
        this.replyCount = replyCount;
    }

    public int getImgsum() {
        return imgsum;
    }

    public void setImgsum(int imgsum) {
        this.imgsum = imgsum;
    }

    public boolean isHasIcon() {
        return hasIcon;
    }

    public void setHasIcon(boolean hasIcon) {
        this.hasIcon = hasIcon;
    }

    public String getSkipType() {
        return skipType;
    }

    public void setSkipType(String skipType) {
        this.skipType = skipType;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public List<ImgextraBean> getImgextra() {
        return imgextra;
    }

    public void setImgextra(List<ImgextraBean> imgextra) {
        this.imgextra = imgextra;
    }

    public List<AdsBean> getAds() {
        return ads;
    }

    public void setAds(List<AdsBean> ads) {
        this.ads = ads;
    }

    @Override
    public int getItemType() {
        return getImgextra() != null ? IMG_MULTI : IMG_ONE;
    }

    public static class ImgextraBean {
        /**
         * imgsrc : http://cms-bucket.nosdn.127.net/e2ab56d95eb342e9b2d851bb3885158720170817074439.jpeg
         */

        private String imgsrc;

        public String getImgsrc() {
            return imgsrc;
        }

        public void setImgsrc(String imgsrc) {
            this.imgsrc = imgsrc;
        }
    }

    public static class AdsBean {
        /**
         * subtitle :
         * skipType : photoset
         * skipID : 00AO0001|2271377
         * tag : photoset
         * title : 为防暴乱 美国连夜拆除内战将领雕像
         * imgsrc : http://cms-bucket.nosdn.127.net/78585eed3e3546598853f9863d148eae20170817083623.jpeg
         * url : 00AO0001|2271377
         */

        private String subtitle;
        private String skipType;
        private String skipID;
        private String tag;
        private String title;
        private String imgsrc;
        private String url;

        public String getSubtitle() {
            return subtitle;
        }

        public void setSubtitle(String subtitle) {
            this.subtitle = subtitle;
        }

        public String getSkipType() {
            return skipType;
        }

        public void setSkipType(String skipType) {
            this.skipType = skipType;
        }

        public String getSkipID() {
            return skipID;
        }

        public void setSkipID(String skipID) {
            this.skipID = skipID;
        }

        public String getTag() {
            return tag;
        }

        public void setTag(String tag) {
            this.tag = tag;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getImgsrc() {
            return imgsrc;
        }

        public void setImgsrc(String imgsrc) {
            this.imgsrc = imgsrc;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
