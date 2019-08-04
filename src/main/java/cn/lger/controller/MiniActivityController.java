package cn.lger.controller;

import cn.lger.dao.*;
import cn.lger.domain.*;
import cn.lger.service.WXPayService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.DateUtils;

import javax.annotation.Resource;
import java.io.File;
import java.util.*;

@RestController
public class MiniActivityController {
    @Resource
    private ActivityDao activityDao;
    @Resource
    private OrderDao orderDao;
    @Resource
    WXPayService wXPayService;
    @RequestMapping("/minapp/findActivity")
    public  Activity findActivity(Integer id,String memberId)
    {
        Activity rst =  activityDao.findById(id).get();
        //问了节省流量，做报名处理
        for(ActivitySingup ass :rst.getActivitySingups())
        {
           if(ass.getMemberId().equals(memberId))
           {
               rst.setActivitySingup(ass);
               rst.setActivitySingups(null);
               break;
           }
        }
        return  rst;
    }
    @RequestMapping("/minapp/activity")
    public Iterable<Activity>  activity(){
        System.out.println("微信小程序正在调用。。。");
        Iterable<Activity> acticity = activityDao.findAll();
        System.out.println("微信小程序调用完成。。。");
        return acticity;
    }

    /**
     * 活动报名
     * @return
     */
    @RequestMapping("/minapp/singup")
    public MiniAppRest  singup(int activityId ,String memberId){
        MiniAppRest rst=  new MiniAppRest();
        Optional<Activity> ap = activityDao.findById(activityId);
        if(ap.isPresent())
        {
            Activity activity = ap.get();
            if(activity.getActivitySingups().size() <activity.getMaxNumber())
            {
                for(ActivitySingup ass :activity.getActivitySingups())
                {
                    if(ass.getMemberId().equals(memberId))
                    {
                        rst.setCode(-3);
                        rst.setMessage("你已经报名");
                        return rst;
                    }
                }
                ActivitySingup as =  new ActivitySingup();
                as.setMemberId(memberId);
                as.setSingupTime(new Date());
                activity.getActivitySingups().add(as);
                activityDao.save(activity);
                rst.setResult(activity);
                return rst;
            }
            else
            {
                rst.setCode(-2);
                rst.setMessage("活动已经满员");
                return rst;
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
    public MiniAppRest gerOrder(String openid,Integer activityId,Integer price) {
        MiniAppRest rst = new MiniAppRest();
        rst.setCode(-1);
        rst.setMessage("未知错误");
        Date now = new Date();
        Optional<Activity> prduncts = activityDao.findById(activityId);
        if(prduncts.isPresent())
        {
            Order order = wXPayService.unifiedOrder(openid, now.getTime()+"p", prduncts.get().toString(), price,"https://minapp.tangyuanwenhua.com/minapp/payCallback");
            if (order != null) {
                order.setOrderType(2);
                order.setProductId(activityId);
                order.setProductNum(1);

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
}
