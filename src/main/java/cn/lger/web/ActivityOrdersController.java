package cn.lger.web;

import cn.lger.domain.ActivityOrders;
import cn.lger.service.ActivityOrdersService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
public class ActivityOrdersController {
    @Resource
    private ActivityOrdersService activityOrdersService;

    @GetMapping("/activityOrders")
    public String getActivityOrders(){
        return "activityOrders";
    }

    @PostMapping("/activityOrders")
    @ResponseBody
    public Page<ActivityOrders> goodsOrders(Integer currentPage, String activityId){
        if (currentPage == null || currentPage <0)
            currentPage = 0;
        if (activityId == null || "".equals(activityId)){
            return activityOrdersService.findActivityOrders(currentPage);
        }
        return activityOrdersService.findActivityOrdersByActivityId(currentPage, activityId);
    }
}
