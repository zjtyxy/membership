package cn.lger.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name = "shiporder")
public class Order {
    static public String SUCCESS="SUCCESS";//支付成功
    static public String REFUND="REFUND";//支转入退款
    static public String NOTPAY="NOTPAY";//支未支付
    static public String CLOSED="CLOSED";//支已关闭
    static public String REVOKED="REVOKED";//支已撤销（付款码支付）
    static public String USERPAYING="USERPAYING";//支用户支付中（付款码支付）
    static public String PAYERROR="PAYERROR";//支支付失败(其他原因，如银行返回失败)
    static public Map<String,String>  StatusDesc =  new HashMap<>();
    static {
        StatusDesc.put("SUCCESS","支付成功");
        StatusDesc.put("REFUND","支转入退款");
        StatusDesc.put("NOTPAY","支未支付");
        StatusDesc.put("CLOSED","支已关闭");
        StatusDesc.put("REVOKED","支已撤销");
        StatusDesc.put("USERPAYING","支用户支付中");
        StatusDesc.put("PAYERROR","PAYERROR");
    }
    @Id
    private  String  id;
    private  String  openid;
    private  String  body;
    private  String  nonceStr;
    private  String  outTradeNo;
    private  String  prePayId;
    private  String  sign;
    private  String  signType;
    private  int  status;
    private  String timeStamp;
    //支付完成时间
    private Date payEndTime;
    private  int  totalFee;
    //订单状态
    private  String  tradeState;
    private  String  tradeStateDesc;

    //商品id
    private  String   memberId;
    private  Integer  productId;
    private  Integer  productNum;
    //订单类型0会费，1.产品，2活动
    private  Integer  orderType;

    public Integer getOrderType() {
        return orderType;
    }

    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getProductNum() {
        return productNum;
    }

    public void setProductNum(Integer productNum) {
        this.productNum = productNum;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getNonceStr() {
        return nonceStr;
    }

    public void setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public String getPrePayId() {
        return prePayId;
    }

    public void setPrePayId(String prePayId) {
        this.prePayId = prePayId;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getSignType() {
        return signType;
    }

    public void setSignType(String signType) {
        this.signType = signType;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public int getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(int totalFee) {
        this.totalFee = totalFee;
    }

    public String getTradeState() {
        return tradeState;
    }

    public void setTradeState(String tradeState) {
        this.tradeState = tradeState;
    }

    public String getTradeStateDesc() {
        return tradeStateDesc;
    }

    public void setTradeStateDesc(String tradeStateDesc) {
        this.tradeStateDesc = tradeStateDesc;
    }

    public Date getPayEndTime() {
        return payEndTime;
    }

    public void setPayEndTime(Date payEndTime) {
        this.payEndTime = payEndTime;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }
}
