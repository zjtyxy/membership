package cn.lger.web;

import cn.lger.dao.MemberDao;
import cn.lger.dao.MemberGradeDao;
import cn.lger.dao.OrderDao;
import cn.lger.domain.Member;
import cn.lger.domain.MemberGrade;
import cn.lger.domain.Progeress;
import cn.lger.service.MemberService;
import cn.lger.util.FileUploadUtil;
import cn.lger.util.UUIDRandomUtil;
import cn.lger.util.WordUtils;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.DateUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.*;

/**
 * Code that Changed the World
 * Pro said
 * Created by Pro on 2017-12-06.
 */

@Controller
public class MemberController {
    private  static  String  wordTempltePath="c:/upload/template/";
    @Resource
    OrderDao orderDao;
    @Resource
    private MemberDao memberDao;
    @Resource
    private MemberGradeDao memberGradeDao;
    @Resource
    private MemberService memberService;
    @Resource
    private BCryptPasswordEncoder encoder;


    /**
     * 审批报名表
     * @param memberId
     * @param ispass
     * @param message
     */
    @RequestMapping("/approveMember")
    @ResponseBody
    public String approveMember(String memberId,boolean ispass,String message){
        Optional<Member> rst = memberDao.findById(memberId);
        if (rst.isPresent()){
            Member ds = rst.get();
            Progeress progeress = new Progeress();
            if(ispass) {
                    progeress.setName(Progeress.progressName[1]);
                    progeress.setStatus("pass");
                    Map<String, String> nots = new HashMap<>();
                    nots.put("审批消息：", "恭喜你报名成功，请尽快完成支付");
                    nots.put("审批时间：",DateUtils.format(new Date(),"yyyy-mm-dd HH:MM:ss",Locale.CHINA));
                    //nots.put("订单号：",order.getId());
                    progeress.setProgresNote(nots);
                    ds.getProgeresses().put(Progeress.progressName[1],progeress);
                    memberDao.save(ds);

            }
            else
            {
                progeress.setName("驳回");
                progeress.setStatus("accept");
                Map<String, String> nots = new HashMap<>();
                nots.put("审批时间：", DateUtils.format(new Date(),"yyyy-mm-dd HH:MM:ss",Locale.CHINA));
                nots.put("驳回原因：", message);
                progeress.setProgresNote(nots);
                ds.getProgeresses().put(Progeress.progressName[5],progeress);
                memberDao.save(ds);
            }

            return "success";
        }
        throw new RuntimeException("MemberGrade中不存在当前的id:"+memberId);
    }
    @GetMapping("/addMember")
    public String getAddMemberView() {
        return "addMember";
    }
    @GetMapping("/getMember")
    public String getMember(String id,Model model)
    {
        Optional<Member> users = memberDao.findById(id);
        if(users.isPresent())
        {
            model.addAttribute("member",users.get());
        }
        return  "modifyMember";
    }
    @GetMapping("/modifyMember")
    public String modifyMemberView(Member member) {

        return "addMember";
    }
    @PostMapping("/addMember")
    public String addMember(Member member, String gradeName, MultipartFile icon, Map<String, Object> model) {
        //处理上传文件
        try {
            if (icon == null)
                return "error";
            if (icon.getOriginalFilename().equals(""))
                member.setIconPath("/assets/icon/common.jpg");
            else
                member.setIconPath(FileUploadUtil.upload(icon, "/assets/icon/", UUIDRandomUtil.get32UUID()));
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
       // member.setId(MemberNumberRandomUtil.randomMemberNumber());
        //通过会员等级名获取会员类型
        List<MemberGrade> list = memberGradeDao.findByGradeName(gradeName);
        //保证输入的会员名是存在的
        if (list.get(0) == null)
            return "error";
        //设置会员类型
        member.setMemberGrade(list.get(0));
        member.setState("正常");
        member.setBalance((float) 0);
        member.setMemberIntegral(0L);
        member.setPassword(encoder.encode(member.getPassword()));
//        System.out.println(member);
        member = memberService.addMember(member);

        model.put("member", member);
        return "addMemberSuccess";
    }

    @GetMapping("/getGrade")
    @ResponseBody
    public Iterable<MemberGrade> getGrade() {
        return memberGradeDao.findAll();
    }

    @GetMapping("/queryMember")
    public String getQueryMemberView(Model model, @RequestParam(defaultValue = "0",required = false) Integer pageNum) {
        if(pageNum == null)
            pageNum=0;
      //  Sort sort = Sort.by(new Sort.Order(Sort.Direction.DESC,"level"));
        Pageable page =PageRequest.of(pageNum,10);
        Page<Member> pages=memberDao.findAll(page);
        model.addAttribute("memberlist",pages.getContent());
        model.addAttribute("page",pages);
        model.addAttribute("pageNum",pageNum);
        model.addAttribute("totalPages",pages.getTotalPages());
        model.addAttribute("totalElements",pages.getTotalElements());
        return "queryMember";
    }

    @PostMapping("/queryMember")
    @ResponseBody
    public Page<Member> queryMember(Integer currentPage, String memberName) {
        if (memberName == null || memberName.trim().equals(""))
            return memberService.findMembers(currentPage);
        return memberService.findMembersByMemberName(currentPage, memberName);
    }

    @GetMapping("/modifyMemberState")
    public String getModifyMemberStateView() {
        return "modifyMemberState";
    }

    @PostMapping("/modifyMemberState")
    @ResponseBody
    public String modifyMemberStateView(String id, String state) {
        memberService.modifyMemberState(id, state);
        return "modifyMemberState";
    }

    @GetMapping("/deleteMember")
    public String getDeleteMemberView() {
        return "deleteMember";
    }

    @PostMapping("/deleteMember")
    @ResponseBody
    public String deleteMember(String id) {
        try {
            memberService.deleteMember(id);
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
        return "success";
    }

    @GetMapping("/balanceRecharge")
    public String getBalanceRechargeView() {
        return "/balanceRecharge";
    }

    @PostMapping("/balanceRecharge")
    @ResponseBody
    public String balanceRecharge(String id, String balance) {
        try {
            memberService.balanceRecharge(id, balance);
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
        return "success";
    }

    @GetMapping("/integralLottery")
    public String getIntegralLotteryView() {
        return "integralLottery";
    }

    @PostMapping("/integralLottery")
    @ResponseBody
    public Member integralLottery(Integer allIntegral) {
        if (allIntegral == null)
            return null;
        try {
            return memberService.integralLottery(allIntegral);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 打印报名表
     * @param req
     * @param response
     * @param memberid
     */
    @RequestMapping(value = "/exportWord")
    public void exportWord(HttpServletRequest req, HttpServletResponse response, String memberid)  {

        //String templatePath = request.getServletContext().getRealPath("") + "/template/会员登记表.docx";

        Member member = memberDao.findById(memberid).get();
        try {
            String templatePath = wordTempltePath+"会员登记表.docx";
            String outfile = "d:/upload/template/test.docx";
            String fileName = new String("会员登记表".getBytes("gb2312"), "ISO8859-1") + ".docx";
            /*数据*/
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("${name}", member.getMemberName());
            params.put("${zhiwu}", member.getZhiwu());
            params.put("${zzmm}", member.getZhengzhimianmao());
            params.put("${sex}", member.getSex());
            params.put("${minzu}", member.getMinzu());
            params.put("${sheng}", member.getBirthday().toString());
            params.put("${xuli}", member.getXueli());
            params.put("${email}", member.getEmail());

            params.put("${shenfenzhenghao}", member.getShenfenzheng());
            params.put("${phone}", member.getPhone());
            params.put("${gongzuodanwei}", member.getGongzuodanwei());
            params.put("${jiankang}", member.getJiankangzhuangkuang());
            params.put("${address}", member.getAddress());

            params.put("${youbian}", member.getYouzhengbianma());
            params.put("${huodong}", member.getHuodongjianjie());
            params.put("${techang}", member.getJinengtechang());
            params.put("${fuwuyixiang}", member.getFuwuyixiang());
            params.put("${zhiye}", member.getZhiye());
            params.put("${image1}", "dd");

            WordUtils wordUtil = new WordUtils();
            XWPFDocument doc;
            InputStream is = new FileInputStream(templatePath);
            // is = getClass().getClassLoader().getResourceAsStream(templatePath);
            doc = new XWPFDocument(is);  //只能使用.docx的
            //     wordUtil.insertImage("${image}",doc);
            wordUtil.replaceInPara(doc, params);
            //替换表格里面的变量
            wordUtil.replaceInTable(doc, params);
           // OutputStream os = new FileOutputStream(outfile);
            OutputStream  os = response.getOutputStream();
             response.setContentType("application/vnd.ms-word");
             response.setHeader("Content-disposition", "attachment;filename=" + fileName);
            doc.write(os);
            wordUtil.close(os);
            wordUtil.close(is);
            os.flush();
            os.close();
        }catch (Exception e)
        {
            e.printStackTrace();
        }

    }
}
