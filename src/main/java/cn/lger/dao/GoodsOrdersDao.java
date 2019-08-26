package cn.lger.dao;

import cn.lger.domain.GoodsOrders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GoodsOrdersDao extends JpaRepository<GoodsOrders, String> {

    Page<GoodsOrders> findAll(Pageable pageable);

    Page<GoodsOrders> findAllByMarketId(Pageable pageable, String marketId);
}
