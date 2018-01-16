package com.llrj.hongbs.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface StudentCountRepository extends JpaRepository<StudentCount,Long> {

    @Query("select s from StudentCount s where s.oddNum=:oddNum")
    StudentCount findByOddNum(@Param("oddNum")String oddNum);
}
