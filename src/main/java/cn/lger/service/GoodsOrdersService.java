package cn.lger.service;

import cn.lger.dao.GoodsOrdersDao;
import cn.lger.domain.GoodsOrders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class GoodsOrdersService {
    @Resource
    private GoodsOrdersDao goodsOrdersDao;

    public Page<GoodsOrders> findGoodsOrders(Integer currentPage){
        Pageable pageable = PageRequest.of(currentPage, 3);
        return goodsOrdersDao.findAll(pageable);
    }

    public Page<GoodsOrders> findGoodsOrdersByMarketId(Integer currentPage, String marketId) {
        Pageable pageable = PageRequest.of(currentPage, 3);
        return goodsOrdersDao.findAllByMarketId(pageable, marketId);
    }
}
