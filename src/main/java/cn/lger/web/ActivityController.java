package cn.lger.web;

import cn.lger.dao.ActivityDao;
import cn.lger.domain.Activity;
import cn.lger.domain.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Optional;

@Controller
public class ActivityController {

    @Resource
    private ActivityDao activityDao;

    @RequestMapping("/queryActivity")
    public String getQueryActivityView(Model model, @RequestParam(defaultValue = "0",required = false) Integer pageNum) {
        if(pageNum == null)
            pageNum=0;
        //  Sort sort = Sort.by(new Sort.Order(Sort.Direction.DESC,"level"));
        Pageable page =PageRequest.of(pageNum,10);
        Page<Activity> pages=activityDao.findAll(page);
        model.addAttribute("entitylist",pages.getContent());
        model.addAttribute("page",pages);
        model.addAttribute("pageNum",pageNum);
        model.addAttribute("totalPages",pages.getTotalPages());
        model.addAttribute("totalElements",pages.getTotalElements());
        return "queryActivity";
    }

    @PostMapping("/queryActivity")
    @ResponseBody
    public Page<Activity> queryActivity(Integer currentPage, String activityName) {
        if (activityName == null || activityName.trim().equals(""))
            return findActivity(currentPage);
        return findActivityByActivityName(currentPage, activityName);
    }

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

    public Page<Activity> findActivityByActivityName(Integer currentPage, String activityName){
        if (currentPage == null){
            currentPage = 1;
        }
        Pageable pageable = PageRequest.of(currentPage, 3);
        return activityDao.findAllByActivityName(activityName, pageable);
    }

    /**
     * 修改新建都调用这个方法，修改时要带参数id
     * @param model
     * @param activity
     * @return
     */
    @RequestMapping("/addActivity")
    public String addActivity(Model model, Activity activity){
        Activity rst = new Activity();
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

    /**
     * 删除指定的活动
     * @param id
     * @return
     */
    @RequestMapping("/deleteActivity")
    @ResponseBody
    public String deleteActivity(Integer id) {

        Optional<Activity> rst = activityDao.findById(id);
        if(rst.isPresent())
        {
            activityDao.deleteById(id);
            return  "success";
        }
      return "fail";
    }
}
