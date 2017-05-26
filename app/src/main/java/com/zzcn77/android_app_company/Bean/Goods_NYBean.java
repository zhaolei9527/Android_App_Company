package com.zzcn77.android_app_company.Bean;

/**
 * Created by 赵磊 on 2017/5/26.
 */

public class Goods_NYBean {
    /**
     * stu : 1
     * res : {"title":"安防警报系统套装","img_lb":"/Public/uploads/goods/img_lb/2017-05-24/592524de579eb.png","x_num":"2010X","price":"100.00","pdf":"/Public/uploads/goods/pdf/2017-05-24/592527dbe79c9.pdf","tel":"4009998852","content":"&lt;p&gt;&lt;img src=&quot;/ueditor/php/upload/image/20170524/1495606471543663.png&quot; title=&quot;1495606471543663.png&quot; alt=&quot;图片1.png&quot;/&gt;&lt;/p&gt;"}
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
        /**
         * title : 安防警报系统套装
         * img_lb : /Public/uploads/goods/img_lb/2017-05-24/592524de579eb.png
         * x_num : 2010X
         * price : 100.00
         * pdf : /Public/uploads/goods/pdf/2017-05-24/592527dbe79c9.pdf
         * tel : 4009998852
         * content : &lt;p&gt;&lt;img src=&quot;/ueditor/php/upload/image/20170524/1495606471543663.png&quot; title=&quot;1495606471543663.png&quot; alt=&quot;图片1.png&quot;/&gt;&lt;/p&gt;
         */

        private String title;
        private String img_lb;
        private String x_num;
        private String price;
        private String pdf;
        private String tel;
        private String content;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getImg_lb() {
            return img_lb;
        }

        public void setImg_lb(String img_lb) {
            this.img_lb = img_lb;
        }

        public String getX_num() {
            return x_num;
        }

        public void setX_num(String x_num) {
            this.x_num = x_num;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getPdf() {
            return pdf;
        }

        public void setPdf(String pdf) {
            this.pdf = pdf;
        }

        public String getTel() {
            return tel;
        }

        public void setTel(String tel) {
            this.tel = tel;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
}
