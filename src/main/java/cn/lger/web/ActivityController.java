package cn.lger.web;

import cn.lger.dao.ActivityDao;
import cn.lger.dao.AdminDao;
import cn.lger.domain.Activity;
import cn.lger.domain.Admin;
import cn.lger.domain.MemberGrade;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
}
