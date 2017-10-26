package com.yulian.platform.Bean;

import java.util.List;

/**
 * Created by 赵磊 on 2017/10/17.
 */

public class ExhibitionBean {

    /**
     * stu : 1
     * res : [{"id":"5","title":"车展","imgurl":"/Public/uploads/news/2017-10-11/59ddd034497c8.jpg","sort":"0","addtime":"1507708980","status":"1","del":"1"},{"id":"4","title":"艺术展厅","imgurl":"/Public/uploads/news/2017-10-11/59dda7a891e60.jpg","sort":"0","addtime":"1507698600","status":"1","del":"1"},{"id":"3","title":"展厅设计","imgurl":"/Public/uploads/news/2017-10-10/59dc6dee59808.jpg","sort":"0","addtime":"1507618286","status":"1","del":"1"},{"id":"2","title":"展厅","imgurl":"/Public/uploads/news/2017-10-10/59dc6e63361a0.jpg","sort":"0","addtime":"1507617205","status":"1","del":"1"},{"id":"1","title":"8号展厅","imgurl":"/Public/uploads/news/2017-10-10/59dc67100f488.jpg","sort":"0","addtime":"1507616528","status":"1","del":"1"}]
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
         * id : 5
         * title : 车展
         * imgurl : /Public/uploads/news/2017-10-11/59ddd034497c8.jpg
         * sort : 0
         * addtime : 1507708980
         * status : 1
         * del : 1
         */

        private String id;
        private String title;
        private String imgurl;
        private String sort;
        private String addtime;
        private String status;
        private String del;

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

        public String getSort() {
            return sort;
        }

        public void setSort(String sort) {
            this.sort = sort;
        }

        public String getAddtime() {
            return addtime;
        }

        public void setAddtime(String addtime) {
            this.addtime = addtime;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getDel() {
            return del;
        }

        public void setDel(String del) {
            this.del = del;
        }
    }
}
