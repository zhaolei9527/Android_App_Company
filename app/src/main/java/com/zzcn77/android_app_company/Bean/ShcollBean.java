package com.zzcn77.android_app_company.Bean;

import java.util.List;

/**
 * Created by 赵磊 on 2017/10/14.
 */

public class ShcollBean {

    /**
     * stu : 1
     * res : [{"sh_id":"10000","pj_stu":"1","img":"/Public/uploads/admin/2017-10-14/59e15fea6c020.jpg","company":"testcompany","point":"4","description":"测试数据"},{"sh_id":"10001","pj_stu":"2","img":"/Public/uploads/admin/2017-10-14/59e172cc15950.jpg","company":"妈妈咪呀","point":"1","description":"公司简介公司简介公司简介公司简介公司简介公司简介公司简介公司简介公司简介公司简介公司简介公司简介公司简介公司简介公司简介公司简介公司简介公司简介"}]
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
         * sh_id : 10000
         * pj_stu : 1
         * img : /Public/uploads/admin/2017-10-14/59e15fea6c020.jpg
         * company : testcompany
         * point : 4
         * description : 测试数据
         */

        private String sh_id;
        private String pj_stu;
        private String img;
        private String company;
        private String point;
        private String description;

        public String getSh_id() {
            return sh_id;
        }

        public void setSh_id(String sh_id) {
            this.sh_id = sh_id;
        }

        public String getPj_stu() {
            return pj_stu;
        }

        public void setPj_stu(String pj_stu) {
            this.pj_stu = pj_stu;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getCompany() {
            return company;
        }

        public void setCompany(String company) {
            this.company = company;
        }

        public String getPoint() {
            return point;
        }

        public void setPoint(String point) {
            this.point = point;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }
}
