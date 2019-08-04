package cn.lger.domain;

import javax.persistence.*;
import java.util.List;

@Entity
public class Market  {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    //商品名称
    private String name;
    //商品图片
    private String link;
    //商品折扣价格
    private String discountPrice;
    //商品原价
    private String normalPrice;
    //商品全称
    private String title;
    //商品图片
    @ElementCollection
    private List<String> imgsUrl;
    //商品规格
    private String value;
    //商品数量
    private String num;
    //商品图片地址
    @ElementCollection
    private List<String> url;
    //商品图片地址

//    //商品文字介绍
//    private String info;

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

    public List<String> getImgsUrl() {
        return imgsUrl;
    }

    public void setImgsUrl(List<String> imgsUrl) {
        this.imgsUrl = imgsUrl;
    }

    public List<String> getUrl() {
        return url;
    }

    public void setUrl(List<String> url) {
        this.url = url;
    }
//    public String getInfo() {
//        return info;
//    }
//
//    public void setInfo(String info) {
//        this.info = info;
//    }


    @Override
    public String toString() {
        return "Market{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", discountPrice='" + discountPrice + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
