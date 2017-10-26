package com.yulian.platform.Bean;

import java.util.List;

/**
 * Created by 赵磊 on 2017/5/27.
 */

public class CollBean {
    /**
     * stu : 1
     * res : [{"gid":"1","id":"1","img":null,"title":"停车场监控套装","price":"100.00","x_num":"2100x"},{"gid":"2","id":"2","img":null,"title":"安防警报系统套装","price":"100.00","x_num":"2010X"},{"gid":"3","id":"3","img":null,"title":"综合布线监控套装","price":"1515.00","x_num":"2010d"},{"gid":"3","id":"3","img":null,"title":"综合布线监控套装","price":"1515.00","x_num":"2010d"},{"gid":"4","id":"4","img":null,"title":"视频监控系统","price":"100.00","x_num":"sd"},{"gid":"5","id":"5","img":null,"title":"停车场明星监控套装","price":"500.00","x_num":"1251"}]
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
         * gid : 1
         * id : 1
         * img : null
         * title : 停车场监控套装
         * price : 100.00
         * x_num : 2100x
         */

        private String gid;
        private String id;
        private Object img;
        private String title;
        private String price;
        private String x_num;

        public String getRmb() {
            return rmb;
        }

        public void setRmb(String rmb) {
            this.rmb = rmb;
        }

        private String rmb;


        public String getGid() {
            return gid;
        }

        public void setGid(String gid) {
            this.gid = gid;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public Object getImg() {
            return img;
        }

        public void setImg(Object img) {
            this.img = img;
        }

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
    }
}
