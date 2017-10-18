package com.zzcn77.android_app_company.Bean;

import java.util.List;

/**
 * Created by 赵磊 on 2017/10/17.
 */

public class ShopsBean {


    /**
     * stu : 1
     * res : [{"id":"10000","company":"testcompany","point":"6","cid":"3","codes":"CY10000","title":"科技互联网","btitle":"B厅1号","code":"CY","name":"testcompany"},{"id":"10000","company":"testcompany","point":"6","cid":"3","codes":"CY10000","title":"科技互联网","btitle":"劳斯莱斯","code":"CY","name":"testcompany"},{"id":"10000","company":"testcompany","point":"6","cid":"3","codes":"CY10000","title":"科技互联网","btitle":"B厅2号","code":"CY","name":"testcompany"}]
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
         * id : 10000
         * company : testcompany
         * point : 6
         * cid : 3
         * codes : CY10000
         * title : 科技互联网
         * btitle : B厅1号
         * code : CY
         * name : testcompany
         */

        private String id;
        private String company;
        private String point;
        private String cid;
        private String codes;
        private String title;
        private String btitle;
        private String code;
        private String name;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
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

        public String getCid() {
            return cid;
        }

        public void setCid(String cid) {
            this.cid = cid;
        }

        public String getCodes() {
            return codes;
        }

        public void setCodes(String codes) {
            this.codes = codes;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getBtitle() {
            return btitle;
        }

        public void setBtitle(String btitle) {
            this.btitle = btitle;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
