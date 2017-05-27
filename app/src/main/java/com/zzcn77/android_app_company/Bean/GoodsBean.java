package com.zzcn77.android_app_company.Bean;

import java.util.List;

/**
 * Created by 赵磊 on 2017/5/27.
 */

public class GoodsBean {


    /**
     * stu : 1
     * res : {"cate":[{"title":"视频监控明星产品","img":"/Public/uploads/goods/img/2017-05-24/5925302bdb558.png","id":"6"},{"title":"停车场明星监控套装","img":"/Public/uploads/goods/img/2017-05-24/59252ed637622.png","id":"5"},{"title":"视频监控系统","img":"/Public/uploads/goods/img/2017-05-24/592525370f9dd.png","id":"4"},{"title":"综合布线监控套装","img":"/Public/uploads/goods/img/2017-05-24/5925251ac74dd.png","id":"3"},{"title":"安防警报系统套装","img":"/Public/uploads/goods/img/2017-05-24/592524de5df7d.png","id":"2"},{"title":"停车场监控套装","img":"/Public/uploads/goods/img/2017-05-24/5925229af0a99.png","id":"1"}],"goodsmx":[{"title":"视频监控明星产品","img":"/Public/uploads/goods/img/2017-05-24/5925302bdb558.png","id":"6"},{"title":"停车场明星监控套装","img":"/Public/uploads/goods/img/2017-05-24/59252ed637622.png","id":"5"}]}
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
        private List<CateBean> cate;
        private List<GoodsmxBean> goodsmx;

        public List<CateBean> getCate() {
            return cate;
        }

        public void setCate(List<CateBean> cate) {
            this.cate = cate;
        }

        public List<GoodsmxBean> getGoodsmx() {
            return goodsmx;
        }

        public void setGoodsmx(List<GoodsmxBean> goodsmx) {
            this.goodsmx = goodsmx;
        }

        public static class CateBean {
            /**
             * title : 视频监控明星产品
             * img : /Public/uploads/goods/img/2017-05-24/5925302bdb558.png
             * id : 6
             */

            private String title;
            private String img;
            private String id;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }
        }

        public static class GoodsmxBean {
            /**
             * title : 视频监控明星产品
             * img : /Public/uploads/goods/img/2017-05-24/5925302bdb558.png
             * id : 6
             */

            private String title;
            private String img;
            private String id;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }
        }
    }
}
