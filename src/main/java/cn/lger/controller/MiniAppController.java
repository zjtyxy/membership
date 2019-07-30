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
import java.io.*;
import java.util.*;

@RestController
public class MiniAppController {

    private  static  String  uploadImagePath="d:/upload/images/";
    @Resource
    WXPayService wXPayService;


    @Resource
    private MemberDao memberDao;
    @Resource
    private MemberAddressDao memberAddressDao;
    @Resource
    private OrderDao orderDao;
    @Resource
    private CommodityDao commodityDao;
    @Resource
    private GiftDao giftDao;

    @Resource
    private MarketDao marketDao;

    @Resource
    private ActivityDao activityDao;

    //@Resource
    //private BCryptPasswordEncoder encoder;
    @Resource
    private MemberGradeDao memberGradeDao;

    @RequestMapping("/minapp/findMarket")
    public  Market findMarket(Integer id)
    {
        Market rst =  marketDao.findById(id).get();
        return  rst;
    }

    @RequestMapping("/minapp/findActivity")
    public  Activity findActivity(Integer id)
    {
        Activity rst =  activityDao.findById(id).get();
        return  rst;
    }

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
    @RequestMapping("/minapp/findOrder")
    public  Order findOrder(String id)
    {
        Order rst =  orderDao.findById(id).get();
        return  rst;
    }


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

    /**
     * 支付下单
     * @param order
     * @return
     */
    @RequestMapping("/minapp/unifiedorder")
    public String  addOrder(String memberId) {
       try {
           Optional<Member> rst = memberDao.findById(memberId);
           if (rst.isPresent()) {
               Member ds = rst.get();
               Order order = wXPayService.unifiedOrder(ds.getOpenid(), ds.getShenfenzheng() + new Date().getYear() + "m", "会费支付", 10);
           }
//           if(order!=null) {
//               orderDao.save(order);
//           }
//           orderDao.save(order);
           return  "success";
       }catch (Exception e)
       {

       }
        return "fail";
    }
    @RequestMapping("/minapp/getorder")
    public Order  gerOrder(String memberId) {
        Optional<Member> rst = memberDao.findById(memberId);
        if (rst.isPresent()) {
            Member ds = rst.get();
            Order order = wXPayService.unifiedOrder(ds.getOpenid(), ds.getShenfenzheng() + new Date().getYear() + "m", "会费支付", 10);
            if(order!=null) {
                orderDao.save(order);
                return order;
            }

        }
        return null;
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

    @RequestMapping("/minapp/activity")
    public Iterable<Activity>  activity(){
        System.out.println("微信小程序正在调用。。。");
        Iterable<Activity> acticity = activityDao.findAll();
        System.out.println("微信小程序调用完成。。。");
        return acticity;
    }

    @RequestMapping("/minapp/market")
    public Iterable<Market>  market(){
        System.out.println("微信小程序正在调用。。。");
        Iterable<Market> market = marketDao.findAll();
        System.out.println("微信小程序调用完成。。。");
        return market;
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


}
