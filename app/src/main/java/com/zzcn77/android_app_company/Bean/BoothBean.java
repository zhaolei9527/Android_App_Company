package com.zzcn77.android_app_company.Bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 赵磊 on 2017/10/17.
 */

public class BoothBean implements Serializable {

    /**
     * stu : 1
     * res : [{"id":"25","title":"劳斯莱斯","number":"A厅9号","x":"33","y":"2","q_num":"10000","eid":"5","hangye":"车行","addtime":"1507784853","del":"1"},{"id":"27","title":"宝马","number":"A厅11号","x":"10","y":"20","q_num":"10000","eid":"5","hangye":"车行","addtime":"1507772219","del":"1"},{"id":"26","title":"奔驰","number":"A厅10号","x":"10","y":"10","q_num":"10000","eid":"5","hangye":"车行","addtime":"1507771859","del":"1"},{"id":"24","title":"A厅6号","number":"222","x":"11","y":"2","q_num":"10000","eid":"4","hangye":"互联网科技","addtime":"1507708370","del":"1"},{"id":"20","title":"人体艺术","number":"001","x":"1","y":"1","q_num":"","eid":"4","hangye":"艺术","addtime":"1507698632","del":"1"},{"id":"17","title":"汽车展位","number":"q1","x":"1","y":"2","q_num":null,"eid":"4","hangye":"汽车","addtime":"1507698610","del":"1"},{"id":"18","title":"摩托车展位","number":"m1","x":"2","y":"1","q_num":null,"eid":"4","hangye":"摩托","addtime":"1507698610","del":"1"},{"id":"19","title":"飞机展位","number":"f1","x":"3","y":"2","q_num":null,"eid":"4","hangye":"飞机","addtime":"1507698610","del":"1"},{"id":"14","title":"汽车展位","number":"q1","x":"1","y":"2","q_num":null,"eid":"1","hangye":"汽车","addtime":"1507698473","del":"1"},{"id":"15","title":"摩托车展位","number":"m1","x":"2","y":"1","q_num":null,"eid":"1","hangye":"摩托","addtime":"1507698473","del":"1"}]
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

    public static class ResBean implements Serializable {
        /**
         * id : 25
         * title : 劳斯莱斯
         * number : A厅9号
         * x : 33
         * y : 2
         * q_num : 10000
         * eid : 5
         * hangye : 车行
         * addtime : 1507784853
         * del : 1
         */

        private String id;
        private String title;
        private String number;
        private String x;
        private String y;
        private String q_num;
        private String eid;
        private String hangye;
        private String addtime;
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

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public String getX() {
            return x;
        }

        public void setX(String x) {
            this.x = x;
        }

        public String getY() {
            return y;
        }

        public void setY(String y) {
            this.y = y;
        }

        public String getQ_num() {
            return q_num;
        }

        public void setQ_num(String q_num) {
            this.q_num = q_num;
        }

        public String getEid() {
            return eid;
        }

        public void setEid(String eid) {
            this.eid = eid;
        }

        public String getHangye() {
            return hangye;
        }

        public void setHangye(String hangye) {
            this.hangye = hangye;
        }

        public String getAddtime() {
            return addtime;
        }

        public void setAddtime(String addtime) {
            this.addtime = addtime;
        }

        public String getDel() {
            return del;
        }

        public void setDel(String del) {
            this.del = del;
        }
    }
}
