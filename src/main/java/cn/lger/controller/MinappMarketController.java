package cn.lger.controller;

import cn.lger.dao.MarketDao;
import cn.lger.dao.MemberDao;
import cn.lger.dao.OrderDao;
import cn.lger.domain.Market;
import cn.lger.domain.Member;
import cn.lger.domain.Order;
import cn.lger.service.WXPayService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;
@RestController
public class MinappMarketController {
    @Resource
    private OrderDao orderDao;
    @Resource
    WXPayService wXPayService;
    @Resource
    private MemberDao memberDao;
    @Resource
    MarketDao marketDao;

    /**
     * 支付下单
     *
     * @return
     */
    @RequestMapping("/minapp/unifiedorder")
    public String addOrder(String memberId) {
        try {
            Optional<Member> rst = memberDao.findById(memberId);
            if (rst.isPresent()) {
                Member ds = rst.get();
                Order order = wXPayService.unifiedOrder(ds.getOpenid(), ds.getShenfenzheng() + new Date().getYear() + "m", "会费支付", 10);
            }
//           if(order!=null) {
//               orderDao.save(order);
//           }
//           orderDao.save(order);
            return "success";
        } catch (Exception e) {

        }
        return "fail";
    }

    @RequestMapping("/minapp/findOrder")
    public Order findOrder(String id) {
        Order rst = orderDao.findById(id).get();
        return rst;
    }

    /**
     * 得到用户所有订单
     * @return
     */
    @RequestMapping("/minapp/getMemberOrder")
    public Iterable<Order> gerMemberOrder(String opneid) {
          return  orderDao.findByOpenid(opneid);
    }

    /**
     * 商品下单
     * @return
     */
    @RequestMapping("/minapp/marketUnified")
    public MiniAppRest unifiedMarketOrder(String openid,Integer productId,Integer productNum,Integer price) {
        MiniAppRest rst = new MiniAppRest();
        rst.setCode(-1);
        rst.setMessage("未知错误");
        Date now = new Date();
        Optional<Market> prduncts = marketDao.findById(productId);
        if(prduncts.isPresent())
        {
            Order order = wXPayService.unifiedOrder(openid, now.getTime()+"p", prduncts.get().toString(), price);
            if (order != null) {
                order.setOrderType(1);
                order.setProductId(productId);
                order.setProductNum(productNum);

                orderDao.save(order);
                rst.setCode(0);
                rst.setMessage("产生成功");
                rst.setResult(order);

            }
            else
            {
                rst.setCode(-2);
                rst.setMessage("未能正确产生订单");

            }

        }

        return rst;
    }

    /***
     * 会费下单
     * @param memberId
     * @return
     */
    @RequestMapping("/minapp/memberUnified")
    public MiniAppRest memberUnified(String memberId) {
        MiniAppRest rst = new MiniAppRest();
        Optional<Member> memberp = memberDao.findById(memberId);
        if (memberp.isPresent()) {
            Member ds = memberp.get();
            Order order = wXPayService.unifiedOrder(ds.getOpenid(), ds.getShenfenzheng() + new Date().getYear() + "m", "会费支付", 10);
            if (order != null) {
                order.setOrderType(0);
                orderDao.save(order);
                rst.setResult(order);
            }
            else
            {
                rst.setCode(-2);
                rst.setMessage("未能正确产生订单");
            }

        }
        rst.setCode(-1);
        rst.setMessage("未知错误");
        return rst;
    }

    /**
     * 活动下单
     * @return
     */
    @RequestMapping("/minapp/activityUnified")
    public Order gerOrder(String openid,Integer activityId,Integer price) {
//        Optional<Member> rst = memberDao.findById(memberId);
//        if (rst.isPresent()) {
//            Member ds = rst.get();
//            Order order = wXPayService.unifiedOrder(ds.getOpenid(), ds.getShenfenzheng() + new Date().getYear() + "m", "会费支付", 10);
//            if (order != null) {
//                order.setOrderType(0);
//                orderDao.save(order);
//                return order;
//            }
//
//        }
        return null;
    }

    /**
     * 支付成功回调
     * @return
     */
    @RequestMapping("/minapp/payCallback")
    public String payCallback() {

        return null;
    }
}
