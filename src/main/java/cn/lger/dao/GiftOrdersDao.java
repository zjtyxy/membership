package cn.lger.dao;

import cn.lger.domain.GiftOrders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GiftOrdersDao extends JpaRepository<GiftOrders, String> {
    Page<GiftOrders> findAll(Pageable pageable);

    Page<GiftOrders> findAllByGiftId(Pageable pageable, String giftId);
}
