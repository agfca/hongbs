package com.llrj.hongbs.web;

import com.llrj.hongbs.domain.*;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/orderDetail")
public class OrderDetailController {
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    OrderInfoRepository orderInfoRepository;

    /** get访问，查询单个orderId */
    @RequestMapping(value = "/{orderId}", method = RequestMethod.GET)
    public String getOrder(@PathVariable Long orderId, HttpSession session,ModelMap map) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            Order order = orderRepository.findById(orderId);
            map.addAttribute(order);
            return "orderDetail";
        } else {
            return "login";
        }
    }

    /**
     * 添加配置  TODO  配置没有处理
     */
    @ResponseBody
    @RequestMapping(value = "/addInfo", method = RequestMethod.POST)
    public String addInfo(@RequestParam Long orderId,//orderId
                          @RequestParam String fabric,//面料  json 多个面料，包括颜色
                          @RequestParam String styleImage,//款式图片  json
                          @RequestParam String style,//款式
                          @RequestParam String gongyi,//工艺
                          @RequestParam int total,//总数
                          @RequestParam String giveKH,//是否给客户留有样衣
                          @RequestParam String containFabric,//含量
                          @RequestParam String logoStation,//校徽位置
                          @RequestParam String logoFile,//校徽文件路径
                          @RequestParam String infoName,//配置名称

                          @RequestParam String modelList,//型号  json
                          HttpSession session) {
        User user = (User) session.getAttribute("user");
        String msg = "";
        if (user != null) {
            //查询是否存在该配置名称
            List<OrderInfo> list = orderInfoRepository.findByOrderidAndInfoName(orderId,infoName);
            if(list.size()>0) return "当前订单已经存在该配置";
            OrderInfo orderInfo = new OrderInfo(orderId,fabric,styleImage,style, gongyi,
             total, giveKH, containFabric, logoStation,
                     logoFile, infoName);
            JSONArray modeArr = JSONArray.fromObject(modelList);
            if(modeArr.size()==18){//型号为18个 TODO   如果前台改动。。要修改
                for (int i = 0 ; i<modeArr.size(); i++){
                    if(i==0){//110
                        String temp = (String) modeArr.get(i);
                        orderInfo.setXXS(Integer.parseInt(temp));
                    }if(i==1){//115
                        String temp = (String) modeArr.get(i);
                        orderInfo.setXXS_M(Integer.parseInt(temp));
                    }if(i==2){//120
                        String temp = (String) modeArr.get(i);
                        orderInfo.setXS(Integer.parseInt(temp));
                    }if(i==3){//125
                        String temp = (String) modeArr.get(i);
                        orderInfo.setXS_M(Integer.parseInt(temp));
                    }if(i==4){//130
                        String temp = (String) modeArr.get(i);
                        orderInfo.setS(Integer.parseInt(temp));
                    }if(i==5){//135
                        String temp = (String) modeArr.get(i);
                        orderInfo.setS_M(Integer.parseInt(temp));
                    }if(i==6){//140
                        String temp = (String) modeArr.get(i);
                        orderInfo.setM(Integer.parseInt(temp));
                    }if(i==7){//145
                        String temp = (String) modeArr.get(i);
                        orderInfo.setM_M(Integer.parseInt(temp));
                    }if(i==8){//150
                        String temp = (String) modeArr.get(i);
                        orderInfo.setL(Integer.parseInt(temp));
                    }if(i==9){//155
                        String temp = (String) modeArr.get(i);
                        orderInfo.setL_M(Integer.parseInt(temp));
                    }if(i==10){//160
                        String temp = (String) modeArr.get(i);
                        orderInfo.setXL(Integer.parseInt(temp));
                    }if(i==11){//165
                        String temp = (String) modeArr.get(i);
                        orderInfo.setXL_M(Integer.parseInt(temp));
                    }if(i==12){//170
                        String temp = (String) modeArr.get(i);
                        orderInfo.setXXL(Integer.parseInt(temp));
                    }if(i==13){//175
                        String temp = (String) modeArr.get(i);
                        orderInfo.setXXL_M(Integer.parseInt(temp));
                    }if(i==14){//180
                        String temp = (String) modeArr.get(i);
                        orderInfo.setXXXL(Integer.parseInt(temp));
                    }if(i==15){//185
                        String temp = (String) modeArr.get(i);
                        orderInfo.setXXXL_M(Integer.parseInt(temp));
                    }if(i==16){//190
                        String temp = (String) modeArr.get(i);
                        orderInfo.setXXXXS(Integer.parseInt(temp));
                    }if(i==17){//195
                        String temp = (String) modeArr.get(i);
                        orderInfo.setXXXXS_M(Integer.parseInt(temp));
                    }
                }
            }
            OrderInfo info = orderInfoRepository.save(orderInfo);
            msg = info.getId()!=null?"配置添加成功":"配置添加失败";
        }
        return msg;
    }

    /**查询单个orderinfo配置 */
    @RequestMapping(value = "/info/{infoId}", method = RequestMethod.GET)
    public String getInfo(@PathVariable Long infoId, HttpSession session,ModelMap map) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            OrderInfo orderInfo = orderInfoRepository.findById(infoId);
            if (orderInfo!=null){
                Order order = orderRepository.findById(orderInfo.getOrderid());
                String styleFile = orderInfo.getStyleImage();
                JSONArray styleImage = JSONArray.fromObject(styleFile);
                //String s =  styleImage.getString(0);

                JSONArray fabric = JSONArray.fromObject(orderInfo.getFabric());

                map.addAttribute("order",order);
                map.addAttribute("styleImage",styleImage);
                map.addAttribute("fabric",fabric);
                map.addAttribute("orderInfo",orderInfo);
                return "orderInfo";
            }else
                return "login";
        } else {
            return "login";
        }
    }

}
