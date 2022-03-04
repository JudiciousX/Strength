package Tool;

import java.util.Date;
import java.util.List;

public class DataClass {
    private String uid;//用户id
    private String phone_numbers;//手机号码
    private String username;//用户昵称
    private String signature;//个性签名
    private List<String> label;//标签/爱好
    private short sex;//性别 0女 1男
    private String email;//邮箱
    private String birthday;//生日
    private String head_sculpture;//头像
    private String background;//背景

    public String getBackground() {
        return background;
    }

    public String getBirthday() {
        return birthday;
    }

    public String getEmail() {
        return email;
    }


    public String getHead_sculpture() {
        return head_sculpture;
    }

    public List<String> getLabel() {
        return label;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setHead_sculpture(String head_sculpture) {
        this.head_sculpture = head_sculpture;
    }

    public void setLabel(List<String> label) {
        this.label = label;
    }

    public String getPhone_numbers() {
        return phone_numbers;
    }

    public short getSex() {
        return sex;
    }

    public String getSignature() {
        return signature;
    }

    public String getUid() {
        return uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public void setSex(short sex) {
        this.sex = sex;
    }

    public void setPhone_numbers(String phone_numbers) {
        this.phone_numbers = phone_numbers;
    }
}
