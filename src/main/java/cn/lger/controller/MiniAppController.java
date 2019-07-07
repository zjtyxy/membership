package cn.lger.controller;

import cn.lger.dao.*;
import cn.lger.domain.Commodity;
import cn.lger.domain.Gift;
import cn.lger.domain.Member;
import cn.lger.domain.MemberGrade;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class MiniAppController {
    @Resource
    private MemberDao memberDao;
    @Resource
    private CommodityDao commodityDao;
    @Resource
    private GiftDao giftDao;
    //@Resource
    //private BCryptPasswordEncoder encoder;
    @Resource
    private MemberGradeDao memberGradeDao;
    @RequestMapping("/minapp/register")
    public  Member userRegister(Member member)
    {
        Member rst = new Member();
        try {
            List<MemberGrade> list = memberGradeDao.findMemberGradeByGradeName("普通会员");
            //保证输入的会员名是存在的
            //设置会员类型
            member.setMemberGrade(list.get(0));
            member.setState("正常");
            member.setBalance((float) 0);
            member.setMemberIntegral(0L);
            member.setPassword("123456");
            BCryptPasswordEncoder encoder =new BCryptPasswordEncoder();
            member.setPassword(encoder.encode(member.getPassword()));
            rst  =memberDao.save(member);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return rst;
    }
    @RequestMapping("/minapp/member")
    public Page<Member> findMember(Integer currentPage) {
        if (currentPage == null) {
            currentPage = 0;
        }
        Pageable pageable = new PageRequest(currentPage, 3);
        Page<Member> rs = memberDao.findAll(pageable);
        return rs;
    }
    @RequestMapping("/minapp/findgift")
    public Page<Gift> findGift(Integer currentPage) {
        if (currentPage == null) {
            currentPage = 0;
        }
        Pageable pageable = new PageRequest(currentPage, 3);
        Page<Gift> rs = giftDao.findAll(pageable);
        return rs;
    }

    @RequestMapping("/minapp/findCommodity")
    public Page<Commodity> findCommodity(Integer currentPage) {
        if (currentPage == null) {
            currentPage = 0;
        }
        Pageable pageable = new PageRequest(currentPage, 3);
        return commodityDao.findAll(pageable);
    }


}
