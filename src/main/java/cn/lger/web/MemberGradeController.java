package cn.lger.web;

import cn.lger.dao.MemberGradeDao;
import cn.lger.domain.MemberGrade;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Optional;

/**
 * Code that Changed the World
 * Pro said
 * Created by Pro on 2017-12-16.
 */
@Controller
public class MemberGradeController {


    @Resource
    private MemberGradeDao memberGradeDao;


    @RequestMapping("/memberGrade")
    public String memberGrade(Model model, @RequestParam(defaultValue = "0",required = false) Integer  currentPage){
        if(currentPage == null)
            currentPage=0;
        //  Sort sort = Sort.by(new Sort.Order(Sort.Direction.DESC,"level"));
        Pageable page =PageRequest.of(currentPage,10);
        Page<MemberGrade> pages=memberGradeDao.findAll(page);
        model.addAttribute("entitylist",pages.getContent());
        model.addAttribute("page",pages);
        model.addAttribute("pageNum",currentPage);
        model.addAttribute("totalPages",pages.getTotalPages());
        model.addAttribute("totalElements",pages.getTotalElements());
        return "memberGrade";

    }

    @PostMapping("/updateMemberGrade")
    @ResponseBody
    public String updateMemberGrade(MemberGrade memberGrade){
        try{
            memberGradeDao.save(memberGrade);
        } catch (Exception e){
            e.printStackTrace();
            return "error";
        }
        return "success";
    }

    @PostMapping("/addMemberGrade")
    @ResponseBody
    public MemberGrade addMemberGrade(MemberGrade memberGrade){
        return memberGradeDao.save(memberGrade);
    }

    @PostMapping("/deleteMemberGrade")
    @ResponseBody
    public String delMemberGrade(Integer id){
        Optional<MemberGrade> rst = memberGradeDao.findById(id);
        if(rst.isPresent())
        {
            memberGradeDao.deleteById(id);
            return  "success";
        }
        return "fail";
    }

}
