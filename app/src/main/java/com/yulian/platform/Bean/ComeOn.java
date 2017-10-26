package com.yulian.platform.Bean;

import java.util.List;

/**
 * Created by 赵磊 on 2017/10/13.
 */

public class ComeOn {

    /**
     * stu : 1
     * res : {"news":[{"id":"38","title":"ACaaS: How it\u2019s influenced by IoT"},{"id":"36","title":"5 questions to ask before selecting face recognition solution"},{"id":"37","title":"Detecting traffic accidents with machine learning"},{"id":"35","title":"Why Digital Barriers limited its free \u2018missing-person solution\u2019 to young people"},{"id":"33","title":"Face recognition is for known threats. This technology detects the unknown!"},{"id":"2","title":"ALPR: a new philosophy of best practices"},{"id":"34","title":"IFSEC International"},{"id":"40","title":"标题"},{"id":"39","title":"测试动态"}],"shanghu":[{"id":"10011","name":"深圳蓝天公司A","daihao":"DZ9001","hangye":"科技互联网","point":"2","code":"9001"}],"beijingtu":"/Public/uploads/images/logo/59ed51a7c2b0f.png"}
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
         * news : [{"id":"38","title":"ACaaS: How it\u2019s influenced by IoT"},{"id":"36","title":"5 questions to ask before selecting face recognition solution"},{"id":"37","title":"Detecting traffic accidents with machine learning"},{"id":"35","title":"Why Digital Barriers limited its free \u2018missing-person solution\u2019 to young people"},{"id":"33","title":"Face recognition is for known threats. This technology detects the unknown!"},{"id":"2","title":"ALPR: a new philosophy of best practices"},{"id":"34","title":"IFSEC International"},{"id":"40","title":"标题"},{"id":"39","title":"测试动态"}]
         * shanghu : [{"id":"10011","name":"深圳蓝天公司A","daihao":"DZ9001","hangye":"科技互联网","point":"2","code":"9001"}]
         * beijingtu : /Public/uploads/images/logo/59ed51a7c2b0f.png
         */

        private String beijingtu;
        private List<NewsBean> news;
        private List<ShanghuBean> shanghu;

        public String getBeijingtu() {
            return beijingtu;
        }

        public void setBeijingtu(String beijingtu) {
            this.beijingtu = beijingtu;
        }

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
             */

            private String id;
            private String title;

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
        }

        public static class ShanghuBean {
            /**
             * id : 10011
             * name : 深圳蓝天公司A
             * daihao : DZ9001
             * hangye : 科技互联网
             * point : 2
             * code : 9001
             */

            private String id;
            private String name;
            private String daihao;
            private String hangye;
            private String point;
            private String code;

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

            public String getPoint() {
                return point;
            }

            public void setPoint(String point) {
                this.point = point;
            }

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
            }
        }
    }
}
