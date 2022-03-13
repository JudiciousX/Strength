package Tool;

import java.util.Date;
import java.util.List;

public class User {
    private String uid;//用户id
    private String phone_numbers;//手机号码
    private String username;//用户昵称
    private String signature;//个性签名
    private String label;//标签/爱好
    private short sex;//性别 0女 1男
    private String email;//邮箱
    private String birthday;//生日
    private String head_sculpture;//头像
    private String background;//背景

    public User() {
    }

    public User(String uid, String phone_numbers, String username, String signature, String label,
                short sex, String email, String birthday, String head_sculpture, String background) {
        this.uid = uid;
        this.phone_numbers = phone_numbers;
        this.username = username;
        this.signature = signature;
        this.label = label;
        this.sex = sex;
        this.email = email;
        this.birthday = birthday;
        this.head_sculpture = head_sculpture;
        this.background = background;
    }

    public String getUid() {
        return uid;
    }

    public String getPhoneNumbers() {
        return phone_numbers;
    }

    public String getUsername() {
        return username;
    }

    public String getSignature() {
        return signature;
    }

    public String getLabel() {
        return label;
    }

    public short getSex() {
        return sex;
    }

    public String getEmail() {
        return email;
    }

    public String getBirthday() {
        return birthday;
    }

    public String getHead_sculpture() {
        return head_sculpture;
    }

    public String getBackground() {
        return background;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setPhoneNumbers(String phoneNumbers) {
        this.phone_numbers = phoneNumbers;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setSex(short sex) {
        this.sex = sex;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public void setHead_sculpture(String head_sculpture) {
        this.head_sculpture = head_sculpture;
    }

    public void setBackground(String background) {
        this.background = background;
    }

}
