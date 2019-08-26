package cn.lger.web;

import cn.lger.domain.GiftOrders;
import cn.lger.service.GiftOrdersService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
public class GiftOrdersController {
    @Resource
    private GiftOrdersService giftOrdersService;

    @GetMapping("/giftOrders")
    public String getgiftOrders(){
        return "giftOrders";
    }

    @PostMapping("/giftOrders")
    @ResponseBody
    public Page<GiftOrders> goodsOrders(Integer currentPage, String giftId){
        if (currentPage == null || currentPage <0)
            currentPage = 0;
        if (giftId == null || "".equals(giftId)){
            return giftOrdersService.findGiftOrders(currentPage);
        }
        return giftOrdersService.findGiftOrdersByGiftId(currentPage, giftId);
    }
}
