package cn.lger.dao;

import cn.lger.domain.MemberAddress;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberAddressDao extends JpaRepository<MemberAddress,String> {
    List<MemberAddress> findByMemberId(String memberId);
}
