package com.zzcn77.android_app_company.Bean;

import java.util.List;

/**
 * Created by 赵磊 on 2017/5/25.
 */

public class IndexBean {


    /**
     * stu : 1
     * res : {"lunbo":[{"id":"6","img_lb":"/Public/uploads/goods/img_lb/2017-05-24/5925302bda5b8.png"},{"id":"5","img_lb":"/Public/uploads/goods/img_lb/2017-05-24/59252ed636682.png"}],"jianjie":{"title":"公司简介","keywords":"监控-SQ366 您的\u201c视界\u201c 由我看护","id":"1","tel":"18638035535","pic":"/Public/uploads/images/logo/592565369acc4.png"},"dongtai":[{"id":"2","title":"SQ366设计婴幼儿监控器 可用手机监测熊孩子","keywords":"SQ366-设计婴幼儿监控器年轻父母们都知道，照顾年龄小却已经能够到处跑的熊孩子简直分分钟就能被逼到抓狂的状态，别说做顿饭，就连吃顿完整饭的时间都要碰运气。有时候，你以为熊孩子睡着了，或者乖乖地坐在屋里看电视、玩玩具，其实他们早就偷偷溜出了房间、甚至已经爬到了阳台上。在这一点上，房子越大，苦恼越深。","imgurl":"/Public/uploads/news/2017-05-23/59239848692fe.png"},{"id":"18","title":"SQ366网络延长器电梯高清网络视频监控应用方案","keywords":"智慧城市包含智能电梯，更智能的电梯需要更大流量的网络信号来支持智慧化，这些信号包括监控信号数据流多媒体信号数据流等。      随着现代高层楼宇的开发建设，电梯成为一个必不可少的工具，在日常生活中扮演着越来越重要的作用。","imgurl":"/Public/uploads/news/2017-05-23/59239b3b42695.png"},{"id":"17","title":"SQ366:恶劣天气高发期 监控如何防水防雷？","keywords":"恶劣天气的不确定性、突发性、毁灭性往往让安防监控设备措手不及，如何让安防监控设备在极端天气下保持良好状态，提高整个城市的安防监控水平，更好服务于平安城市?","imgurl":"/Public/uploads/news/2017-05-23/59239a1542b35.png"},{"id":"16","title":"SQ366：地铁视频监控新概念","keywords":"SQ366-地铁作为现代化的城市交通工具，已有一百多年的历史，具有车辆运行速度快、车次多、旅客运送量大、方便舒适等特点。世人皆知地铁造价不菲，但这丝毫不影响地铁在中国的高速发展，现在城市里没个地铁都不好意思说自己是现代化城市。","imgurl":"/Public/uploads/news/2017-05-23/592399779ad4c.png"},{"id":"11","title":"SQ366推出智能睡眠监控器 Sleeptracker，可以跟踪两个人睡眠质量","keywords":"SQ366-Sleeptracker，可以跟踪两个人睡眠质量","imgurl":"/Public/uploads/news/2017-05-23/59239802b4a14.jpg"}],"huodong":[{"id":"3","title":"bguj","keywords":"hv","zhe":"9.9","imgurl":"/Public/uploads/cuxiao/2017-05-24/59255eb7e8309.png"},{"id":"4","title":"xngf","keywords":"dsf","zhe":"8.0","imgurl":"/Public/uploads/cuxiao/2017-05-24/5925580913170.png"},{"id":"1","title":"xngf","keywords":"7896524123","zhe":"9.9","imgurl":"/Public/uploads/cuxiao/2017-05-24/59255e2962d9f.png"}]}
     */

    private String stu;
    private ResBean res;

    public String getStu() {
        return stu;
    }

    public void setStu(String stu) {
        this.stu = stu;
    }

    public ResBean getRes() {
        return res;
    }

    public void setRes(ResBean res) {
        this.res = res;
    }

