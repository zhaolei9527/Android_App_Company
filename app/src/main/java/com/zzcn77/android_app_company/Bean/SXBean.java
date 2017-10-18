package com.zzcn77.android_app_company.Bean;

import java.util.List;

/**
 * Created by 赵磊 on 2017/10/16.
 */

public class SXBean {

    /**
     * stu : 1
     * res : [{"id":"6","sh_id":"10000","uid":"1","addtime":"1507803938","canceltime":"1507803925","type":"1","is_pj":"0","sort":"3"},{"id":"3","sh_id":"10001","uid":"1","addtime":"1507617698","canceltime":"1507800508","type":"2","is_pj":null,"sort":"6"}]
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
         * id : 6
         * sh_id : 10000
         * uid : 1
         * addtime : 1507803938
         * canceltime : 1507803925
         * type : 1
         * is_pj : 0
         * sort : 3
         */

        private String id;
        private String sh_id;
        private String uid;
        private String addtime;
        private String canceltime;
        private String type;
        private String is_pj;
        private String sort;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getSh_id() {
            return sh_id;
        }

        public void setSh_id(String sh_id) {
            this.sh_id = sh_id;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getAddtime() {
            return addtime;
        }

        public void setAddtime(String addtime) {
            this.addtime = addtime;
        }

        public String getCanceltime() {
            return canceltime;
        }

        public void setCanceltime(String canceltime) {
            this.canceltime = canceltime;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getIs_pj() {
            return is_pj;
        }

        public void setIs_pj(String is_pj) {
            this.is_pj = is_pj;
        }

        public String getSort() {
            return sort;
        }

        public void setSort(String sort) {
            this.sort = sort;
        }
    }
}
