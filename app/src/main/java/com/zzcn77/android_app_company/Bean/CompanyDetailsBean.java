package com.zzcn77.android_app_company.Bean;

import java.util.List;

/**
 * Created by 赵磊 on 2017/5/25.
 */

public class CompanyDetailsBean {
    /**
     * stu : 1
     * res : {"lunbo":[{"img":"/Public/uploads/ad/2017-05-23/5923cc87beb85.png"},{"img":"/Public/uploads/ad/2017-05-23/5923cc910aeaf.png"},{"img":"/Public/uploads/ad/2017-05-23/5923cc7ea5724.png"}],"jianjie":{"content":"&lt;h2&gt;【友盟+】 \u2014\u2014 全球领先的第三方全域数据服务商&lt;/h2&gt;&lt;p&gt;\r\n\t\t\r\n\t\t\t&lt;/p&gt;&lt;p&gt;【友盟+】全球领先的第三方全域数据服务商。通过全面覆盖PC、手机、传感器、无线路由器等多种设备数据，打造全域数据平台。秉承独立第三方的数据服务理念，坚持诚信、公正、客观的数据信仰，为客户提供全业务链数据应用解决方案，包括基础统计、运营分析、数据决策和数据业务等，帮助企业实现数据化运营和管理。&lt;/p&gt;&lt;p&gt;&lt;br/&gt;&lt;/p&gt;"}}
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
         * lunbo : [{"img":"/Public/uploads/ad/2017-05-23/5923cc87beb85.png"},{"img":"/Public/uploads/ad/2017-05-23/5923cc910aeaf.png"},{"img":"/Public/uploads/ad/2017-05-23/5923cc7ea5724.png"}]
         * jianjie : {"content":"&lt;h2&gt;【友盟+】 \u2014\u2014 全球领先的第三方全域数据服务商&lt;/h2&gt;&lt;p&gt;\r\n\t\t\r\n\t\t\t&lt;/p&gt;&lt;p&gt;【友盟+】全球领先的第三方全域数据服务商。通过全面覆盖PC、手机、传感器、无线路由器等多种设备数据，打造全域数据平台。秉承独立第三方的数据服务理念，坚持诚信、公正、客观的数据信仰，为客户提供全业务链数据应用解决方案，包括基础统计、运营分析、数据决策和数据业务等，帮助企业实现数据化运营和管理。&lt;/p&gt;&lt;p&gt;&lt;br/&gt;&lt;/p&gt;"}
         */

        private JianjieBean jianjie;
        private List<LunboBean> lunbo;

        public JianjieBean getJianjie() {
            return jianjie;
        }

        public void setJianjie(JianjieBean jianjie) {
            this.jianjie = jianjie;
        }

        public List<LunboBean> getLunbo() {
            return lunbo;
        }

        public void setLunbo(List<LunboBean> lunbo) {
            this.lunbo = lunbo;
        }

        public static class JianjieBean {
            /**
             * content : &lt;h2&gt;【友盟+】 —— 全球领先的第三方全域数据服务商&lt;/h2&gt;&lt;p&gt;

             &lt;/p&gt;&lt;p&gt;【友盟+】全球领先的第三方全域数据服务商。通过全面覆盖PC、手机、传感器、无线路由器等多种设备数据，打造全域数据平台。秉承独立第三方的数据服务理念，坚持诚信、公正、客观的数据信仰，为客户提供全业务链数据应用解决方案，包括基础统计、运营分析、数据决策和数据业务等，帮助企业实现数据化运营和管理。&lt;/p&gt;&lt;p&gt;&lt;br/&gt;&lt;/p&gt;
             */

            private String content;

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }
        }

        public static class LunboBean {
            /**
             * img : /Public/uploads/ad/2017-05-23/5923cc87beb85.png
             */

            private String img;

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }
        }
    }
}
