package com.example.court;

import java.util.List;

public class Article {

    private String msg;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

    private List<Data> data;

    public List<Data> getData() {
        return data;
    }

//    public int getSize



}

    //定义输出返回数据的方法
//    public void show(){
//        System.out.println(uid);
//        System.out.println(username);
//        System.out.println(head_sculpture);
//        System.out.println(article_name);
//        System.out.println(address);
//    }


