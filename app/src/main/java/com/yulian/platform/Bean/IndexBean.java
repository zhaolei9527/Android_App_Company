package com.yulian.platform.Bean;

import java.util.List;

/**
 * Created by 赵磊 on 2017/5/25.
 */

public class IndexBean {
    @Override
    public String toString() {
        return "IndexBean{" +
                "stu='" + stu + '\'' +
                ", res=" + res +
                '}';
    }

    /**
     * stu : 1
     * res : {"is_coll":1,"lunbo":[{"id":"18","img":"/Public/uploads/ad/2017-07-28/597b058f03380.png","type":"5","bid":null},{"id":"17","img":"/Public/uploads/ad/2017-07-27/59798eea3ce91.jpg","type":"3","bid":"4"},{"id":"12","img":"/Public/uploads/ad/2017-07-27/59798f131d447.jpg","type":"2","bid":"3"}],"jianjie":{"title":"宇联APP科技有限公司","keywords":"测试数据","id":10000,"tel":"15111111111","logo":"/Public/uploads/admin/2017-10-12/59df2588c6d40.jpg","imgurl":"/Public/uploads/admin/2017-10-14/59e15fea6c020.jpg","point":"4"},"dongtai":[{"id":"38","title":"ACaaS: How it\u2019s influenced by IoT","keywords":"ACaaS: How it\u2019s influenced by IoT","imgurl":"/Public/uploads/news/2017-09-08/59b1fa02c1829.jpg"},{"id":"36","title":"5 questions to ask before selecting face recognition solution","keywords":"5 questions to ask before selecting face recognition solution","imgurl":"/Public/uploads/news/2017-08-24/599ea872efe58.jpg"},{"id":"37","title":"Detecting traffic accidents with machine learning","keywords":"Detecting traffic accidents with machine learning","imgurl":"/Public/uploads/news/2017-08-31/59a7d41ab8067.jpg"},{"id":"35","title":"Why Digital Barriers limited its free \u2018missing-person solution\u2019 to young people","keywords":"Why Digital Barriers limited its free \u2018missing-person solution\u2019 to young people","imgurl":"/Public/uploads/news/2017-08-12/598e74280d570.jpg"},{"id":"33","title":"Face recognition is for known threats. This technology detects the unknown!","keywords":"Face recognition is for known threats. This technology detects the unknown!","imgurl":"/Public/uploads/news/2017-07-27/5979955c41559.jpg"},{"id":"2","title":"ALPR: a new philosophy of best practices","keywords":"ALPR: a new philosophy of best practices","imgurl":"/Public/uploads/news/2017-07-27/597997401a272.jpg"},{"id":"34","title":"IFSEC International","keywords":"Thank you to everyone that attended IFSEC International 2017","imgurl":"/Public/uploads/news/2017-07-27/59799c6f2a26c.jpg"}],"huodong":[{"id":"22","title":"测试数据","keywords":"测试数据","imgurl":"/Public/uploads/cuxiao/2017-10-11/59dd8d4c12f20.jpg","zhe":"0","content":"&lt;p&gt;测试数据&lt;/p&gt;","add_time":"1507691852","sort":"0","sh_id":"10000"}]}
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
        @Override
        public String toString() {
            return "ResBean{" +
                    "is_coll=" + is_coll +
                    ", jianjie=" + jianjie +
                    ", lunbo=" + lunbo +
                    ", dongtai=" + dongtai +
                    ", huodong=" + huodong +
                    '}';
        }

        /**
         * is_coll : 1
         * lunbo : [{"id":"18","img":"/Public/uploads/ad/2017-07-28/597b058f03380.png","type":"5","bid":null},{"id":"17","img":"/Public/uploads/ad/2017-07-27/59798eea3ce91.jpg","type":"3","bid":"4"},{"id":"12","img":"/Public/uploads/ad/2017-07-27/59798f131d447.jpg","type":"2","bid":"3"}]
         * jianjie : {"title":"宇联APP科技有限公司","keywords":"测试数据","id":10000,"tel":"15111111111","logo":"/Public/uploads/admin/2017-10-12/59df2588c6d40.jpg","imgurl":"/Public/uploads/admin/2017-10-14/59e15fea6c020.jpg","point":"4"}
         * dongtai : [{"id":"38","title":"ACaaS: How it\u2019s influenced by IoT","keywords":"ACaaS: How it\u2019s influenced by IoT","imgurl":"/Public/uploads/news/2017-09-08/59b1fa02c1829.jpg"},{"id":"36","title":"5 questions to ask before selecting face recognition solution","keywords":"5 questions to ask before selecting face recognition solution","imgurl":"/Public/uploads/news/2017-08-24/599ea872efe58.jpg"},{"id":"37","title":"Detecting traffic accidents with machine learning","keywords":"Detecting traffic accidents with machine learning","imgurl":"/Public/uploads/news/2017-08-31/59a7d41ab8067.jpg"},{"id":"35","title":"Why Digital Barriers limited its free \u2018missing-person solution\u2019 to young people","keywords":"Why Digital Barriers limited its free \u2018missing-person solution\u2019 to young people","imgurl":"/Public/uploads/news/2017-08-12/598e74280d570.jpg"},{"id":"33","title":"Face recognition is for known threats. This technology detects the unknown!","keywords":"Face recognition is for known threats. This technology detects the unknown!","imgurl":"/Public/uploads/news/2017-07-27/5979955c41559.jpg"},{"id":"2","title":"ALPR: a new philosophy of best practices","keywords":"ALPR: a new philosophy of best practices","imgurl":"/Public/uploads/news/2017-07-27/597997401a272.jpg"},{"id":"34","title":"IFSEC International","keywords":"Thank you to everyone that attended IFSEC International 2017","imgurl":"/Public/uploads/news/2017-07-27/59799c6f2a26c.jpg"}]
         * huodong : [{"id":"22","title":"测试数据","keywords":"测试数据","imgurl":"/Public/uploads/cuxiao/2017-10-11/59dd8d4c12f20.jpg","zhe":"0","content":"&lt;p&gt;测试数据&lt;/p&gt;","add_time":"1507691852","sort":"0","sh_id":"10000"}]
         */

