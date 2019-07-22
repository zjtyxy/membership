package cn.lger.web;

import cn.lger.dao.ActivityDao;
import cn.lger.dao.AdminDao;
import cn.lger.domain.Activity;
import cn.lger.domain.Admin;
import cn.lger.domain.Member;
import cn.lger.domain.MemberGrade;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Controller
public class ActivityController {

    @Resource
    private ActivityDao activityDao;

    @PostMapping("/findActivity")
    @ResponseBody
    public Page<Activity> findActivity(Integer currentPage) {
        if (currentPage == null) {
            currentPage = 0;
        }
        Pageable pageable = new PageRequest(currentPage, 3);
        Page<Activity> rs = activityDao.findAll(pageable);
        return rs;
    }

    @GetMapping("/queryActivity")
    public String getQueryMemberView(Model model, @RequestParam(defaultValue = "0", required = false) Integer pageNum) {
        if (pageNum == null)
            pageNum = 0;
        //  Sort sort = Sort.by(new Sort.Order(Sort.Direction.DESC,"level"));
        Pageable page = PageRequest.of(pageNum, 10);
        Page<Activity> pages = activityDao.findAll(page);
        model.addAttribute("entitylist", pages.getContent());
        model.addAttribute("page", pages);
        model.addAttribute("pageNum", pageNum);
        model.addAttribute("totalPages", pages.getTotalPages());
        model.addAttribute("totalElements", pages.getTotalElements());
        return "queryActivity";
    }

    /**
     * 修改新建都调用这个方法，修改时要带参数id
     * @param model
     * @param activity
     * @return
     */
    @RequestMapping("/addActivity")
    public String addActivity(Model model, Activity activity){
        Activity rst = activity;
        if(activity.getId()!=null)
        {
            rst =  activityDao.findById(activity.getId()).get();
        }
        model.addAttribute("entity",rst);
        return "modifyActivity";
    }

    /**
     * 修改或者添加提交调用
     * @param model
     * @param activity
     * @return
     */
    @RequestMapping("/modifyActivity")
    public String modifyActivity(Model model,Activity activity) {
        activityDao.save(activity);
        model.addAttribute("entity",activity);
        return "redirect:queryActivity";
    }
}
