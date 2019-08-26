package cn.lger.web;

import cn.lger.domain.GoodsOrders;
import cn.lger.service.GoodsOrdersService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
public class GoodsOrdersController {

    @Resource
    private GoodsOrdersService goodsOrdersService;

    @GetMapping("/goodsOrders")
    public String getGoodsOrders(){
        return "goodsOrders";
    }

    @PostMapping("/goodsOrders")
    @ResponseBody
    public Page<GoodsOrders> goodsOrders(Integer currentPage, String marketId){
        if (currentPage == null || currentPage <0)
            currentPage = 0;
        if (marketId == null || "".equals(marketId)){
            return goodsOrdersService.findGoodsOrders(currentPage);
        }
        return goodsOrdersService.findGoodsOrdersByMarketId(currentPage, marketId);
    }
}
