package com.zzcn77.android_app_company.Bean;

/**
 * Created by 赵磊 on 2017/5/25.
 */

public class LoginBean {

    /**
     * stu : 1
     * msg : 登陆成功！
     * res : {"id":"10000","username":"yulian","tel":"15111111112","email":"152@163.com","password":"9db06bcff9248837f86d1a6bcf41c9e7","add_time":"1508218418","status":"1","is_show":"1","sex":"1","headimg":null,"last_login_time":"1508316276","type":"2","sh_id":"10000"}
     */

    private String stu;
    private String msg;
    private ResBean res;

    public String getStu() {
        return stu;
    }

    public void setStu(String stu) {
        this.stu = stu;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ResBean getRes() {
        return res;
    }

    public void setRes(ResBean res) {
        this.res = res;
    }

    public static class ResBean {
        /**
         * id : 10000
         * username : yulian
         * tel : 15111111112
         * email : 152@163.com
         * password : 9db06bcff9248837f86d1a6bcf41c9e7
         * add_time : 1508218418
         * status : 1
         * is_show : 1
         * sex : 1
         * headimg : null
         * last_login_time : 1508316276
         * type : 2
         * sh_id : 10000
         */

        private String id;
        private String username;
        private String tel;
        private String email;
        private String password;
        private String add_time;
        private String status;
        private String is_show;
        private String sex;
        private Object headimg;
        private String last_login_time;
        private String type;
        private String sh_id;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getTel() {
            return tel;
        }

        public void setTel(String tel) {
            this.tel = tel;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getAdd_time() {
            return add_time;
        }

        public void setAdd_time(String add_time) {
            this.add_time = add_time;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getIs_show() {
            return is_show;
        }

        public void setIs_show(String is_show) {
            this.is_show = is_show;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public Object getHeadimg() {
            return headimg;
        }

        public void setHeadimg(Object headimg) {
            this.headimg = headimg;
        }

        public String getLast_login_time() {
            return last_login_time;
        }

        public void setLast_login_time(String last_login_time) {
            this.last_login_time = last_login_time;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getSh_id() {
            return sh_id;
        }

        public void setSh_id(String sh_id) {
            this.sh_id = sh_id;
        }
    }
}
