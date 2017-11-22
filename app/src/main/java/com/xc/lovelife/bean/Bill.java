package com.xc.lovelife.bean;

import android.widget.ImageView;

import java.util.Date;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.datatype.BmobFile;


/**
 * Created by xum19 on 2017/11/17.
 */
public class Bill extends BmobObject {
    private String userName;
    private BmobFile pic;
    private String type;
    private String date;
    private String note;
    private Integer amount;

    public Bill(){

    }


    public Bill(String userName, BmobFile pic, String type, String date, String note, Integer amount){

        this.userName = userName;
        this.pic = pic;
        this.type = type;
        this.date = date;
        this.note = note;
        this.amount = amount;

    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public BmobFile getPic() {
        return pic;
    }

    public void setPic(BmobFile pic) {
        this.pic = pic;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setData(String date) {
        this.date = date;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }
}
