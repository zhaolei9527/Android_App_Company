package com.yulian.platform.Bean;

import java.util.List;

/**
 * Created by 赵磊 on 2017/5/26.
 */

public class CuxiaoBean {


    /**
     * stu : 1
     * res : [{"id":"3","title":"bguj","keywords":"hv","imgurl":"/Public/uploads/cuxiao/2017-05-24/59255eb7e8309.png","zhe":"9.9","content":"&lt;p&gt;njjbk&lt;br/&gt;&lt;/p&gt;","add_time":"1495520442","sort":"7"},{"id":"4","title":"xngf","keywords":"dsf","imgurl":"/Public/uploads/cuxiao/2017-05-24/5925580913170.png","zhe":"8.0","content":"&lt;p&gt;sdf &lt;br/&gt;&lt;/p&gt;","add_time":"1495619593","sort":"5"},{"id":"1","title":"xngf","keywords":"7896524123","imgurl":"/Public/uploads/cuxiao/2017-05-24/59255e2962d9f.png","zhe":"9.9","content":"&lt;p&gt;gf&lt;br/&gt;&lt;/p&gt;","add_time":"0","sort":"5"}]
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
         * id : 3
         * title : bguj
         * keywords : hv
         * imgurl : /Public/uploads/cuxiao/2017-05-24/59255eb7e8309.png
         * zhe : 9.9
         * content : &lt;p&gt;njjbk&lt;br/&gt;&lt;/p&gt;
         * add_time : 1495520442
         * sort : 7
         */

        private String id;
        private String title;
        private String keywords;
        private String imgurl;
        private String zhe;
        private String content;
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

        public String getKeywords() {
            return keywords;
        }

        public void setKeywords(String keywords) {
            this.keywords = keywords;
        }

        public String getImgurl() {
            return imgurl;
        }

        public void setImgurl(String imgurl) {
            this.imgurl = imgurl;
        }

        public String getZhe() {
            return zhe;
        }

        public void setZhe(String zhe) {
            this.zhe = zhe;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
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
