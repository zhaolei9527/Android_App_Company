package com.yulian.platform.Bean;

/**
 * Created by 赵磊 on 2017/10/17.
 */

public class B_DetailBean {

    /**
     * stu : 1
     * res : {"id":"36","title":"B厅2号","number":"2","x":"2","y":"1","q_num":"10000","eid":"6","hangye":"美容养生","addtime":"1507947545","del":"1","imgurl":"/Public/uploads/news/2017-10-13/59e08e3791e61.jpg","img":"/Public/uploads/news/2017-10-13/59e08e3792631.png","sid":"10000","name":"B厅2号"}
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
         * id : 36
         * title : B厅2号
         * number : 2
         * x : 2
         * y : 1
         * q_num : 10000
         * eid : 6
         * hangye : 美容养生
         * addtime : 1507947545
         * del : 1
         * imgurl : /Public/uploads/news/2017-10-13/59e08e3791e61.jpg
         * img : /Public/uploads/news/2017-10-13/59e08e3792631.png
         * sid : 10000
         * name : B厅2号
         */

        private String id;
        private String title;
        private String number;
        private String x;
        private String y;
        private String q_num;
        private String eid;
        private String hangye;
        private String addtime;
        private String del;
        private String imgurl;
        private String img;
        private String sid;
        private String name;

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

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public String getX() {
            return x;
        }

        public void setX(String x) {
            this.x = x;
        }

        public String getY() {
            return y;
        }

        public void setY(String y) {
            this.y = y;
        }

        public String getQ_num() {
            return q_num;
        }

        public void setQ_num(String q_num) {
            this.q_num = q_num;
        }

        public String getEid() {
            return eid;
        }

        public void setEid(String eid) {
            this.eid = eid;
        }

        public String getHangye() {
            return hangye;
        }

        public void setHangye(String hangye) {
            this.hangye = hangye;
        }

        public String getAddtime() {
            return addtime;
        }

        public void setAddtime(String addtime) {
            this.addtime = addtime;
        }

        public String getDel() {
            return del;
        }

        public void setDel(String del) {
            this.del = del;
        }

        public String getImgurl() {
            return imgurl;
        }

        public void setImgurl(String imgurl) {
            this.imgurl = imgurl;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getSid() {
            return sid;
        }

        public void setSid(String sid) {
            this.sid = sid;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
