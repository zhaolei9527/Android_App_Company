package com.zzcn77.android_app_company.Bean;

/**
 * Created by 赵磊 on 2017/5/27.
 */

public class VersionBean {

    /**
     * stu : 1
     * res : {"android":"/Public/uploads/android/59269fb97b196.apk","android_bnum":"2","android_content":"更新了若干bug","android_uptime":"1495703481"}
     */

    private int stu;
    private ResBean res;

    public int getStu() {
        return stu;
    }

    public void setStu(int stu) {
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
         * android : /Public/uploads/android/59269fb97b196.apk
         * android_bnum : 2
         * android_content : 更新了若干bug
         * android_uptime : 1495703481
         */

        private String android;
        private String android_bnum;
        private String android_content;
        private String android_uptime;

        public String getAndroid() {
            return android;
        }

        public void setAndroid(String android) {
            this.android = android;
        }

        public String getAndroid_bnum() {
            return android_bnum;
        }

        public void setAndroid_bnum(String android_bnum) {
            this.android_bnum = android_bnum;
        }

        public String getAndroid_content() {
            return android_content;
        }

        public void setAndroid_content(String android_content) {
            this.android_content = android_content;
        }

        public String getAndroid_uptime() {
            return android_uptime;
        }

        public void setAndroid_uptime(String android_uptime) {
            this.android_uptime = android_uptime;
        }
    }
}
