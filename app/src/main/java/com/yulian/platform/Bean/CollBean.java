package com.yulian.platform.Bean;

import java.util.List;

/**
 * Created by 赵磊 on 2017/5/27.
 */

public class CollBean {

    /**
     * stu : 1
     * res : [{"gid":"51","id":"51","img":"/Public/uploads/goods/img/2017-10-27/59f2ebc9d34c0.jpg","title":"CMOS TRUE WDR FACE RECOGNITION DOME IP CAMERA-2","price":"0.00","x_num":"I9015-H21H-AV-FR-WIFI PROBE","rmb":"$","sname":" Shenzhen HQVT Technology Co.,LTD"},{"gid":"56","id":"56","img":"/Public/uploads/goods/img/2017-10-27/59f2faa2e4918.jpg","title":"2MP Outdoor LPR Camera","price":"0.00","x_num":"I6009-H21A-AV-PRS","rmb":"$","sname":" Shenzhen HQVT Technology Co.,LTD"},{"gid":"50","id":"50","img":"/Public/uploads/goods/img/2017-10-27/59f2e77ee36af.jpg","title":"CMOS TRUE WDR FACE RECOGNITION DOME IP CAMERA-1","price":"0.00","x_num":"I6009-H21A-AV-FR","rmb":"$","sname":" Shenzhen HQVT Technology Co.,LTD"}]
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
         * gid : 51
         * id : 51
         * img : /Public/uploads/goods/img/2017-10-27/59f2ebc9d34c0.jpg
         * title : CMOS TRUE WDR FACE RECOGNITION DOME IP CAMERA-2
         * price : 0.00
         * x_num : I9015-H21H-AV-FR-WIFI PROBE
         * rmb : $
         * sname :  Shenzhen HQVT Technology Co.,LTD
         */

        private String gid;
        private String id;
        private String img;
        private String title;
        private String price;
        private String x_num;
        private String rmb;
        private String sname;

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

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
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

        public String getRmb() {
            return rmb;
        }

        public void setRmb(String rmb) {
            this.rmb = rmb;
        }

        public String getSname() {
            return sname;
        }

        public void setSname(String sname) {
            this.sname = sname;
        }
    }
}
