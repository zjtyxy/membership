package cn.lger.domain;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
public class Activity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    //活动名称
    private String name;
    //活动中介绍
    @Column(length = 50000)
    private String info;
    //行程列表

    @ElementCollection
    private Map<String,String> routingday =new HashMap<String,String>();
    //报名截止日期
    private LocalDate endDate;
    //最大参见人数
    private Integer   maxNumber;
    //报名方式
    private String   signupMode;
    //其他增值服务
    @ElementCollection
    private Map<Integer,String> increment;
    //活动天数
    private  Integer  days;
    //活动主图片
    private String link;
    //活动相册
    @ElementCollection
     private  List<String> imageurls;


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

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Map<String, String> getRoutingday() {
        return routingday;
    }

    public void setRoutingday(Map<String, String> routingday) {
        this.routingday = routingday;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Integer getMaxNumber() {
        return maxNumber;
    }

    public void setMaxNumber(Integer maxNumber) {
        this.maxNumber = maxNumber;
    }

    public String getSignupMode() {
        return signupMode;
    }

    public void setSignupMode(String signupMode) {
        this.signupMode = signupMode;
    }

    public Map<Integer, String> getIncrement() {
        return increment;
    }

    public void setIncrement(Map<Integer, String> increment) {
        this.increment = increment;
    }

    public Integer getDays() {
        return days;
    }

    public void setDays(Integer days) {
        this.days = days;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public List<String> getImageurls() {
        return imageurls;
    }

    public void setImageurls(List<String> imageurls) {
        this.imageurls = imageurls;
    }
}
