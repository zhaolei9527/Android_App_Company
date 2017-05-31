package com.zzcn77.android_app_company.Bean;

import java.util.List;

/**
 * Created by 赵磊 on 2017/5/31.
 */

public class Goods_ListsBean {


    /**
     * stu : 1
     * res : {"goods":[{"title":"停车场明星监控套装","price":"500.00","x_num":"1251","img":"/Public/uploads/goods/img/2017-05-24/59252ed637622.png","id":"5","coll":"1"},{"title":"start","price":"569.00","x_num":"start9","img":"/Public/uploads/goods/img/2017-05-25/59268a45b6278.png","id":"7","coll":"1"},{"title":"视频监控明星产品","price":"500.00","x_num":"1251","img":"/Public/uploads/goods/img/2017-05-24/5925302bdb558.png","id":"6","coll":"1"},{"title":"视频监控系统","price":"100.00","x_num":"sd","img":"/Public/uploads/goods/img/2017-05-24/592525370f9dd.png","id":"4","coll":"-1"},{"title":"综合布线监控套装","price":"1515.00","x_num":"2010d","img":"/Public/uploads/goods/img/2017-05-24/5925251ac74dd.png","id":"3","coll":"-1"},{"title":"安防警报系统套装","price":"100.00","x_num":"2010X","img":"/Public/uploads/goods/img/2017-05-24/592524de5df7d.png","id":"2","coll":"-1"},{"title":"停车场监控套装","price":"100.00","x_num":"2100x","img":"/Public/uploads/goods/img/2017-05-24/5925229af0a99.png","id":"1","coll":"-1"}]}
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
        private List<GoodsBean> goods;

        public List<GoodsBean> getGoods() {
            return goods;
        }

        public void setGoods(List<GoodsBean> goods) {
            this.goods = goods;
        }

        public static class GoodsBean {
            /**
             * title : 停车场明星监控套装
             * price : 500.00
             * x_num : 1251
             * img : /Public/uploads/goods/img/2017-05-24/59252ed637622.png
             * id : 5
             * coll : 1
             */

            private String title;
            private String price;
            private String x_num;
            private String img;
            private String id;
            private String coll;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public String getX_num() {
                return x_num;
            }

            public void setX_num(String x_num) {
                this.x_num = x_num;
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

            public String getColl() {
                return coll;
            }

            public void setColl(String coll) {
                this.coll = coll;
            }
        }
    }
}
