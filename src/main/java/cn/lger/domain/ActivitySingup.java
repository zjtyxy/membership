package cn.lger.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;
import java.util.Date;

/**
 * 活动报名情况
 */
@Entity
public class ActivitySingup {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    //报名时间
    private Date singupTime;
    //会员id
    private String  memberId;

    //支付完成时间
    private Date payTime;

    private Integer payId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getSingupTime() {
        return singupTime;
    }

    public void setSingupTime(Date singupTime) {
        this.singupTime = singupTime;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    public Integer getPayId() {
        return payId;
    }

    public void setPayId(Integer payId) {
        this.payId = payId;
    }
}
