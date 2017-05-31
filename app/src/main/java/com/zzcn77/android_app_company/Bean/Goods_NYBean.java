package com.zzcn77.android_app_company.Bean;

/**
 * Created by 赵磊 on 2017/5/26.
 */

public class Goods_NYBean {

    /**
     * stu : 1
     * res : {"title":"停车场明星监控套装","img_lb":"/Public/uploads/goods/img_lb/2017-05-24/59252ed636682.png","x_num":"1251","price":"500.00","pdf":"/Public/uploads/goods/pdf/2017-05-24/59252ed637df2.pdf","pdf_title":"监控器必读手册.pdf","tel":"13165828017","content":"&lt;p&gt;&lt;img src=&quot;/ueditor/php/upload/image/20170524/1495609114813429.png&quot; title=&quot;1495609114813429.png&quot; alt=&quot;图片1.png&quot;/&gt;&lt;/p&gt;","coll":"0"}
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
         * title : 停车场明星监控套装
         * img_lb : /Public/uploads/goods/img_lb/2017-05-24/59252ed636682.png
         * x_num : 1251
         * price : 500.00
         * pdf : /Public/uploads/goods/pdf/2017-05-24/59252ed637df2.pdf
         * pdf_title : 监控器必读手册.pdf
         * tel : 13165828017
         * content : &lt;p&gt;&lt;img src=&quot;/ueditor/php/upload/image/20170524/1495609114813429.png&quot; title=&quot;1495609114813429.png&quot; alt=&quot;图片1.png&quot;/&gt;&lt;/p&gt;
         * coll : 0
         */

        private String title;
        private String img_lb;
        private String x_num;
        private String price;
        private String pdf;
        private String pdf_title;
        private String tel;
        private String content;
        private String coll;

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

        public String getPdf_title() {
            return pdf_title;
        }

        public void setPdf_title(String pdf_title) {
            this.pdf_title = pdf_title;
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

        public String getColl() {
            return coll;
        }

        public void setColl(String coll) {
            this.coll = coll;
        }
    }
}
