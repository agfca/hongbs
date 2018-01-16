package com.llrj.hongbs.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query("select o from Order o where o.orderName=:orderName")
    List<Order> findByOrderName(@Param("orderName")String orderName);
    
    @Query("select o from Order o where o.oddNum=:oddNum")
    Order findByOddNum(@Param("oddNum")String oddNum);
  
    @Query("select o from Order o where o.id=:id")
    Order findById(@Param("id")Long id);

    @Query("select o from Order o where o.orderStatu=:orderStatu")
    List<Order> findByOrderStatu(@Param("orderStatu")String orderStatu);
    
    @Query("select o from Order o where o.partyA=:partyA")
    List<Order> findBypartyA(@Param("partyA")String partyA);

    @Query("select count(o) from  Order o where o.orderStatu=:orderStatu and o.orderSx>=:orderSx")
    Integer findOrderBySx(@Param("orderStatu")String orderStatu,@Param("orderSx")Integer orderSx);
   
    @Query("select o from Order o where o.orderTime>:startTime and o.orderTime<:endTime ORDER BY deliveryTime")
	List<Order> findOrders(@Param("startTime")String startTime,@Param("endTime")String endTime);
}
