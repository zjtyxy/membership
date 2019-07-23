package cn.lger.dao;

import cn.lger.domain.Market;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MarketDao extends JpaRepository<Market, Integer> {
    @Query("select m from Market m where m.name = ?1")
    Page<Market> findAllByMarketName(String name, Pageable pageable);
}
