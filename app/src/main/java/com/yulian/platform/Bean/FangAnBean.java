package com.yulian.platform.Bean;

import java.util.List;

/**
 * Created by 赵磊 on 2017/5/26.
 */

public class FangAnBean {

    /**
     * stu : 1
     * res : [{"id":"2","title":"测试方案","imgurl":"/Public/uploads/fangan/2017-05-25/59264abd78a99.png","content":"&lt;p&gt;第三个v房v根本&lt;br/&gt;&lt;/p&gt;","status":"1","is_del":"0","add_time":"1495681725","sort":"2"},{"id":"1","title":"折叠缝合你","imgurl":"/Public/uploads/fangan/2017-05-25/59264d20ed998.png","content":"&lt;p&gt;股份吗&lt;br/&gt;&lt;/p&gt;","status":"1","is_del":"0","add_time":"1495678632","sort":"1"}]
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
         * id : 2
         * title : 测试方案
         * imgurl : /Public/uploads/fangan/2017-05-25/59264abd78a99.png
         * content : &lt;p&gt;第三个v房v根本&lt;br/&gt;&lt;/p&gt;
         * status : 1
         * is_del : 0
         * add_time : 1495681725
         * sort : 2
         */

        private String id;
        private String title;
        private String imgurl;
        private String content;
        private String status;
        private String is_del;
        private String add_time;
        private String sort;

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

        public String getImgurl() {
            return imgurl;
        }

        public void setImgurl(String imgurl) {
            this.imgurl = imgurl;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getIs_del() {
            return is_del;
        }

        public void setIs_del(String is_del) {
            this.is_del = is_del;
        }

        public String getAdd_time() {
            return add_time;
        }

        public void setAdd_time(String add_time) {
            this.add_time = add_time;
        }

        public String getSort() {
            return sort;
        }

        public void setSort(String sort) {
            this.sort = sort;
        }
    }
}
