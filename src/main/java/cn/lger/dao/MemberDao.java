package cn.lger.dao;

import cn.lger.domain.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.time.LocalDate;
import java.util.List;

/**
 * Code that Changed the World
 * Pro said
 * Created by Pro on 2017-12-06.
 */
public interface MemberDao extends PagingAndSortingRepository<Member, String> {

    Member findMemberByOpenid(String openid);




    @Query("select m from Member m where m.memberName = ?1")
    Page<Member> findAllByMemberName(String memberName, Pageable pageable);

    @Query("select count(m.id) from  Member  m")
    int queryAllCount();

    List<Member> findByBirthday(LocalDate birthday);
}
