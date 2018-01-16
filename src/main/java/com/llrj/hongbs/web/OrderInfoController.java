package com.llrj.hongbs.web;

import com.llrj.hongbs.domain.*;
import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/orderInfo")
public class OrderInfoController {
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    OrderInfoRepository orderInfoRepository;

    /**
     * 修改订单info  页面
     * */
    @RequestMapping(value = "/edit/{infoId}", method = RequestMethod.GET)
    public String editInfo(@PathVariable Long infoId,
                           HttpSession session,ModelMap map) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            OrderInfo orderInfo = orderInfoRepository.findById(infoId);
            if(orderInfo!=null){
                Order order = orderRepository.findById(orderInfo.getOrderid());
                map.addAttribute(order);
                if(orderInfo.getFabric().length()>4){//是个数组
                    try {
                        JSONArray fabric = JSONArray.fromObject(orderInfo.getFabric());
                        map.addAttribute("fabrics",fabric);
                    }catch (Exception e){
                        System.out.println(e.getMessage());
                    }
                }


            }
            map.addAttribute(orderInfo);
            return "editOrderInfo";
        } else {
            return "login";
        }
    }

    /**
     * 删除订单info
     * */
    @ResponseBody
    @RequestMapping(value = "/{infoId}", method = RequestMethod.DELETE)
    public String deleteInfo(@PathVariable Long infoId,
                           HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "请登录";
        }
        OrderInfo orderInfo = orderInfoRepository.findById(infoId);
        if(orderInfo!=null){
            orderInfoRepository.delete(infoId);
        }
        return "删除成功";
    }

    /**
     * 修改订单info  字段  post
     * */
    @ResponseBody
    @PostMapping (value = "/edit/{infoId}")
    public String editInfo(@PathVariable Long infoId,
                             @RequestParam String name,
                             @RequestParam String value,
                             HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "请登录";
        }
        OrderInfo orderInfo = orderInfoRepository.findById(infoId);
        if(orderInfo==null){
            return "订单配置不存在";
        }
        String msg = "success";
        if("infoName".equals(name)){//配置名称
            orderInfo.setInfoName(value);
            orderInfoRepository.save(orderInfo);
        }if("gongyi".equals(name)){//工艺
            orderInfo.setGongyi(value);
            orderInfoRepository.save(orderInfo);
        }if("giveKH".equals(name)){//样衣
            orderInfo.setGiveKH(value);
            orderInfoRepository.save(orderInfo);
        }if("logoStation".equals(name)){//校徽位置
            orderInfo.setLogoStation(value);
            orderInfoRepository.save(orderInfo);
        }if("total".equals(name)){//总数
            orderInfo.setTotal(Integer.valueOf(value));
            orderInfoRepository.save(orderInfo);
        }if("style".equals(name)){//款式
            orderInfo.setStyle(value);
            orderInfoRepository.save(orderInfo);
        }if("containFabric".equals(name)){//含量
            orderInfo.setContainFabric(value);
            orderInfoRepository.save(orderInfo);
        }if("logoFile".equals(name)){//校徽file
            orderInfo.setLogoFile(value);
            orderInfoRepository.save(orderInfo);
        }if("styleImage".equals(name)){//款式图片
            orderInfo.setStyleImage(value);
            orderInfoRepository.save(orderInfo);
        }if("fabric".equals(name)){//面料
            orderInfo.setFabric(value);
            orderInfoRepository.save(orderInfo);
        }


        /*型号数量  身高  */
        if("XXS".equals(name)){//110
            int newVal = Integer.parseInt(value);
            int cha = newVal -orderInfo.getXXS();
            orderInfo.setXXS(newVal);
            orderInfo.setTotal(orderInfo.getTotal()+cha);
            orderInfoRepository.save(orderInfo);
        }if("XXS_M".equals(name)){//115
            int newVal = Integer.parseInt(value);
            int cha = newVal -orderInfo.getXXS_M();
            orderInfo.setXXS_M(newVal);
            orderInfo.setTotal(orderInfo.getTotal()+cha);
            orderInfoRepository.save(orderInfo);
        }if("XS".equals(name)){//120
            int newVal = Integer.parseInt(value);
            int cha = newVal -orderInfo.getXS();
            orderInfo.setXS(newVal);
            orderInfo.setTotal(orderInfo.getTotal()+cha);
            orderInfoRepository.save(orderInfo);
        }if("XS_M".equals(name)){//125
            int newVal = Integer.parseInt(value);
            int cha = newVal -orderInfo.getXS_M();
            orderInfo.setXS_M(newVal);
            orderInfo.setTotal(orderInfo.getTotal()+cha);
            orderInfoRepository.save(orderInfo);
        }if("S".equals(name)){//130
            int newVal = Integer.parseInt(value);
            int cha = newVal -orderInfo.getS();
            orderInfo.setS(newVal);
            orderInfo.setTotal(orderInfo.getTotal()+cha);
            orderInfoRepository.save(orderInfo);
        }if("S_M".equals(name)){//135
            int newVal = Integer.parseInt(value);
            int cha = newVal -orderInfo.getS_M();
            orderInfo.setS_M(newVal);
            orderInfo.setTotal(orderInfo.getTotal()+cha);
            orderInfoRepository.save(orderInfo);
        }if("M".equals(name)){//140
            int newVal = Integer.parseInt(value);
            int cha = newVal -orderInfo.getM();
            orderInfo.setM(newVal);
            orderInfo.setTotal(orderInfo.getTotal()+cha);
            orderInfoRepository.save(orderInfo);
        }if("M_M".equals(name)){//145
            int newVal = Integer.parseInt(value);
            int cha = newVal -orderInfo.getM_M();
            orderInfo.setM_M(newVal);
            orderInfo.setTotal(orderInfo.getTotal()+cha);
            orderInfoRepository.save(orderInfo);
        }if("L".equals(name)){//150
            int newVal = Integer.parseInt(value);
            int cha = newVal -orderInfo.getL();
            orderInfo.setL(newVal);
            orderInfo.setTotal(orderInfo.getTotal()+cha);
            orderInfoRepository.save(orderInfo);
        }if("L_M".equals(name)){//155
            int newVal = Integer.parseInt(value);
            int cha = newVal -orderInfo.getL_M();
            orderInfo.setL_M(newVal);
            orderInfo.setTotal(orderInfo.getTotal()+cha);
            orderInfoRepository.save(orderInfo);
        }if("XL".equals(name)){//160
            int newVal = Integer.parseInt(value);
            int cha = newVal -orderInfo.getXL();
            orderInfo.setXL(newVal);
            orderInfo.setTotal(orderInfo.getTotal()+cha);
            orderInfoRepository.save(orderInfo);
        }if("XL_M".equals(name)){//165
            int newVal = Integer.parseInt(value);
            int cha = newVal -orderInfo.getXL_M();
            orderInfo.setXL_M(newVal);
            orderInfo.setTotal(orderInfo.getTotal()+cha);
            orderInfoRepository.save(orderInfo);
        }if("XXL".equals(name)){//170
            int newVal = Integer.parseInt(value);
            int cha = newVal -orderInfo.getXXL();
            orderInfo.setXXL(newVal);
            orderInfo.setTotal(orderInfo.getTotal()+cha);
            orderInfoRepository.save(orderInfo);
        }if("XXL_M".equals(name)){//175
            int newVal = Integer.parseInt(value);
            int cha = newVal -orderInfo.getXXL_M();
            orderInfo.setXXL_M(newVal);
            orderInfo.setTotal(orderInfo.getTotal()+cha);
            orderInfoRepository.save(orderInfo);
        }if("XXXL".equals(name)){//180
            int newVal = Integer.parseInt(value);
            int cha = newVal -orderInfo.getXXXL();
            orderInfo.setXXXL(newVal);
            orderInfo.setTotal(orderInfo.getTotal()+cha);
            orderInfoRepository.save(orderInfo);
        }if("XXXL_M".equals(name)){//185
            int newVal = Integer.parseInt(value);
            int cha = newVal -orderInfo.getXXXL_M();
            orderInfo.setXXXL_M(newVal);
            orderInfo.setTotal(orderInfo.getTotal()+cha);
            orderInfoRepository.save(orderInfo);
        }if("XXXXS".equals(name)){//190
            int newVal = Integer.parseInt(value);
            int cha = newVal -orderInfo.getXXXXS();
            orderInfo.setXXXXS(newVal);
            orderInfo.setTotal(orderInfo.getTotal()+cha);
            orderInfoRepository.save(orderInfo);
        }if("XXXXS_M".equals(name)){//195
            int newVal = Integer.parseInt(value);
            int cha = newVal -orderInfo.getXXXXS_M();
            orderInfo.setXXXXS_M(newVal);
            orderInfo.setTotal(orderInfo.getTotal()+cha);
            orderInfoRepository.save(orderInfo);
        }
        return msg;
    }

}
