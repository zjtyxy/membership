package cn.lger.domain;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.*;

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
    //活动类型目前两种。"研学"，"征文"
    private String atype ="研学";

    @OneToMany(fetch = FetchType.EAGER,cascade=CascadeType.ALL)
    private Map<Integer,DayTrip> routingday = new HashMap<>();
    //报名截止日期
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private LocalDate endDate;
    //最大参见人数
    private Integer   maxNumber;
    //活动费用
    private Integer   money;
    //报名方式
    private String   signupMode;
    //其他增值服务
    @ElementCollection(fetch = FetchType.EAGER)
    private Map<String,String> increment;
    //活动天数
    private  Integer  days;
    //活动主图片
    private String link;
    //活动相册
    @ElementCollection(fetch = FetchType.EAGER)
     private Set<String> imageurls = new HashSet<>();

     private  String userName;
     private  String userDate;

    public Integer getMoney() {
        return money;
    }

    public void setMoney(Integer money) {
        this.money = money;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserDate() {
        return userDate;
    }

    public void setUserDate(String userDate) {
        this.userDate = userDate;
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

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Map<Integer, DayTrip> getRoutingday() {
        return routingday;
    }

    public void setRoutingday(Map<Integer, DayTrip> routingday) {
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

    public Map<String, String> getIncrement() {
        return increment;
    }

    public void setIncrement(Map<String, String> increment) {
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

    public Set<String> getImageurls() {
        return imageurls;
    }

    public void setImageurls(Set<String> imageurls) {
        this.imageurls = imageurls;
    }

    public String getAtype() {
        return atype;
    }

    public void setAtype(String atype) {
        this.atype = atype;
    }
}
