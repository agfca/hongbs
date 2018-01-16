package com.llrj.hongbs.web;

import com.llrj.hongbs.domain.Cons;
import com.llrj.hongbs.domain.Order;
import com.llrj.hongbs.domain.OrderRepository;
import com.llrj.hongbs.domain.User;
import com.llrj.hongbs.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/orderFile")
public class FileController {
    @Autowired
    OrderRepository orderRepository;

    /*get访问，返回文件*/
    @RequestMapping(value = "/",method = RequestMethod.GET)
    public String getOrderFile() {
        return "login";
    }
    /*post访问，上传文件*/
    @RequestMapping(value = "/",method = RequestMethod.POST)
    @ResponseBody
    public String uploadOrderFile(@RequestParam(value = "file",required = true) MultipartFile multipartFile, HttpServletRequest request) {//必须传入文件,http错误400  前台进行判断
        File file = new File(Cons.filePath + multipartFile.getOriginalFilename());
        try{
            multipartFile.transferTo(file);
        }catch (Exception e){
            System.out.println(e);
            return "错误";
        }
        return "上传成功";
    }

    @ResponseBody
    @PostMapping("/upload/{orderId}")
    public Map<String,String> upload(@PathVariable Long orderId, MultipartFile file, HttpSession session) throws  Exception{
        Map<String,String> map = new HashMap<String,String>();
        User user = (User)session.getAttribute("user");
        if(user==null){
            map.put("msg","登录超时，请登录");
            map.put("result","fail");
            return map;
        }
        //查询订单
        Order order  = orderRepository.findById(orderId);
        if(order==null){
            map.put("msg","订单不存在");
            map.put("result","fail");
            return map;
        }
        if(file!=null){
            try{
                //订单id文件夹
                String dirPath = Cons.filePath + String.valueOf(orderId) + "//";
                File fileDir = new File(dirPath);
                if (!fileDir.exists()) { // 如果不存在 则创建
                    fileDir.mkdirs();
                }
                String fileName = String.valueOf( new Date().getTime());//当前时间戳 为文件名
                String yName = file.getOriginalFilename();
                String ext = yName.substring(yName.lastIndexOf("."));//后缀
                String filePath = fileName+ext;
                File modelF = new File(fileDir,
                        filePath);
                file.transferTo(modelF);

                map.put("msg",filePath);//返回文件名字
                map.put("result","ok");
            }catch (Exception e){
                map.put("msg","文件上传失败，请检查文件格式是否正确");
                map.put("result","fail");
                System.out.println(e.getMessage());
                throw e;
            }
        }else{
            map.put("msg","请上传文件");
            map.put("result","fail");
        }
        return map;
    }

    /**
     * 上传图片页面
     * */
    @RequestMapping(value = "/uploadPicture")
    public String uploadStyle(HttpSession session) {
        User user = (User)session.getAttribute("user");
        if(user==null){
            return "login";
        }
        return "uploadPicture";
    }


}
