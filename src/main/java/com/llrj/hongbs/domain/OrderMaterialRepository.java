package com.llrj.hongbs.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderMaterialRepository extends JpaRepository<OrderMaterial, Long> {

    /**根据orderId查询材料单*/
    @Query("select o from OrderMaterial o where o.orderid=:orderId")
    List<OrderMaterial> findByOrderId(@Param("orderId") Long orderId);

    /**根据orderId 删除材料单*/
    @Modifying
    @Query("delete from OrderMaterial o where o.orderid=:orderId")
    int deleteByOrderId(@Param("orderId") Long orderId);

}
