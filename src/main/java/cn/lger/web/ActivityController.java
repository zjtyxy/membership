package cn.lger.web;

import cn.lger.dao.ActivityDao;
import cn.lger.domain.Activity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Optional;

@Controller
public class ActivityController {

    @Resource
    private ActivityDao activityDao;

    @GetMapping("/queryActivity")
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

    @GetMapping("/getActivity")
    public String getActivity(String id,Model model)
    {
        Optional<Activity> users = activityDao.findById(id);
        if(users.isPresent())
        {
            model.addAttribute("activity",users.get());
        }
        return  "modifyActivity";
    }

    @GetMapping("/modifyActivity")
    public String modifyActivityView(Activity activity) {

        return "addActivity";
    }

    @GetMapping("/addActivity")
    public String getAddActivityView(){
        return "success";
    }
}
