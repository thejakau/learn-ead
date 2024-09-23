package com.sltc.dait.library.repository;

import com.sltc.dait.library.model.Lend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface LendRepo extends JpaRepository<Lend,Long> {

    @Query("select l from Lend l where l.dueDate < ?1")
    List<Lend> findOverdue(Date byDate);

    @Query("select l from Lend l where l.dueDate < ?1 and overdueNotifyCount <= ?2")
    List<Lend> findOverdue(Date byDate, Integer overdueNotifyCount);

    @Modifying(clearAutomatically = true)
    @Query("update Lend l set l.overdueNotifyCount=l.overdueNotifyCount+1 where l.id = ?1")
    Integer increaseNotifyCount(Long lendID);
}
