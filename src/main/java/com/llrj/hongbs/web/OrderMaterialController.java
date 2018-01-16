package com.llrj.hongbs.web;

import com.llrj.hongbs.domain.*;
import com.llrj.hongbs.service.OrderService;
import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/orderMaterial")
public class OrderMaterialController {

    @Autowired
    OrderRepository orderRepository;
    @Autowired
    OrderInfoRepository orderInfoRepository;
    @Autowired
    OrderMaterialRepository orderMaterialRepository;
    @Autowired
    OrderService orderService;


    /** 添加材料单页面 */
    @RequestMapping(value = "/{orderId}", method = RequestMethod.GET)
    public String getOrder(@PathVariable Long orderId, HttpSession session,ModelMap map) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            //订单
            Order order = orderRepository.findById(orderId);
            //订单所属材料单
            List<OrderMaterial> orderMaterials = orderMaterialRepository.findByOrderId(orderId);
            //订单所属配置
            List<OrderInfo> orderInfos = orderInfoRepository.findByOrderId(orderId);

            map.addAttribute("order",order);
            map.addAttribute("orderMaterials",orderMaterials);
            map.addAttribute("orderInfos",orderInfos);
            return "orderMaterial";
        } else {
            return "login";
        }
    }

    /**
     * 添加材料单
     */
    @ResponseBody
    @RequestMapping(value = "/add/{orderId}", method = RequestMethod.POST)
    public String addMaterial(@PathVariable Long orderId,//orderId
                          @RequestBody List<OrderMaterial> list,
                          HttpSession session) {
        User user = (User) session.getAttribute("user");
        String msg = "";
        if (user == null) {
            return "请登录";
        }
        //订单
        Order order = orderRepository.findById(orderId);
        if(order==null){
            return "订单不存在";
        }
        //查询已有的材料单
        List<OrderMaterial> temp = orderMaterialRepository.findByOrderId(orderId);
        if(temp.size()>0){
            order.setMaterial(1);
            orderRepository.save(order);
            return "材料单已存在，请在修改页面修改";
        }

        for (OrderMaterial orderMaterial: list) {
            orderMaterial = orderMaterialRepository.save(orderMaterial);
            if(orderMaterial.getId()==null){
                return "材料添加失败";
            }
        }
        order.setMaterial(1);
        orderRepository.save(order);
        return "材料添加成功";
    }

    /** 修改材料单页面 */
    @RequestMapping(value = "/edit/{orderId}", method = RequestMethod.GET)
    public String editOrder(@PathVariable Long orderId, HttpSession session,ModelMap map) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            //订单
            Order order = orderRepository.findById(orderId);
            //订单所属材料单
            List<OrderMaterial> orderMaterials = orderMaterialRepository.findByOrderId(orderId);
            //订单所属配置
            List<OrderInfo> orderInfos = orderInfoRepository.findByOrderId(orderId);

            map.addAttribute("order",order);
            map.addAttribute("orderMaterials",orderMaterials);
            map.addAttribute("orderInfos",orderInfos);
            return "editOrderMaterial";
        } else {
            return "login";
        }
    }

    /**
     * 修改材料单
     */
    @ResponseBody
    @RequestMapping(value = "/edit/{orderId}", method = RequestMethod.POST)
    public String editMaterial(@PathVariable Long orderId,//orderId
                          @RequestBody List<OrderMaterial> list,
                          HttpSession session) {
        User user = (User) session.getAttribute("user");
        String msg = "";
        if (user == null) {
            return "请登录";
        }
        //订单
        Order order = orderRepository.findById(orderId);
        if(order==null){
            return "订单不存在";
        }
        int i = orderService.changeMaterialByOrderId(orderId,list);
        return "材料修改成功";
    }

    /** 查看材料单页面  detail   */
    @RequestMapping(value = "/detail/{orderId}", method = RequestMethod.GET)
    public String getOrderMaterial(@PathVariable Long orderId, HttpSession session,ModelMap map) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            //订单
            Order order = orderRepository.findById(orderId);
            //订单所属材料单
            List<OrderMaterial> orderMaterials = orderMaterialRepository.findByOrderId(orderId);
            //订单所属配置
            List<OrderInfo> orderInfos = orderInfoRepository.findByOrderId(orderId);
            map.addAttribute("order",order);
            map.addAttribute("orderMaterials",orderMaterials);
            map.addAttribute("orderInfos",orderInfos);
            return "orderMaterialDetail";
        } else {
            return "login";
        }
    }
}
