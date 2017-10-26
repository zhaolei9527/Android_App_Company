package com.yulian.platform.Bean;

import java.util.List;

/**
 * Created by 赵磊 on 2017/5/25.
 */

public class NewsBean {

    /**
     * stu : 1
     * res : [{"id":"2","title":"SQ366设计婴幼儿监控器 可用手机监测熊孩子","add_time":"1494057064","keywords":"SQ366-设计婴幼儿监控器年轻父母们都知道，照顾年龄小却已经能够到处跑的熊孩子简直分分钟就能被逼到抓狂的状态，别说做顿饭，就连吃顿完整饭的时间都要碰运气。有时候，你以为熊孩子睡着了，或者乖乖地坐在屋里看电视、玩玩具，其实他们早就偷偷溜出了房间、甚至已经爬到了阳台上。在这一点上，房子越大，苦恼越深。","imgurl":"/Public/uploads/news/2017-05-23/59239848692fe.png"},{"id":"18","title":"SQ366网络延长器电梯高清网络视频监控应用方案","add_time":"1495505452","keywords":"智慧城市包含智能电梯，更智能的电梯需要更大流量的网络信号来支持智慧化，这些信号包括监控信号数据流多媒体信号数据流等。      随着现代高层楼宇的开发建设，电梯成为一个必不可少的工具，在日常生活中扮演着越来越重要的作用。","imgurl":"/Public/uploads/news/2017-05-23/59239b3b42695.png"},{"id":"17","title":"SQ366:恶劣天气高发期 监控如何防水防雷？","add_time":"1495505418","keywords":"恶劣天气的不确定性、突发性、毁灭性往往让安防监控设备措手不及，如何让安防监控设备在极端天气下保持良好状态，提高整个城市的安防监控水平，更好服务于平安城市?","imgurl":"/Public/uploads/news/2017-05-23/59239a1542b35.png"},{"id":"16","title":"SQ366：地铁视频监控新概念","add_time":"1495505099","keywords":"SQ366-地铁作为现代化的城市交通工具，已有一百多年的历史，具有车辆运行速度快、车次多、旅客运送量大、方便舒适等特点。世人皆知地铁造价不菲，但这丝毫不影响地铁在中国的高速发展，现在城市里没个地铁都不好意思说自己是现代化城市。","imgurl":"/Public/uploads/news/2017-05-23/592399779ad4c.png"},{"id":"11","title":"SQ366推出智能睡眠监控器 Sleeptracker，可以跟踪两个人睡眠质量","add_time":"1494570378","keywords":"SQ366-Sleeptracker，可以跟踪两个人睡眠质量","imgurl":"/Public/uploads/news/2017-05-23/59239802b4a14.jpg"}]
     */

    private String stu;
    private List<ResBean> res;

    public String getStu() {
        return stu;
    }

    public void setStu(String stu) {
        this.stu = stu;
    }

    public List<ResBean> getRes() {
        return res;
    }

    public void setRes(List<ResBean> res) {
        this.res = res;
    }

    public static class ResBean {
        /**
         * id : 2
         * title : SQ366设计婴幼儿监控器 可用手机监测熊孩子
         * add_time : 1494057064
         * keywords : SQ366-设计婴幼儿监控器年轻父母们都知道，照顾年龄小却已经能够到处跑的熊孩子简直分分钟就能被逼到抓狂的状态，别说做顿饭，就连吃顿完整饭的时间都要碰运气。有时候，你以为熊孩子睡着了，或者乖乖地坐在屋里看电视、玩玩具，其实他们早就偷偷溜出了房间、甚至已经爬到了阳台上。在这一点上，房子越大，苦恼越深。
         * imgurl : /Public/uploads/news/2017-05-23/59239848692fe.png
         */

        private String id;
        private String title;
        private String add_time;
        private String keywords;
        private String imgurl;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getAdd_time() {
            return add_time;
        }

        public void setAdd_time(String add_time) {
            this.add_time = add_time;
        }

        public String getKeywords() {
            return keywords;
        }

        public void setKeywords(String keywords) {
            this.keywords = keywords;
        }

        public String getImgurl() {
            return imgurl;
        }

        public void setImgurl(String imgurl) {
            this.imgurl = imgurl;
        }
    }
}
