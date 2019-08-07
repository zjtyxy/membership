package cn.lger.service;

import cn.lger.dao.MemberDao;
import cn.lger.dao.OrderDao;
import cn.lger.domain.Member;
import cn.lger.domain.Order;
import cn.lger.domain.Progeress;
import com.github.wxpay.sdk.MyPayConfig;
import com.github.wxpay.sdk.WXPayUtil;
import org.apache.http.client.utils.DateUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.*;

@Service
public class OrderService {
    @Resource
    private OrderDao orderDao;

    @Resource
    private MemberDao memberDao;

    public  String   processPaySuccess(Map<String, String> packageParams ) throws Exception {

       String   resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>"
                + "<return_msg><![CDATA[报文为空]]></return_msg>" + "</xml> ";

        if (packageParams.get("result_code").toString().equalsIgnoreCase("SUCCESS")) {
            System.out.println("微信支付----返回成功");
            if (verifyWeixinNotify(packageParams)) {
                System.out.println("微信支付----验证签名成功");
                resXml = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>"
                        + "<return_msg><![CDATA[OK]]></return_msg>" + "</xml> ";

                String mch_id = (String) packageParams.get("mch_id"); // 商户号
                String openid = (String) packageParams.get("openid"); // 用户标识
                String out_trade_no = (String) packageParams.get("out_trade_no"); // 商户订单号 +随机6位数
                String transaction_id = (String) packageParams.get("transaction_id"); // 微信支付订单号
                Long orderNo = Long.valueOf(out_trade_no.substring(0, out_trade_no.length() - 6)); //还原数据库存放的订单号
                String cash_fee = (String) packageParams.get("cash_fee"); // 实际支付金额（如果有优惠或者没优惠）
                String time_end = (String) packageParams.get("time_end"); //支付完成时间
                Optional<Order> os = orderDao.findById(out_trade_no);
                if (os.isPresent()) {
                    Order order = os.get();
                    order.setStatus(1);
                    order.setTradeState(Order.SUCCESS);
                    order.setTradeStateDesc(Order.StatusDesc.get(Order.SUCCESS));
                    order.setPayEndTime(DateUtils.parseDate(time_end, new String[]{"yyyyMMddhhmmss"}));
                    orderDao.save(order);

                    if (order.getOrderType() == 0)//会费支付
                    {
                        Member member = memberDao.findById(order.getMemberId()).get();
                        Progeress progeress = new Progeress();
                        progeress.setName(Progeress.progressName[3]);
                        progeress.setStatus("accept");
                        Map<String,String> nots = new HashMap<>();
                        nots.put("支付完成时间：", org.thymeleaf.util.DateUtils.format(new Date(),"yyyy-mm-dd HH:MM:ss",Locale.CHINA));
                        progeress.setProgresNote(nots);
                        member.getProgeresses().put(Progeress.progressName[3],progeress);
                        member.setMemberDay(LocalDate.now().plusYears(1));
                        memberDao.save(member);
                    }
                }

            }
        }



        return resXml;
    }

    /**
     * 验证签名
     *
     * @param map
     * @return
     */
    private boolean verifyWeixinNotify(Map<String, String> map) throws Exception {
        SortedMap<String, String> parameterMap = new TreeMap<String, String>();
        String sign = (String) map.get("sign");
        for (Object keyValue : map.keySet()) {
            if (!keyValue.toString().equals("sign")) {
                parameterMap.put(keyValue.toString(), map.get(keyValue).toString());
            }
        }
        String createSign = WXPayUtil.generateSignature(parameterMap, MyPayConfig.getInstance().getKey());
        if (createSign.equals(sign)) {
            return true;
        } else {
            //  logger.error("微信支付  ~~~~~~~~~~~~~~~~验证签名失败");
            return false;
        }


    }
}
