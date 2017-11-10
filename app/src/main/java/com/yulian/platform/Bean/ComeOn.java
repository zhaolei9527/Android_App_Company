package com.yulian.platform.Bean;

import java.util.List;

/**
 * Created by 赵磊 on 2017/10/13.
 */

public class ComeOn {

    /**
     * stu : 1
     * res : {"news":[{"id":"55","title":"New cybersecurity approaches urged after Swedish cybercrime"},{"id":"48","title":"ACaaS: How it\u2019s influenced by IoT"},{"id":"49","title":"5 questions to ask before selecting face recognition solution"},{"id":"50","title":"Detecting traffic accidents with machine learning"},{"id":"51","title":"Why Digital Barriers limited its free \u2018missing-person solution\u2019 to young people"},{"id":"52","title":"Face recognition is for known threats. This technology detects the unknown!"},{"id":"53","title":"ALPR: a new philosophy of best practices"},{"id":"54","title":"IFSEC International"}],"shanghu":[{"id":"10044","name":"HIP global cp.,ltd","daihao":"THAB001","hangye":"Security","point":"0","code":"AB001"},{"id":"10042","name":"THAI SECURITY ASSOCIATION","daihao":"THA001","hangye":"Association","point":"0","code":"A001"},{"id":"10028","name":"EXCEL PROTECT LIMITED","daihao":"HKAA0010","hangye":"Security","point":"0","code":"AA0010"},{"id":"10037","name":"SAMCOM TECHNOLOGY Co.,Ltd.","daihao":"THAA0028","hangye":"Security","point":"2","code":"AA0028"},{"id":"10025","name":"Guard Security Equipment Co,. Ltd.","daihao":"HKAA0021","hangye":"Security","point":"2","code":"AA0021"},{"id":"10024","name":"Beijing ICETech Co.,Ltd.","daihao":"CNAI0010","hangye":"AI","point":"2","code":"AI0010"},{"id":"10015","name":"SHENZHEN YUEYANGXING CABLE &amp; WIRE CO., LTD","daihao":"CNAE0020","hangye":"Security","point":"6","code":"AE0020"},{"id":"10021","name":"七七网络科技","daihao":"HKqiqi10000","hangye":"Security","point":"4","code":"qiqi10000"},{"id":"10019","name":" Shenzhen HQVT Technology Co.,LTD","daihao":"CNAA0013","hangye":"Security","point":"7","code":"AA0013"}],"beijingtu":"/Public/uploads/images/logo/59ff3596131a3.png","bg_img":"/Public/uploads/images/logo/5a02f2f7195d8.jpg","card_img":"/Public/uploads/images/logo/5a02f3b43158b.png"}
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
         * news : [{"id":"55","title":"New cybersecurity approaches urged after Swedish cybercrime"},{"id":"48","title":"ACaaS: How it\u2019s influenced by IoT"},{"id":"49","title":"5 questions to ask before selecting face recognition solution"},{"id":"50","title":"Detecting traffic accidents with machine learning"},{"id":"51","title":"Why Digital Barriers limited its free \u2018missing-person solution\u2019 to young people"},{"id":"52","title":"Face recognition is for known threats. This technology detects the unknown!"},{"id":"53","title":"ALPR: a new philosophy of best practices"},{"id":"54","title":"IFSEC International"}]
         * shanghu : [{"id":"10044","name":"HIP global cp.,ltd","daihao":"THAB001","hangye":"Security","point":"0","code":"AB001"},{"id":"10042","name":"THAI SECURITY ASSOCIATION","daihao":"THA001","hangye":"Association","point":"0","code":"A001"},{"id":"10028","name":"EXCEL PROTECT LIMITED","daihao":"HKAA0010","hangye":"Security","point":"0","code":"AA0010"},{"id":"10037","name":"SAMCOM TECHNOLOGY Co.,Ltd.","daihao":"THAA0028","hangye":"Security","point":"2","code":"AA0028"},{"id":"10025","name":"Guard Security Equipment Co,. Ltd.","daihao":"HKAA0021","hangye":"Security","point":"2","code":"AA0021"},{"id":"10024","name":"Beijing ICETech Co.,Ltd.","daihao":"CNAI0010","hangye":"AI","point":"2","code":"AI0010"},{"id":"10015","name":"SHENZHEN YUEYANGXING CABLE &amp; WIRE CO., LTD","daihao":"CNAE0020","hangye":"Security","point":"6","code":"AE0020"},{"id":"10021","name":"七七网络科技","daihao":"HKqiqi10000","hangye":"Security","point":"4","code":"qiqi10000"},{"id":"10019","name":" Shenzhen HQVT Technology Co.,LTD","daihao":"CNAA0013","hangye":"Security","point":"7","code":"AA0013"}]
         * beijingtu : /Public/uploads/images/logo/59ff3596131a3.png
         * bg_img : /Public/uploads/images/logo/5a02f2f7195d8.jpg
         * card_img : /Public/uploads/images/logo/5a02f3b43158b.png
         */

        private String beijingtu;
        private String bg_img;
        private String card_img;
        private List<NewsBean> news;
        private List<ShanghuBean> shanghu;

        public String getBeijingtu() {
            return beijingtu;
        }

        public void setBeijingtu(String beijingtu) {
            this.beijingtu = beijingtu;
        }

        public String getBg_img() {
            return bg_img;
        }

        public void setBg_img(String bg_img) {
            this.bg_img = bg_img;
        }

        public String getCard_img() {
            return card_img;
        }

        public void setCard_img(String card_img) {
            this.card_img = card_img;
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
             * id : 55
             * title : New cybersecurity approaches urged after Swedish cybercrime
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
             * id : 10044
             * name : HIP global cp.,ltd
             * daihao : THAB001
             * hangye : Security
             * point : 0
             * code : AB001
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
