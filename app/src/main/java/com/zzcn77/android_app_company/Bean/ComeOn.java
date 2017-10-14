package com.zzcn77.android_app_company.Bean;

import java.util.List;

/**
 * Created by 赵磊 on 2017/10/13.
 */

public class ComeOn {

    /**
     * stu : 1
     * res : {"news":[{"id":"38","title":"ACaaS: How it\u2019s influenced by IoT","add_time":"1504835917","keywords":"ACaaS: How it\u2019s influenced by IoT","imgurl":"/Public/uploads/news/2017-09-08/59b1fa02c1829.jpg"},{"id":"36","title":"5 questions to ask before selecting face recognition solution","add_time":"1503569901","keywords":"5 questions to ask before selecting face recognition solution","imgurl":"/Public/uploads/news/2017-08-24/599ea872efe58.jpg"},{"id":"37","title":"Detecting traffic accidents with machine learning","add_time":"1504170638","keywords":"Detecting traffic accidents with machine learning","imgurl":"/Public/uploads/news/2017-08-31/59a7d41ab8067.jpg"},{"id":"35","title":"Why Digital Barriers limited its free \u2018missing-person solution\u2019 to young people","add_time":"1502507960","keywords":"Why Digital Barriers limited its free \u2018missing-person solution\u2019 to young people","imgurl":"/Public/uploads/news/2017-08-12/598e74280d570.jpg"},{"id":"33","title":"Face recognition is for known threats. This technology detects the unknown!","add_time":"1498559509","keywords":"Face recognition is for known threats. This technology detects the unknown!","imgurl":"/Public/uploads/news/2017-07-27/5979955c41559.jpg"},{"id":"2","title":"ALPR: a new philosophy of best practices","add_time":"1494057064","keywords":"ALPR: a new philosophy of best practices","imgurl":"/Public/uploads/news/2017-07-27/597997401a272.jpg"},{"id":"34","title":"IFSEC International","add_time":"1501141974","keywords":"Thank you to everyone that attended IFSEC International 2017","imgurl":"/Public/uploads/news/2017-07-27/59799c6f2a26c.jpg"},{"id":"39","title":"测试动态","add_time":"1507772459","keywords":"测试动态","imgurl":"/Public/uploads/news/2017-10-12/59dec84aabe00.jpg"}],"shanghu":[{"id":"10001","name":"郑州七七网络科技有限公司","addtime":"1507703627","code":"11233","point":"0","hid":"2","lid":"2","daihao":"BX11233","hangye":"科技互联网"},{"id":"10000","name":"宇联APP科技有限公司","addtime":"1507617698","code":"10000","point":"0","hid":"2","lid":"2","daihao":"BX10000","hangye":"科技互联网"}]}
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
        private List<NewsBean> news;
        private List<ShanghuBean> shanghu;

        public List<NewsBean> getNews() {
            return news;
        }

        public void setNews(List<NewsBean> news) {
            this.news = news;
        }

        public List<ShanghuBean> getShanghu() {
            return shanghu;
        }

        public void setShanghu(List<ShanghuBean> shanghu) {
            this.shanghu = shanghu;
        }

        public static class NewsBean {
            /**
             * id : 38
             * title : ACaaS: How it’s influenced by IoT
             * add_time : 1504835917
             * keywords : ACaaS: How it’s influenced by IoT
             * imgurl : /Public/uploads/news/2017-09-08/59b1fa02c1829.jpg
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

        public static class ShanghuBean {
            @Override
            public String toString() {
                return "ShanghuBean{" +
                        "id='" + id + '\'' +
                        ", name='" + name + '\'' +
                        ", addtime='" + addtime + '\'' +
                        ", code='" + code + '\'' +
                        ", point='" + point + '\'' +
                        ", hid='" + hid + '\'' +
                        ", lid='" + lid + '\'' +
                        ", daihao='" + daihao + '\'' +
                        ", hangye='" + hangye + '\'' +
                        '}';
            }

            /**
             * id : 10001
             * name : 郑州七七网络科技有限公司
             * addtime : 1507703627
             * code : 11233
             * point : 0
             * hid : 2
             * lid : 2
             * daihao : BX11233
             * hangye : 科技互联网
             */

            private String id;
            private String name;
            private String addtime;
            private String code;
            private String point;
            private String hid;
            private String lid;
            private String daihao;
            private String hangye;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getAddtime() {
                return addtime;
            }

            public void setAddtime(String addtime) {
                this.addtime = addtime;
            }

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
            }

            public String getPoint() {
                return point;
            }

            public void setPoint(String point) {
                this.point = point;
            }

            public String getHid() {
                return hid;
            }

            public void setHid(String hid) {
                this.hid = hid;
            }

            public String getLid() {
                return lid;
            }

            public void setLid(String lid) {
                this.lid = lid;
            }

            public String getDaihao() {
                return daihao;
            }

            public void setDaihao(String daihao) {
                this.daihao = daihao;
            }

            public String getHangye() {
                return hangye;
            }

            public void setHangye(String hangye) {
                this.hangye = hangye;
            }
        }
    }
}
