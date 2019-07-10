package cn.lger.controller;

import cn.lger.dao.*;
import cn.lger.domain.Commodity;
import cn.lger.domain.Gift;
import cn.lger.domain.Member;
import cn.lger.domain.MemberGrade;

import cn.lger.util.WordUtils;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
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
import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

@RestController
public class MiniAppController {

    private  static  String  uploadImagePath="c:/upload/images/";
    private  static  String  wordTempltePath="c:/templte/word/";

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
    @RequestMapping("/minapp/findMemberByOpenid")
    public  Member userRegister(String openid)
    {
        return  memberDao.findMemberByOpenid(openid);
    }
    @RequestMapping(value = "/minapp/exportWord")
    public void exportWord(String memberid,String tempplteName)  {
        //String templatePath = request.getServletContext().getRealPath("") + "/template/会员登记表.docx";

        Member member = memberDao.findMemberById(memberid);
        try {
            String templatePath = wordTempltePath+tempplteName+".docx";
            String outfile = "d:/upload/template/test.docx";
            String fileName = new String("税源信息比对".getBytes("gb2312"), "ISO8859-1") + ".docx";
            /*数据*/
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("${name}", member.getMemberName());
            params.put("${zhiwu}", member.getZhiwu());
            params.put("${zzmm}", member.getZhengzhimianmao());
            params.put("${sex}", member.getSex());
            params.put("${minzu}", member.getMinzu());
            params.put("${sheng}", member.getBirthday());
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
            OutputStream os = new FileOutputStream(outfile);
            //  response.setContentType("application/vnd.ms-excel");
            // response.setHeader("Content-disposition", "attachment;filename=" + fileName);
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

    @RequestMapping("/minapp/register")
    public  Member userRegister(Member member)
    {
        Member rst = new Member();
        Member cm =  memberDao.findMemberByOpenid(member.getOpenid());
        if(cm!=null)
        {
            member.setId(cm.getId());
        }
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
