package cn.lger.controller;

import cn.lger.dao.MarketDao;
import cn.lger.dao.MemberDao;
import cn.lger.dao.OrderDao;
import cn.lger.domain.Market;
import cn.lger.domain.Member;
import cn.lger.domain.Order;
import cn.lger.service.OrderService;
import cn.lger.service.WXPayService;
import com.github.wxpay.sdk.WXPayUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.*;


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
    @Resource
    OrderService orderService;

    @RequestMapping("/minapp/findOrder")
    public MiniAppRest findOrder(String id) {
        MiniAppRest rst = new MiniAppRest();
        rst.setResult(orderDao.findById(id).get());
        return rst;
    }

    /**
     * 得到用户所有订单
     *
     * @return
     */
    @RequestMapping("/minapp/getMemberOrder")
    public MiniAppRest gerMemberOrder(String opneid, Integer page) {
        MiniAppRest rst = new MiniAppRest();
        if (page == null) page = 0;
        Page<Order> pages = orderDao.findAll(PageRequest.of(page - 1, 10));
        List<Order> dsda = pages.getContent();
        rst.setResult(pages);

        return rst;
    }

    /**
     * 商品下单
     *
     * @return
     */
    @RequestMapping("/minapp/marketUnified")
    public MiniAppRest unifiedMarketOrder(String openid, Integer productId, Integer productNum, Integer price) {
        MiniAppRest rst = new MiniAppRest();
        rst.setCode(-1);
        rst.setMessage("未知错误");
        Date now = new Date();
        Optional<Market> prduncts = marketDao.findById(productId);
        if (prduncts.isPresent()) {
            Order order = wXPayService.unifiedOrder(openid, now.getTime() + "p", prduncts.get().toString(), price, "https://minapp.tangyuanwenhua.com/minapp/payCallback");
            if (order != null) {
                order.setOrderType(1);
                order.setProductId(productId);
                order.setProductNum(productNum);

                orderDao.save(order);
                rst.setCode(0);
                rst.setMessage("产生成功");
                rst.setResult(order);

            } else {
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
            Order order = wXPayService.unifiedOrder(ds.getOpenid(), new Date().getTime() + "m", "会费支付",Member.levenMoney.get(ds.getPaylevel())*100 , "https://minapp.tangyuanwenhua.com/minapp/payCallback");
            if (order != null) {
                order.setOrderType(0);
                order.setMemberId(memberId);
                orderDao.save(order);
                rst.setResult(order);
                return rst;
            } else {
                rst.setCode(-2);
                rst.setMessage("未能正确产生订单");
                return rst;
            }

        }
        rst.setCode(-1);
        rst.setMessage("未知错误");
        return rst;
    }


    /**
     * 支付成功回调
     *
     * @return
     */
    @RequestMapping("/minapp/payCallback")
    public String payCallback(HttpServletRequest request, HttpServletResponse response) {
        String resXml = "";
        InputStream inStream;
        try {
            inStream = request.getInputStream();
            ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len;
            while ((len = inStream.read(buffer)) != -1) {
                outSteam.write(buffer, 0, len);
            }
            System.out.println("微信支付----付款成功----");
            outSteam.close();
            inStream.close();
            String result = new String(outSteam.toByteArray(), "utf-8");// 获取微信调用我们notify_url的返回信息
            System.out.println("微信支付----result----=" + result);
            Map<String, String> packageParams = WXPayUtil.xmlToMap(result);
            BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());
            out.write(orderService.processPaySuccess(packageParams).getBytes());
            out.flush();
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return resXml;

    }


    @RequestMapping("/minapp/findMarket")
    public Market findMarket(Integer id) {
        Market rst = marketDao.findById(id).get();
        return rst;
    }

    @RequestMapping("/minapp/market")
    public Iterable<Market> market() {
        System.out.println("微信小程序正在调用。。。");
        Iterable<Market> market = marketDao.findAll();
        System.out.println("微信小程序调用完成。。。");
        return market;
    }


}
