package com.llrj.hongbs.service;

import com.llrj.hongbs.domain.OrderInfoRepository;
import com.llrj.hongbs.domain.OrderMaterial;
import com.llrj.hongbs.domain.OrderMaterialRepository;
import com.llrj.hongbs.domain.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sun.rmi.runtime.Log;

import java.util.List;

@Service
@Transactional
public class OrderService {
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    OrderInfoRepository orderInfoRepository;
    @Autowired
    OrderMaterialRepository orderMaterialRepository;

    @Modifying
    public void deleteByOrderId(Long orderId){
        orderRepository.delete(orderId);
        orderInfoRepository.deleteByOrderId(orderId);
    }

    /*修改材料*/
    @Modifying
    public int changeMaterialByOrderId(Long orderId, List<OrderMaterial> list){
        orderMaterialRepository.deleteByOrderId(orderId);
        int i = 0;
        for (OrderMaterial orderMaterial:list) {
            orderMaterialRepository.save(orderMaterial);
            i++;
        }
        return i;
    }

}
