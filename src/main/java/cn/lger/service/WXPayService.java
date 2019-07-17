package cn.lger.service;

import cn.lger.domain.Member;
import cn.lger.domain.Order;
import com.github.wxpay.sdk.MyPayConfig;
import com.github.wxpay.sdk.WXPay;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Service
public class WXPayService {
    public Order unifiedOrder(String openid, String tradeno, String orderDesc, int price) {
        try {
            WXPay wxpay = new WXPay(MyPayConfig.getInstance());
            Map<String, String> data = new HashMap<String, String>();
            data.put("body", orderDesc);
            data.put("out_trade_no", tradeno); // 订单唯一编号, 不允许重复
            data.put("total_fee", price+""); // 订单金额, 单位分
            data.put("spbill_create_ip", "192.168.31.166"); // 下单ip
            data.put("openid", openid); // 微信公众号统一标示openid
            data.put("notify_url", "http://wxlj.oopmind.com/payCallback"); // 订单结果通知, 微信主动回调此接口
            data.put("trade_type", "JSAPI"); // 固定填写
            Map<String, String> dd = wxpay.unifiedOrder(data);
            if(dd.get("return_code").equals("SUCCESS") && dd.get("result_code").equals("SUCCESS"))
            {
                Order order = new Order();
                order.setId(tradeno);
                order.setOpenid(openid);
                order.setBody(orderDesc);
                order.setNonceStr(dd.get("nonce_str"));
                order.setOutTradeNo(tradeno);
                order.setTotalFee(price);
                order.setPrePayId(dd.get("prepay_id"));
                order.setSign(dd.get("sign"));
//
//              //  order.setStatus(dd.get("return_code"));
//                order.setTimeStamp(dd.get("return_code"));
//                order.setTradeState(dd.get("return_code"));
//                order.setSignType(dd.get("return_code"));
//                order.setTradeStateDesc(dd.get("return_code"));
              return  order;

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return  null;
    }

    public String  orderQuery(String tradeno) {
        try {
            WXPay wxpay = new WXPay(MyPayConfig.getInstance());
            Map<String, String> data = new HashMap<String, String>();
            data.put("out_trade_no", tradeno); // 订单唯一编号, 不允许重复
            data.put("spbill_create_ip", "192.168.31.166"); // 下单ip
            Map<String, String> dd = wxpay.orderQuery(data);
            //{nonce_str=mulJF0C3DwwDlTwD, device_info=, out_trade_no=402881e96bf8d155016bf8f3298d0000, trade_state=NOTPAY, appid=wx509992ee3584678d, total_fee=1, sign=87DAF6FCB2CE16FBBAACAAEC08CE5ED644647137114F34902FA5DBFB1A4D64DC, trade_state_desc=订单未支付, return_msg=OK, result_code=SUCCESS, mch_id=1543333401, return_code=SUCCESS}
          if(dd.get("return_code").equals("SUCCESS"))
          {
              return dd.get("out_trade_no");
          }
        } catch (Exception e) {
            e.printStackTrace();
        }
       return null;

    }

    public void closeOrder(String tradeno) {
        try {
            WXPay wxpay = new WXPay(MyPayConfig.getInstance());
            Map<String, String> data = new HashMap<String, String>();
            data.put("out_trade_no", tradeno); // 订单唯一编号, 不允许重复
            Map<String, String> dd = wxpay.closeOrder(data);
            System.out.println(dd);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
