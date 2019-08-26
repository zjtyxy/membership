package cn.lger.service;

import cn.lger.dao.ActivityOrdersDao;
import cn.lger.domain.ActivityOrders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ActivityOrdersService {
    @Resource
    private ActivityOrdersDao activityOrdersDao;

    public Page<ActivityOrders> findActivityOrders(Integer currentPage){
        Pageable pageable = PageRequest.of(currentPage, 3);
        return activityOrdersDao.findAll(pageable);
    }

    public Page<ActivityOrders> findActivityOrdersByActivityId(Integer currentPage, String activityId) {
        Pageable pageable = PageRequest.of(currentPage, 3);
        return activityOrdersDao.findAllByActivityId(pageable, activityId);
    }
}
