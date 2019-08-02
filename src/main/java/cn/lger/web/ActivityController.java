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
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
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
    public String modifyActivity(Model model, Activity activity) {
        activityDao.save(activity);
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
    @RequestMapping(value = "/addPhoto",method = RequestMethod.POST)
    public @ResponseBody String uploadImg(@RequestParam("file") MultipartFile file, HttpSession session,
                                          HttpServletRequest request) {



        String str="";

        String league_id=request.getSession().getAttribute("leagueID").toString();

        System.out.println("联赛id："+league_id);

        String url=request.getRequestURI();
        System.out.println("URL:"+url);

        try {
            if(null!=file){
                //获得当前项目所在路径
                String pathRoot=request.getSession().getServletContext().getRealPath("");
                System.out.println("当前项目所在路径："+pathRoot);
                //生成uuid作为文件名称
             //  String uuid=UUID.randomUUID().toString().replaceAll("-","");
             //   System.out.println("文件名称："+uuid);
                //获得文件类型（判断如果不是图片文件类型，则禁止上传）
                String contentType=file.getContentType();
                System.out.println("文件类型："+contentType);
                //获得文件后缀名称
                String imageName=contentType.substring(contentType.indexOf("/")+1);
                System.out.println("文件后缀名称："+imageName);

                String filePath="/Users/hurley/Desktop/footballleague-managementsystem/src/main/resources/static/upload/images/";
                //根据日期来创建对应的文件夹
                String datePath=new SimpleDateFormat("yyyy/MM/dd/").format(new Date());
                System.out.println("日期："+datePath);
                //根据id分类来创建对应的文件夹
                String leagueIdPath=league_id+"/";

                //日期
                /*String path=filePath+datePath;*/
                //联赛id
                String path=filePath+leagueIdPath;

                //如果不存在，则创建新文件夹
                File f=new File(path);
                if(!f.exists()){
                    f.mkdirs();
                }
                //新生成的文件名称
            //    String fileName=uuid+"."+imageName;
                String fileName="uuid"+"."+imageName;
                System.out.println("新生成的文件名称："+fileName);
                session.setAttribute("fileName",fileName);

                //图片保存的完整路径
                String pathName=path+fileName;
                System.out.println(pathName);

                //获取所属联赛ID
                int leagueID=Integer.parseInt(league_id);

                //图片的静态资源路径
                String staticPath="/upload/images/"+leagueID+"/"+fileName;
                System.out.println("静态资源路径："+staticPath);

                //复制操作
                //将图片从源位置复制到目标位置
                file.transferTo(new File(pathName));

                str = "{\"code\": 0,\"msg\": \"\",\"data\": {\"src\":\"" + "pic/"+datePath+fileName + "\"}}";


            }
            else{
                System.out.println("文件为空");
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            return str;
        }

    }

}