        private int is_coll;
        private JianjieBean jianjie;
        private List<LunboBean> lunbo;
        private List<DongtaiBean> dongtai;
        private List<HuodongBean> huodong;

        public int getIs_coll() {
            return is_coll;
        }

        public void setIs_coll(int is_coll) {
            this.is_coll = is_coll;
        }

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
            @Override
            public String toString() {
                return "JianjieBean{" +
                        "title='" + title + '\'' +
                        ", keywords='" + keywords + '\'' +
                        ", id=" + id +
                        ", tel='" + tel + '\'' +
                        ", logo='" + logo + '\'' +
                        ", imgurl='" + imgurl + '\'' +
                        ", point='" + point + '\'' +
                        '}';
            }

            /**
             * title : 宇联APP科技有限公司
             * keywords : 测试数据
             * id : 10000
             * tel : 15111111111
             * logo : /Public/uploads/admin/2017-10-12/59df2588c6d40.jpg
             * imgurl : /Public/uploads/admin/2017-10-14/59e15fea6c020.jpg
             * point : 4
             */

            private String title;
            private String keywords;
            private int id;
            private String tel;
            private String logo;
            private String imgurl;
            private String point;

            public String getCodes() {
                return codes;
            }

            public void setCodes(String codes) {
                this.codes = codes;
            }

            private String codes;

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

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getTel() {
                return tel;
            }

            public void setTel(String tel) {
                this.tel = tel;
            }

            public String getLogo() {
                return logo;
            }

            public void setLogo(String logo) {
                this.logo = logo;
            }

            public String getImgurl() {
                return imgurl;
            }

            public void setImgurl(String imgurl) {
                this.imgurl = imgurl;
            }

            public String getPoint() {
                return point;
            }

            public void setPoint(String point) {
                this.point = point;
            }
        }

        public static class LunboBean {
            @Override
            public String toString() {
                return "LunboBean{" +
                        "id='" + id + '\'' +
                        ", img='" + img + '\'' +
                        ", type='" + type + '\'' +
                        ", bid='" + bid + '\'' +
                        '}';
            }

            /**
             * id : 18
             * img : /Public/uploads/ad/2017-07-28/597b058f03380.png
             * type : 5
             * bid : null
             */

            private String id;
            private String img;
            private String type;
            private String bid;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getBid() {
                return bid;
            }

            public void setBid(String bid) {
                this.bid = bid;
            }
        }

        public static class DongtaiBean {
            @Override
            public String toString() {
                return "DongtaiBean{" +
                        "id='" + id + '\'' +
                        ", title='" + title + '\'' +
                        ", keywords='" + keywords + '\'' +
                        ", imgurl='" + imgurl + '\'' +
                        '}';
            }

            /**
             * id : 38
             * title : ACaaS: How it’s influenced by IoT
             * keywords : ACaaS: How it’s influenced by IoT
             * imgurl : /Public/uploads/news/2017-09-08/59b1fa02c1829.jpg
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
            @Override
            public String toString() {
                return "HuodongBean{" +
                        "id='" + id + '\'' +
                        ", title='" + title + '\'' +
                        ", keywords='" + keywords + '\'' +
                        ", imgurl='" + imgurl + '\'' +
                        ", zhe='" + zhe + '\'' +
                        ", content='" + content + '\'' +
                        ", add_time='" + add_time + '\'' +
                        ", sort='" + sort + '\'' +
                        ", sh_id='" + sh_id + '\'' +
                        '}';
            }

            /**
             * id : 22
             * title : 测试数据
             * keywords : 测试数据
             * imgurl : /Public/uploads/cuxiao/2017-10-11/59dd8d4c12f20.jpg
             * zhe : 0
             * content : &lt;p&gt;测试数据&lt;/p&gt;
             * add_time : 1507691852
             * sort : 0
             * sh_id : 10000
             */

            private String id;
            private String title;
            private String keywords;
            private String imgurl;
            private String zhe;
            private String content;
            private String add_time;
            private String sort;
            private String sh_id;

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

            public String getZhe() {
                return zhe;
            }

            public void setZhe(String zhe) {
                this.zhe = zhe;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getAdd_time() {
                return add_time;
            }

            public void setAdd_time(String add_time) {
                this.add_time = add_time;
            }

            public String getSort() {
                return sort;
            }

            public void setSort(String sort) {
                this.sort = sort;
            }

            public String getSh_id() {
                return sh_id;
            }

            public void setSh_id(String sh_id) {
                this.sh_id = sh_id;
            }
        }
    }
}