    public static class ResBean {
        /**
         * lunbo : [{"id":"6","img_lb":"/Public/uploads/goods/img_lb/2017-05-24/5925302bda5b8.png"},{"id":"5","img_lb":"/Public/uploads/goods/img_lb/2017-05-24/59252ed636682.png"}]
         * jianjie : {"title":"公司简介","keywords":"监控-SQ366 您的\u201c视界\u201c 由我看护","id":"1","tel":"18638035535","pic":"/Public/uploads/images/logo/592565369acc4.png"}
         * dongtai : [{"id":"2","title":"SQ366设计婴幼儿监控器 可用手机监测熊孩子","keywords":"SQ366-设计婴幼儿监控器年轻父母们都知道，照顾年龄小却已经能够到处跑的熊孩子简直分分钟就能被逼到抓狂的状态，别说做顿饭，就连吃顿完整饭的时间都要碰运气。有时候，你以为熊孩子睡着了，或者乖乖地坐在屋里看电视、玩玩具，其实他们早就偷偷溜出了房间、甚至已经爬到了阳台上。在这一点上，房子越大，苦恼越深。","imgurl":"/Public/uploads/news/2017-05-23/59239848692fe.png"},{"id":"18","title":"SQ366网络延长器电梯高清网络视频监控应用方案","keywords":"智慧城市包含智能电梯，更智能的电梯需要更大流量的网络信号来支持智慧化，这些信号包括监控信号数据流多媒体信号数据流等。      随着现代高层楼宇的开发建设，电梯成为一个必不可少的工具，在日常生活中扮演着越来越重要的作用。","imgurl":"/Public/uploads/news/2017-05-23/59239b3b42695.png"},{"id":"17","title":"SQ366:恶劣天气高发期 监控如何防水防雷？","keywords":"恶劣天气的不确定性、突发性、毁灭性往往让安防监控设备措手不及，如何让安防监控设备在极端天气下保持良好状态，提高整个城市的安防监控水平，更好服务于平安城市?","imgurl":"/Public/uploads/news/2017-05-23/59239a1542b35.png"},{"id":"16","title":"SQ366：地铁视频监控新概念","keywords":"SQ366-地铁作为现代化的城市交通工具，已有一百多年的历史，具有车辆运行速度快、车次多、旅客运送量大、方便舒适等特点。世人皆知地铁造价不菲，但这丝毫不影响地铁在中国的高速发展，现在城市里没个地铁都不好意思说自己是现代化城市。","imgurl":"/Public/uploads/news/2017-05-23/592399779ad4c.png"},{"id":"11","title":"SQ366推出智能睡眠监控器 Sleeptracker，可以跟踪两个人睡眠质量","keywords":"SQ366-Sleeptracker，可以跟踪两个人睡眠质量","imgurl":"/Public/uploads/news/2017-05-23/59239802b4a14.jpg"}]
         * huodong : [{"id":"3","title":"bguj","keywords":"hv","zhe":"9.9","imgurl":"/Public/uploads/cuxiao/2017-05-24/59255eb7e8309.png"},{"id":"4","title":"xngf","keywords":"dsf","zhe":"8.0","imgurl":"/Public/uploads/cuxiao/2017-05-24/5925580913170.png"},{"id":"1","title":"xngf","keywords":"7896524123","zhe":"9.9","imgurl":"/Public/uploads/cuxiao/2017-05-24/59255e2962d9f.png"}]
         */

        private JianjieBean jianjie;
        private List<LunboBean> lunbo;
        private List<DongtaiBean> dongtai;
        private List<HuodongBean> huodong;

        public JianjieBean getJianjie() {
            return jianjie;
        }

        public void setJianjie(JianjieBean jianjie) {
            this.jianjie = jianjie;
        }

        public List<LunboBean> getLunbo() {
            return lunbo;
        }

        public void setLunbo(List<LunboBean> lunbo) {
            this.lunbo = lunbo;
        }

        public List<DongtaiBean> getDongtai() {
            return dongtai;
        }

        public void setDongtai(List<DongtaiBean> dongtai) {
            this.dongtai = dongtai;
        }

        public List<HuodongBean> getHuodong() {
            return huodong;
        }

        public void setHuodong(List<HuodongBean> huodong) {
            this.huodong = huodong;
        }

        public static class JianjieBean {
            /**
             * title : 公司简介
             * keywords : 监控-SQ366 您的“视界“ 由我看护
             * id : 1
             * tel : 18638035535
             * pic : /Public/uploads/images/logo/592565369acc4.png
             */

            private String title;
            private String keywords;
            private String id;
            private String tel;
            private String pic;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getKeywords() {
                return keywords;
            }

            public void setKeywords(String keywords) {
                this.keywords = keywords;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getTel() {
                return tel;
            }

            public void setTel(String tel) {
                this.tel = tel;
            }

            public String getPic() {
                return pic;
            }

            public void setPic(String pic) {
                this.pic = pic;
            }
        }

        public static class LunboBean {
            /**
             * id : 6
             * img_lb : /Public/uploads/goods/img_lb/2017-05-24/5925302bda5b8.png
             */

            private String id;
            private String img_lb;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getImg_lb() {
                return img_lb;
            }

            public void setImg_lb(String img_lb) {
                this.img_lb = img_lb;
            }
        }

        public static class DongtaiBean {
            /**
             * id : 2
             * title : SQ366设计婴幼儿监控器 可用手机监测熊孩子
             * keywords : SQ366-设计婴幼儿监控器年轻父母们都知道，照顾年龄小却已经能够到处跑的熊孩子简直分分钟就能被逼到抓狂的状态，别说做顿饭，就连吃顿完整饭的时间都要碰运气。有时候，你以为熊孩子睡着了，或者乖乖地坐在屋里看电视、玩玩具，其实他们早就偷偷溜出了房间、甚至已经爬到了阳台上。在这一点上，房子越大，苦恼越深。
             * imgurl : /Public/uploads/news/2017-05-23/59239848692fe.png
             */

            private String id;
            private String title;
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

        public static class HuodongBean {
            /**
             * id : 3
             * title : bguj
             * keywords : hv
             * zhe : 9.9
             * imgurl : /Public/uploads/cuxiao/2017-05-24/59255eb7e8309.png
             */

            private String id;
            private String title;
            private String keywords;
            private String zhe;
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

            public String getKeywords() {
                return keywords;
            }

            public void setKeywords(String keywords) {
                this.keywords = keywords;
            }

            public String getZhe() {
                return zhe;
            }

            public void setZhe(String zhe) {
                this.zhe = zhe;
            }

            public String getImgurl() {
                return imgurl;
            }

            public void setImgurl(String imgurl) {
                this.imgurl = imgurl;
            }
        }
    }
}
