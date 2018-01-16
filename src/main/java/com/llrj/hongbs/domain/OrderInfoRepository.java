package com.llrj.hongbs.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderInfoRepository extends JpaRepository<OrderInfo, Long> {

    /**根据orderId查询所有配置info*/
    @Query("select o from OrderInfo o where o.orderid=:orderId")
    List<OrderInfo> findByOrderId(@Param("orderId") Long orderId);

    /**根据orderId查询所有配置info*/
    @Query("select o from OrderInfo o where o.orderid=:orderId and o.infoName=:infoName")
    List<OrderInfo> findByOrderidAndInfoName(@Param("orderId") Long orderId,@Param("infoName") String infoName);

    /**根据orderId删除info*/
    @Modifying
    @Query("delete from OrderInfo o where o.orderid=:orderId")
    int deleteByOrderId(@Param("orderId") Long orderId);

    /**根据infoId查询info*/
    @Query("select o from OrderInfo o where o.id=:id")
    OrderInfo findById(@Param("id")Long id);
}
