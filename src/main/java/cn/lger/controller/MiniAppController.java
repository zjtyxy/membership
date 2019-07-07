package cn.lger.controller;

import cn.lger.dao.*;
import cn.lger.domain.Commodity;
import cn.lger.domain.Gift;
import cn.lger.domain.Member;
import cn.lger.domain.MemberGrade;

import org.apache.coyote.Response;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
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
    @RequestMapping(value = "/minapp/upload", method = RequestMethod.POST)
    public String uploadImage(@RequestParam(value = "file") MultipartFile file) throws RuntimeException {
        if (file.isEmpty()) {
            return "文件不能为空";
        }
        // 获取文件名
        String fileName = file.getOriginalFilename();
       // logger.info("上传的文件名为：" + fileName);
        // 获取文件的后缀名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
       // logger.info("上传的后缀名为：" + suffixName);
        // 文件上传后的路径
        String filePath = "d:/upload/images/";
        // 解决中文问题，liunx下中文路径，图片显示问题
        // fileName = UUID.randomUUID() + suffixName;
        File dest = new File(filePath + fileName);
        // 检测是否存在目录
        File parent = dest.getParentFile();
        if (!parent.exists()) {
           parent.mkdirs();
        }
        try {
            file.transferTo(dest);
          //  logger.info("上传成功后的文件路径未：" + filePath + fileName);
            return  fileName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "文件上传失败";
    }


}
