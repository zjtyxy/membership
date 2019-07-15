package cn.lger.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Code that Changed the World
 * Pro said
 * Created by Pro on 2017-12-06.
 */
@Entity
public class Member implements Serializable {

    @Id
    @GeneratedValue(generator="id")
    @GenericGenerator(name="id",strategy="uuid")
    private String id;
    private String iconPath;

    private String password;



    //会员等级
    @ManyToOne
    private MemberGrade memberGrade;

    public Map<String,Progeress> getProgeresses() {
        if(progeresses == null)
            progeresses = new HashMap<>();
        return progeresses;
    }

    public void setProgeresses(Map<String,Progeress> progeresses) {
        this.progeresses = progeresses;
    }

    @OneToMany(cascade = CascadeType.ALL)
    private Map<String,Progeress> progeresses;
    //会员积分
    private Long memberIntegral;
    //会员余额
    private Float balance;
    //会员状态 挂失、停用、正常
    private String state;

   @Transient
    public String getApprovedState()
    {
        String rst = null;
        if(this.getProgeresses().get(Progeress.progressName[5])!=null)
        {
          return Progeress.progressName[5];
        }
        else {
            for (String pa : Progeress.progressName) {
                Progeress pg = this.getProgeresses().get(pa);
                if (pg == null ) {
                    return pa;
                }
            }
        }
        return Progeress.progressName[4];
    }



    private String openid;// 微信用户唯一标示
    private String memberName;

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    private String zhiwu;
    private String zhengzhimianmao;
    private String sex;
    private LocalDate birthday;
    private String minzu;
    private String shenfenzheng;
    private String xueli;
    private String email;
    private String phone;
    private String gongzuodanwei;
    private String jiankangzhuangkuang;
    private String address;
    private String youzhengbianma;
    private String huodongjianjie;
    private String jinengtechang;
    private String fuwuyixiang;
    private String zhiye;

    public String getZhiwu() {
        return zhiwu;
    }

    public void setZhiwu(String zhiwu) {
        this.zhiwu = zhiwu;
    }

    public String getZhengzhimianmao() {
        return zhengzhimianmao;
    }

    public void setZhengzhimianmao(String zhengzhimianmao) {
        this.zhengzhimianmao = zhengzhimianmao;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public String getMinzu() {
        return minzu;
    }

    public void setMinzu(String minzu) {
        this.minzu = minzu;
    }

    public String getShenfenzheng() {
        return shenfenzheng;
    }

    public void setShenfenzheng(String shenfenzheng) {
        this.shenfenzheng = shenfenzheng;
    }

    public String getXueli() {
        return xueli;
    }

    public void setXueli(String xueli) {
        this.xueli = xueli;
    }

    public String getGongzuodanwei() {
        return gongzuodanwei;
    }

    public void setGongzuodanwei(String gongzuodanwei) {
        this.gongzuodanwei = gongzuodanwei;
    }

    public String getJiankangzhuangkuang() {
        return jiankangzhuangkuang;
    }

    public void setJiankangzhuangkuang(String jiankanzhuangkuang) {
        this.jiankangzhuangkuang = jiankanzhuangkuang;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getYouzhengbianma() {
        return youzhengbianma;
    }

    public void setYouzhengbianma(String youzhengbianma) {
        this.youzhengbianma = youzhengbianma;
    }

    public String getHuodongjianjie() {
        return huodongjianjie;
    }

    public void setHuodongjianjie(String huodongjianjie) {
        this.huodongjianjie = huodongjianjie;
    }

    public String getJinengtechang() {
        return jinengtechang;
    }

    public void setJinengtechang(String jinengtechang) {
        this.jinengtechang = jinengtechang;
    }

    public String getFuwuyixiang() {
        return fuwuyixiang;
    }

    public void setFuwuyixiang(String fuwuyixiang) {
        this.fuwuyixiang = fuwuyixiang;
    }

    public String getZhiye() {
        return zhiye;
    }

    public void setZhiye(String zhiye) {
        this.zhiye = zhiye;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIconPath() {
        return iconPath;
    }

    public void setIconPath(String iconPath) {
        this.iconPath = iconPath;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBirthday() {
        return birthday.toString();
    }

    public void setBirthday(String birthday) {
        this.birthday = LocalDate.parse(birthday);
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public MemberGrade getMemberGrade() {
        return memberGrade;
    }

    public void setMemberGrade(MemberGrade memberGrade) {
        this.memberGrade = memberGrade;
    }

    public Long getMemberIntegral() {
        return memberIntegral;
    }

    public void setMemberIntegral(Long memberIntegral) {
        this.memberIntegral = memberIntegral;
    }

    public Float getBalance() {
        return balance;
    }

    public void setBalance(Float balance) {
        this.balance = balance;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public LocalDate getLocalDate(){
        return this.birthday;
    }

    @Override
    public String toString() {
        return "Member{" +
                "id='" + id + '\'' +
                ", iconPath='" + iconPath + '\'' +
                ", memberName='" + memberName + '\'' +
                ", password='" + password + '\'' +
                ", phone='" + phone + '\'' +
                ", birthday=" + birthday +
                ", sex='" + sex + '\'' +
                ", memberGrade=" + memberGrade +
                ", memberIntegral=" + memberIntegral +
                ", balance=" + balance +
                ", state='" + state + '\'' +
                '}';
    }
}
