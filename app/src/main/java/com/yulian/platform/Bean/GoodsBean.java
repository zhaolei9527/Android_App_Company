package com.yulian.platform.Bean;

import java.util.List;

/**
 * Created by 赵磊 on 2017/5/27.
 */

public class GoodsBean {

    /**
     * stu : 1
     * res : {"cate":[{"title":"视频监控系统","img":"/Public/uploads/cate/2017-05-23/5923f57e4554f.png","id":"4"},{"title":"楼宇对讲系统","img":"/Public/uploads/cate/2017-05-23/5923f5694603e.png","id":"5"},{"title":"停车场系统","img":"/Public/uploads/cate/2017-05-23/5923f598cbfad.png","id":"1"},{"title":"安防警报系统","img":"/Public/uploads/cate/2017-05-23/5923f5c6186fa.png","id":"2"},{"title":"综合布线系统","img":"/Public/uploads/cate/2017-05-23/5923f5ddb0dda.png","id":"3"},{"title":"有线电视系统","img":"/Public/uploads/cate/2017-05-23/5923f5f85476c.png","id":"6"}],"brand":[{"title":"天地伟业","id":"4"},{"title":"海康威视Hikvision","id":"5"},{"title":"泰科安防Tyco","id":"1"},{"title":"齐盛科技","id":"2"},{"title":"深圳帝之特","id":"3"},{"title":"深圳视鑫电子","id":"6"}],"px":[{"w_px":"352","h_px":"288","id":"4"},{"w_px":"704","h_px":"546","id":"5"},{"w_px":"960","h_px":"576","id":"1"},{"w_px":"1280","h_px":"720","id":"2"},{"w_px":"1920","h_px":"1080","id":"3"},{"w_px":"960","h_px":"600","id":"6"}],"goodsmx":[{"title":"停车场明星监控套装","img":"/Public/uploads/goods/img/2017-05-24/59252ed637622.png","id":"5"},{"title":"start","img":"/Public/uploads/goods/img/2017-05-25/59268a45b6278.png","id":"7"},{"title":"视频监控明星产品","img":"/Public/uploads/goods/img/2017-05-24/5925302bdb558.png","id":"6"}]}
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
        private List<CateBean> cate;
        private List<BrandBean> brand;
        private List<PxBean> px;
        private List<GoodsmxBean> goodsmx;

        public List<CateBean> getCate() {
            return cate;
        }

        public void setCate(List<CateBean> cate) {
            this.cate = cate;
        }

        public List<BrandBean> getBrand() {
            return brand;
        }

        public void setBrand(List<BrandBean> brand) {
            this.brand = brand;
        }

        public List<PxBean> getPx() {
            return px;
        }

        public void setPx(List<PxBean> px) {
            this.px = px;
        }

        public List<GoodsmxBean> getGoodsmx() {
            return goodsmx;
        }

        public void setGoodsmx(List<GoodsmxBean> goodsmx) {
            this.goodsmx = goodsmx;
        }

        public static class CateBean {
            private boolean ischeck=false;

            public boolean getIscheck() {
                return ischeck;
            }

            public void setIscheck(boolean ischeck) {
                this.ischeck = ischeck;
            }

            /**
             * title : 视频监控系统
             * img : /Public/uploads/cate/2017-05-23/5923f57e4554f.png
             * id : 4
             */

            private String title;
            private String img;
            private String id;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }
        }

        public static class BrandBean {
            /**
             * title : 天地伟业
             * id : 4
             */

            private boolean ischeck=false;

            public boolean getIscheck() {
                return ischeck;
            }

            public void setIscheck(boolean ischeck) {
                this.ischeck = ischeck;
            }
            private String title;
            private String id;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }
        }

        public static class PxBean {
            /**
             * w_px : 352
             * h_px : 288
             * id : 4
             */
            private boolean ischeck=false;

            public boolean getIscheck() {
                return ischeck;
            }

            public void setIscheck(boolean ischeck) {
                this.ischeck = ischeck;
            }

            public String getPx() {
                return px;
            }

            public void setPx(String px) {
                this.px = px;
            }

            private String px;
            private String id;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }
        }

        public static class GoodsmxBean {
            /**
             * title : 停车场明星监控套装
             * img : /Public/uploads/goods/img/2017-05-24/59252ed637622.png
             * id : 5
             */

            private String title;
            private String img_lb;
            private String id;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getImg_lb() {
                return img_lb;
            }

            public void setImg(String Img_lb) {
                this.img_lb = Img_lb;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }
        }
    }
}
