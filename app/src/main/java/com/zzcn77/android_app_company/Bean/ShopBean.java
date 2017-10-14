package com.zzcn77.android_app_company.Bean;

import java.util.List;

/**
 * Created by 赵磊 on 2017/10/13.
 */

public class ShopBean {

    /**
     * stu : 1
     * res : [{"name":"rdgvs","point":"0","cid":"2","code":"BX","title":"监控硬件销售"},{"name":"rdgvs","point":"0","cid":"2","code":"BX","title":"科技互联网"},{"name":"郑州七七网络科技有限公司","point":"0","cid":"6","code":"DZ","title":"监控硬件销售"},{"name":"郑州七七网络科技有限公司","point":"0","cid":"6","code":"DZ","title":"科技互联网"},{"name":"宇联APP科技有限公司","point":"4","cid":"2","code":"BX","title":"监控硬件销售"},{"name":"宇联APP科技有限公司","point":"4","cid":"2","code":"BX","title":"科技互联网"},{"name":"FUN","point":"0","cid":"0","code":null,"title":"监控硬件销售"},{"name":"FUN","point":"0","cid":"0","code":null,"title":"科技互联网"},{"name":"推送","point":"0","cid":"0","code":null,"title":"监控硬件销售"},{"name":"推送","point":"0","cid":"0","code":null,"title":"科技互联网"}]
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
         * name : rdgvs
         * point : 0
         * cid : 2
         * code : BX
         * title : 监控硬件销售
         */

        private String name;
        private String point;
        private String cid;
        private String code;
        private String title;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
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

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
