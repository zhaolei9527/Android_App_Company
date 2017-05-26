package com.zzcn77.android_app_company.Bean;

import java.util.List;

/**
 * Created by 赵磊 on 2017/5/26.
 */

public class YanShiBean  {

    /**
     * stu : 1
     * res : [{"id":"2","title":"fs r","imgurl":"/Public/uploads/yanshi/2017-05-25/592636c44a688.png","url":"http://www.baidu.com","sort":"0","add_time":"1495675804"},{"id":"3","title":"实时视频支持双向超清（1080p）视频","imgurl":"/Public/uploads/yanshi/2017-05-25/592637a153d7a.png","url":"http://www.baidu.com","sort":"1","add_time":"1495676833"}]
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
         * title : fs r
         * imgurl : /Public/uploads/yanshi/2017-05-25/592636c44a688.png
         * url : http://www.baidu.com
         * sort : 0
         * add_time : 1495675804
         */

        private String id;
        private String title;
        private String imgurl;
        private String url;
        private String sort;
        private String add_time;

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

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getSort() {
            return sort;
        }

        public void setSort(String sort) {
            this.sort = sort;
        }

        public String getAdd_time() {
            return add_time;
        }

        public void setAdd_time(String add_time) {
            this.add_time = add_time;
        }
    }
}
