package xuk.imaginary.data;

import java.util.List;

/**
 * @author xuie
 * @date 17-8-15
 */
public class GankBean {

    /**
     * category : ["Android","瞎推荐","iOS","休息视频","福利","前端"]
     * error : false
     * results : {"Android":[{"_id":"59818615421aa90ca209c547","createdAt":"2017-08-02T15:58:13.659Z","desc":"垃圾回收算法与 JVM 垃圾回收器综述","publishedAt":"2017-08-15T13:32:36.998Z","source":"chrome","type":"BaseBean","url":"https://zhuanlan.zhihu.com/p/28258571","used":true,"who":"王下邀月熊"},{"_id":"59848574421aa90ca209c564","createdAt":"2017-08-04T22:32:20.718Z","desc":"腾讯开源, 在Android设备上实现可信的指纹认证","images":["http://img.gank.io/e8529e62-606c-44f3-b10d-963d58a2ef83"],"publishedAt":"2017-08-15T13:32:36.998Z","source":"web","type":"BaseBean","url":"https://github.com/Tencent/soter","used":true,"who":"ShineYang"},{"_id":"598bdc54421aa90ca209c589","createdAt":"2017-08-10T12:08:52.490Z","desc":"BaseBean-自定义应用选择器","images":["http://img.gank.io/6d5aedbc-f87f-4edc-98d2-3c55af699c1a"],"publishedAt":"2017-08-15T13:32:36.998Z","source":"web","type":"BaseBean","url":"http://www.jianshu.com/p/3f65576f89b7","used":true,"who":"Julian Chu"}],"iOS":[{"_id":"598c1db2421aa90ca209c58b","createdAt":"2017-08-10T16:47:46.44Z","desc":"用户体验极佳的干货集中营 iOS App","images":["http://img.gank.io/218bcc4e-8d85-479d-b8ce-0d674eb38ef8"],"publishedAt":"2017-08-15T13:32:36.998Z","source":"web","type":"iOS","url":"https://github.com/yeziahehe/Gank","used":true,"who":"Fan Ye"}],"休息视频":[{"_id":"5991b9d1421aa96729c57245","createdAt":"2017-08-14T22:55:13.926Z","desc":"搞笑GIF锦集101期 ：好喜欢这种污污的女生！","publishedAt":"2017-08-15T13:32:36.998Z","source":"chrome","type":"休息视频","url":"http://www.bilibili.com/video/av13356763/","used":true,"who":"LHF"}],"前端":[{"_id":"5992691f421aa9672f354dbf","createdAt":"2017-08-15T11:23:11.922Z","desc":"URL 友好的随机字符串生成器。","images":["http://img.gank.io/e0583970-5500-4270-b0b9-a1151943f452"],"publishedAt":"2017-08-15T13:32:36.998Z","source":"chrome","type":"前端","url":"https://github.com/ai/nanoid","used":true,"who":"代码家"}],"瞎推荐":[{"_id":"5982c768421aa97de5c7ca1a","createdAt":"2017-08-03T14:49:12.819Z","desc":"今天，距离我从腾讯离职刚好130天了,也许会继续找个朝九晚五的工作，也许会告别职场、换种活法。","publishedAt":"2017-08-15T13:32:36.998Z","source":"web","type":"瞎推荐","url":"https://community.clouderwork.com/article/view/597fec5795d46.html","used":true,"who":null},{"_id":"598bc591421aa97de5c7ca51","createdAt":"2017-08-10T10:31:45.55Z","desc":"产品游戏化设计","publishedAt":"2017-08-15T13:32:36.998Z","source":"web","type":"瞎推荐","url":"http://www.jianshu.com/p/e1c626bde294","used":true,"who":null}],"福利":[{"_id":"599237dd421aa96729c57246","createdAt":"2017-08-15T07:53:01.962Z","desc":"8-15","publishedAt":"2017-08-15T13:32:36.998Z","source":"chrome","type":"福利","url":"https://ws1.sinaimg.cn/large/610dc034ly1fik2q1k3noj20u00u07wh.jpg","used":true,"who":"daimajia"}]}
     */

    private boolean error;
    private ResultsBean results;
    private List<String> category;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public ResultsBean getResults() {
        return results;
    }

    public void setResults(ResultsBean results) {
        this.results = results;
    }

    public List<String> getCategory() {
        return category;
    }

    public void setCategory(List<String> category) {
        this.category = category;
    }

    public static class ResultsBean {
        private List<BaseBean> Android;
        private List<BaseBean> iOS;
        private List<BaseBean> 休息视频;
        private List<BaseBean> 前端;
        private List<BaseBean> 瞎推荐;
        private List<BaseBean> 福利;
        private List<BaseBean> 拓展资源;

        public List<BaseBean> getAndroid() {
            return Android;
        }

        public void setAndroid(List<BaseBean> android) {
            Android = android;
        }

        public List<BaseBean> getiOS() {
            return iOS;
        }

        public void setiOS(List<BaseBean> iOS) {
            this.iOS = iOS;
        }

        public List<BaseBean> get休息视频() {
            return 休息视频;
        }

        public void set休息视频(List<BaseBean> 休息视频) {
            this.休息视频 = 休息视频;
        }

        public List<BaseBean> get前端() {
            return 前端;
        }

        public void set前端(List<BaseBean> 前端) {
            this.前端 = 前端;
        }

        public List<BaseBean> get瞎推荐() {
            return 瞎推荐;
        }

        public void set瞎推荐(List<BaseBean> 瞎推荐) {
            this.瞎推荐 = 瞎推荐;
        }

        public List<BaseBean> get福利() {
            return 福利;
        }

        public void set福利(List<BaseBean> 福利) {
            this.福利 = 福利;
        }

        public List<BaseBean> get拓展资源() {
            return 拓展资源;
        }

        public void set拓展资源(List<BaseBean> 拓展资源) {
            this.拓展资源 = 拓展资源;
        }

        @Override
        public String toString() {
            return "ResultsBean{" +
                    "Android=" + Android +
                    ", iOS=" + iOS +
                    ", 休息视频=" + 休息视频 +
                    ", 前端=" + 前端 +
                    ", 瞎推荐=" + 瞎推荐 +
                    ", 福利=" + 福利 +
                    ", 拓展资源=" + 拓展资源 +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "GankBean{" +
                "error=" + error +
                ", results=" + results +
                ", category=" + category +
                '}';
    }
}
