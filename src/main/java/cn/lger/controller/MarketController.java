package cn.lger.controller;

import cn.lger.dao.MemberDao;
import cn.lger.dao.OrderDao;
import cn.lger.domain.Member;
import cn.lger.domain.Order;
import cn.lger.service.WXPayService;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Optional;

public class MarketController {
    @Resource
    private OrderDao orderDao;
    @Resource
    WXPayService wXPayService;
    @Resource
    private MemberDao memberDao;

    /**
     * 支付下单
     *
     * @param order
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

    @RequestMapping("/minapp/getorder")
    public Order gerOrder(String memberId) {
        Optional<Member> rst = memberDao.findById(memberId);
        if (rst.isPresent()) {
            Member ds = rst.get();
            Order order = wXPayService.unifiedOrder(ds.getOpenid(), ds.getShenfenzheng() + new Date().getYear() + "m", "会费支付", 10);
            if (order != null) {
                orderDao.save(order);
                return order;
            }

        }
        return null;
    }
}
