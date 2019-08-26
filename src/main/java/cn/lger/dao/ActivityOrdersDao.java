package cn.lger.dao;

import cn.lger.domain.ActivityOrders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActivityOrdersDao extends JpaRepository<ActivityOrders, String> {
    Page<ActivityOrders> findAll(Pageable pageable);

    Page<ActivityOrders> findAllByActivityId(Pageable pageable, String marketId);
}
