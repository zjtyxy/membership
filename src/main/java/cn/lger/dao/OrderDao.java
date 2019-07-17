package cn.lger.dao;

import cn.lger.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDao  extends JpaRepository<Order,String> {
}
