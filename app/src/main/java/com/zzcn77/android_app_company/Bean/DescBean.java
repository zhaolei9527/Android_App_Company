package com.zzcn77.android_app_company.Bean;

/**
 * Created by 赵磊 on 2017/10/19.
 */

public class DescBean {

    /**
     * stu : 1
     * res : {"content":"消息称，由于南茂科技与奇景光电合作进入了iPhone \r\nX的供应链，所以南茂科技从WLO芯片订单中获得的收入预计将在今年晚些时候显著增长。南茂科技从WLO芯片订单中获得的收入预计介于新台币5000万元（约合166万美元）至新台币6000万元，高于目前的新台币2000万元至新台币3000万元。"}
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
         * content : 消息称，由于南茂科技与奇景光电合作进入了iPhone
         X的供应链，所以南茂科技从WLO芯片订单中获得的收入预计将在今年晚些时候显著增长。南茂科技从WLO芯片订单中获得的收入预计介于新台币5000万元（约合166万美元）至新台币6000万元，高于目前的新台币2000万元至新台币3000万元。
         */

        private String content;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
}
