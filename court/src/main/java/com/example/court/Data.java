package com.example.court;

/**
 * @创建者 mingyan.su
 * @创建时间 2019/5/31 18:16
 * @类描述 ${TODO}步骤三：回调数据统一封装类
 */
public class Data {

    private String username;
    private String headSculpture;
    private String address;
    private String article_name;
    private String time;
    private String content;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getHead_sculpture() {
        return headSculpture;
    }

    public void setHead_sculpture(String head_sculpture) {
        this.headSculpture = head_sculpture;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getArticle_name() {
        return article_name;
    }

    public void setArticle_name(String article_name) {
        this.article_name = article_name;
    }

}


