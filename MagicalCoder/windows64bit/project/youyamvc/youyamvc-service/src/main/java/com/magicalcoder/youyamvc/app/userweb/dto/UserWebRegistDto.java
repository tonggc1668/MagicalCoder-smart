package com.magicalcoder.youyamvc.app.userweb.dto;

import java.util.Date;

/**
 * Created by Administrator on 2016/1/18.
 */
public class UserWebRegistDto {
    private String twoCodeImgSrc;//二维码图片
    private String nickname;//昵称
    private String realName;//用户真名
    private Integer sex;//性别
    private Date birthday;//生日
    private Integer babySex;//大宝宝性别
    private Date babyBirthday;//大宝宝生日
    private String headImgSrc;//头像地址

    public String getTwoCodeImgSrc() {
        return twoCodeImgSrc;
    }

    public void setTwoCodeImgSrc(String twoCodeImgSrc) {
        this.twoCodeImgSrc = twoCodeImgSrc;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Integer getBabySex() {
        return babySex;
    }

    public void setBabySex(Integer babySex) {
        this.babySex = babySex;
    }

    public Date getBabyBirthday() {
        return babyBirthday;
    }

    public void setBabyBirthday(Date babyBirthday) {
        this.babyBirthday = babyBirthday;
    }

    public String getHeadImgSrc() {
        return headImgSrc;
    }

    public void setHeadImgSrc(String headImgSrc) {
        this.headImgSrc = headImgSrc;
    }
}
