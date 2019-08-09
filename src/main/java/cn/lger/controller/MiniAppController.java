package cn.lger.controller;

import cn.lger.dao.*;
import cn.lger.domain.*;

import cn.lger.service.WXPayService;
import com.alibaba.fastjson.JSONObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.DateUtils;

import javax.annotation.Resource;
import java.io.*;
import java.util.*;

import static com.sun.xml.internal.fastinfoset.util.DuplicateAttributeVerifier.MAP_SIZE;

@RestController
public class MiniAppController {

    private  static  String  uploadImagePath="c:/work/upload/images";

    @Resource
    private MemberDao memberDao;
    @Resource
    private MemberAddressDao memberAddressDao;

    @Resource
    private CommodityDao commodityDao;
    @Resource
    private GiftDao giftDao;

    @Resource
    private MemberGradeDao memberGradeDao;


    @RequestMapping("/minapp/findAddress")
    public MiniAppRest findAddress(String memberId)
    {
        MiniAppRest rst = new MiniAppRest();
        rst.setResult( memberAddressDao.findByMemberId(memberId));
        return  rst;
    }
    @RequestMapping("/minapp/addAddress")
    public  MiniAppRest  addAddress(@RequestBody MemberAddress address)
    {
        MiniAppRest rst = new MiniAppRest();
        memberAddressDao.save(address);
        rst.setResult( memberAddressDao.findByMemberId(address.getMemberId()));
        return  rst;
    }
    @RequestMapping("/minapp/deleteAddress")
    public  List<MemberAddress> deleteAddress(String id)
    {
        Optional<MemberAddress> rp = memberAddressDao.findById(id);
        if(rp.isPresent()) {
            memberAddressDao.deleteById(id);
            return memberAddressDao.findByMemberId(rp.get().getMemberId());
        }
        return null;
    }

    @RequestMapping("/minapp/findMemberByOpenid")
    public  Member userRegister(String openid)
    {
        Member rst =  memberDao.findMemberByOpenid(openid);
        return  rst;
    }


    /**
     * 会员注册
     * @param member
     * @return
     */
    @RequestMapping(value ="/minapp/register",consumes="application/json")
    public  Member userRegister(@RequestBody Member member)
    {
        Member rst = new Member();
        Member cm =  memberDao.findMemberByOpenid(member.getOpenid());
        if(cm!=null)
        {
            member.setId(cm.getId());
        }
        try {
            List<MemberGrade> list = memberGradeDao.findByGradeName("普通会员");
            //保证输入的会员名是存在的
            //设置会员类型
            member.setMemberGrade(list.get(0));
            member.setState("正常");
            member.setBalance((float) 0);
            member.setMemberIntegral(0L);
            member.setPassword("123456");
            BCryptPasswordEncoder encoder =new BCryptPasswordEncoder();
            member.setPassword(encoder.encode(member.getPassword()));
            Progeress progeress = new Progeress();
            progeress.setName("报名");
            progeress.setStatus("accept");
            Map<String,String> nots = new HashMap<>();
            nots.put("报名时间：",DateUtils.format(new Date(),"yyyy-mm-dd HH:MM:ss",Locale.CHINA));
            progeress.setProgresNote(nots);
            member.getProgeresses().put(Progeress.progressName[0],progeress);
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
    @ResponseBody
    public String uploadImage(@RequestParam(value = "file") MultipartFile file) {
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
      //  String filePath = "d:/upload/images/";
        // 解决中文问题，liunx下中文路径，图片显示问题
        // fileName = UUID.randomUUID() + suffixName;
        File dest = new File(uploadImagePath + fileName);
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
    @RequestMapping(value = "/minapp/uploadFile")
    public String uploadImage1(@RequestParam("file") MultipartFile file) {
        if (!file.isEmpty()) {
            Map<String, String> resObj = new HashMap<>(MAP_SIZE);
            try {
                BufferedOutputStream out = new BufferedOutputStream(
                        new FileOutputStream(new File(uploadImagePath, file.getOriginalFilename())));
                out.write(file.getBytes());
                out.flush();
                out.close();
            } catch (IOException e) {
                resObj.put("msg", "error");
                resObj.put("code", "1");
                return JSONObject.toJSONString(resObj);
            }
            resObj.put("msg", "ok");
            resObj.put("code", "0");
            resObj.put("url","/upload/images/"+file.getOriginalFilename());
            return JSONObject.toJSONString(resObj);
        } else {
            return null;
        }
    }

}
