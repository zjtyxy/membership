package cn.lger.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Market  {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    //商品name
    private String name;
    //商品图片地址
    private String url;
    //商品图片跳转地址
    private String link;
    //商品折扣价格
    private String discountPrice;
    //商品原价
    private String normalPrice;
    //商品全称
    private String title;
    //商品轮播图片
    private String[]  imgsUrl;
//    private String userName;
//    private String userDate;
    //商品规格
    private String value;
    //商品数量
    private String num;
    //商品文字介绍
    private String info;
    //商品图片介绍
    private String[]  detailImgs;

    public String[] getDetailImgs() {
        return detailImgs;
    }

    public void setDetailImgs(String[] detailImgs) {
        this.detailImgs = detailImgs;
    }



    public void setImgsUrl(String[] imgsUrl) {
        this.imgsUrl = imgsUrl;
    }

    public String[] getImgsUrl() {
        return imgsUrl;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNormalPrice() {
        return normalPrice;
    }

    public void setNormalPrice(String normalPrice) {
        this.normalPrice = normalPrice;
    }

    public String getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(String discountPrice) {
        this.discountPrice = discountPrice;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

//    public String getUserName() {
//        return userName;
//    }
//
//    public void setUserName(String userName) {
//        this.userName = userName;
//    }
//
//    public String getUserDate() {
//        return userDate;
//    }
//
//    public void setUserDate(String userDate) {
//        this.userDate = userDate;
//    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

}
