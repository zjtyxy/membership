package cn.lger.service;

import cn.lger.dao.GiftOrdersDao;
import cn.lger.domain.GiftOrders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class GiftOrdersService {
    @Resource
    private GiftOrdersDao giftOrdersDao;

    public Page<GiftOrders> findGiftOrders(Integer currentPage){
        Pageable pageable = PageRequest.of(currentPage, 3);
        return giftOrdersDao.findAll(pageable);
    }

    public Page<GiftOrders> findGiftOrdersByGiftId(Integer currentPage, String giftId) {
        Pageable pageable = PageRequest.of(currentPage, 3);
        return giftOrdersDao.findAllByGiftId(pageable, giftId);
    }
}
