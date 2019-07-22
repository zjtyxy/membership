package cn.lger.dao;

import cn.lger.domain.Activity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ActivityDao extends JpaRepository<Activity, String> {

    @Query("select m from Activity m where m.name = ?1")
    Page<Activity> findAllByActivityName(String name, Pageable pageable);

}
