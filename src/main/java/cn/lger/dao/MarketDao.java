package cn.lger.dao;

import cn.lger.domain.Market;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

public interface MarketDao extends PagingAndSortingRepository<Market, String> {



}
