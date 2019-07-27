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
    private String  imgsUrl;
    //商品图片1
    private String  imgsUrl1;
    //商品图片2
    private String  imgsUrl2;
    //商品图片3
    private String  imgsUrl3;
    //商品规格
    private String value;
    //商品数量
    private String num;
    //商品图片地址
    private String url;
    //商品图片地址
    private String url1;
    //商品图片地址
    private String url2;
    //商品图片地址
    private String url3;
    //商品图片地址
    private String url4;
    //商品图片地址
    private String url5;
    //商品图片地址
    private String url6;
    //商品图片地址
    private String url7;
    //商品图片地址
    private String url8;
    //商品图片地址
    private String url9;
    //商品图片地址
    private String url10;
    //商品图片地址
    private String url11;
    //商品图片地址
    private String url12;
    //商品图片地址
    private String url13;
    //商品图片地址
    private String url14;
    //商品图片地址
    private String url15;
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

//    public String getInfo() {
//        return info;
//    }
//
//    public void setInfo(String info) {
//        this.info = info;
//    }

    public String getImgsUrl() {
        return imgsUrl;
    }

    public void setImgsUrl(String imgsUrl) {
        this.imgsUrl = imgsUrl;
    }

    public String getImgsUrl1() {
        return imgsUrl1;
    }

    public void setImgsUrl1(String imgsUrl1) {
        this.imgsUrl1 = imgsUrl1;
    }

    public String getImgsUrl2() {
        return imgsUrl2;
    }

    public void setImgsUrl2(String imgsUrl2) {
        this.imgsUrl2 = imgsUrl2;
    }

    public String getImgsUrl3() {
        return imgsUrl3;
    }

    public void setImgsUrl3(String imgsUrl3) {
        this.imgsUrl3 = imgsUrl3;
    }

    public String getUrl1() {
        return url1;
    }

    public void setUrl1(String url1) {
        this.url1 = url1;
    }

    public String getUrl2() {
        return url2;
    }

    public void setUrl2(String url2) {
        this.url2 = url2;
    }

    public String getUrl3() {
        return url3;
    }

    public void setUrl3(String url3) {
        this.url3 = url3;
    }

    public String getUrl4() {
        return url4;
    }

    public void setUrl4(String url4) {
        this.url4 = url4;
    }

    public String getUrl5() {
        return url5;
    }

    public void setUrl5(String url5) {
        this.url5 = url5;
    }

    public String getUrl6() {
        return url6;
    }

    public void setUrl6(String url6) {
        this.url6 = url6;
    }

    public String getUrl7() {
        return url7;
    }

    public void setUrl7(String url7) {
        this.url7 = url7;
    }

    public String getUrl8() {
        return url8;
    }

    public void setUrl8(String url8) {
        this.url8 = url8;
    }

    public String getUrl9() {
        return url9;
    }

    public void setUrl9(String url9) {
        this.url9 = url9;
    }

    public String getUrl10() {
        return url10;
    }

    public void setUrl10(String url10) {
        this.url10 = url10;
    }

    public String getUrl11() {
        return url11;
    }

    public void setUrl11(String url11) {
        this.url11 = url11;
    }

    public String getUrl12() {
        return url12;
    }

    public void setUrl12(String url12) {
        this.url12 = url12;
    }

    public String getUrl13() {
        return url13;
    }

    public void setUrl13(String url13) {
        this.url13 = url13;
    }

    public String getUrl14() {
        return url14;
    }

    public void setUrl14(String url14) {
        this.url14 = url14;
    }

    public String getUrl15() {
        return url15;
    }

    public void setUrl15(String url15) {
        this.url15 = url15;
    }
}
