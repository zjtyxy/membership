package cn.lger.web;

import cn.lger.dao.MarketDao;
import cn.lger.domain.Market;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Optional;

@Controller
public class MarketController {
    @Resource
    private MarketDao marketDao;
    /**
     * 修改新建都调用这个方法，修改时要带参数id
     * @param model
     * @param market
     * @return
     */
    @RequestMapping("/addMarket")
    public String addMarket(Model model, Market market){
        Market rst = market;
        if(market.getId()!=null)
        {
            rst =  marketDao.findById(market.getId()).get();
        }
        model.addAttribute("entity",rst);
        return "modifyMarket";
    }

    /**
     * 修改或者添加提交调用
     * @param model
     * @param market
     * @return
     */
    @RequestMapping("/modifyMarket")
    public String modifyMarket(Model model,Market market) {
        marketDao.save(market);
        model.addAttribute("entity",market);
        return "redirect:queryMarket";
    }

    @RequestMapping("/queryMarket")
    public String getQueryMarketView(Model model, @RequestParam(defaultValue = "0",required = false) Integer pageNum) {
        if(pageNum == null)
            pageNum=0;
        Pageable page =PageRequest.of(pageNum,10);
        Page<Market> pages=marketDao.findAll(page);
        model.addAttribute("entitylist",pages.getContent());
        model.addAttribute("page",pages);
        model.addAttribute("pageNum",pageNum);
        model.addAttribute("totalPages",pages.getTotalPages());
        model.addAttribute("totalElements",pages.getTotalElements());
        return "queryMarket";
    }

    @PostMapping("/queryMarket")
    @ResponseBody
    public Page<Market> queryMarket(Integer currentPage, String MarketName) {
        if (MarketName == null || MarketName.trim().equals(""))
            return findMarket(currentPage);
        return findMarketByMarketName(currentPage, MarketName);
    }

    @PostMapping("/findMarket")
    @ResponseBody
    public Page<Market> findMarket(Integer currentPage) {
        if (currentPage == null) {
            currentPage = 0;
        }
        Pageable pageable = new PageRequest(currentPage, 3);
        Page<Market> rs = marketDao.findAll(pageable);
        return rs;
    }

    public Page<Market> findMarketByMarketName(Integer currentPage, String marketName){
        if (currentPage == null){
            currentPage = 1;
        }
        Pageable pageable = PageRequest.of(currentPage, 3);
        return marketDao.findAllByMarketName(marketName, pageable);
    }

    /**
     * 删除指定的活动
     * @param id
     * @return
     */
    @RequestMapping("/deleteMarket")
    @ResponseBody
    public String deleteMarket(Integer id) {

        Optional<Market> rst = marketDao.findById(id);
        if(rst.isPresent())
        {
            marketDao.deleteById(id);
            return  "success";
        }
        return "fail";
    }
}
