package com.llrj.hongbs.utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

 

public class BatchPublishSMSMessageDemo {
  
	public static void main(String[] args) {
		// 设置超时时间-可自行调整
//  	      List<String> numbser = new ArrayList<String>();
//  	      numbser.add("18182231383");
//  	      numbser.add("18996251891");
//  	      //numbser.add("18223327212");
//  	     for (int i = 0; i < numbser.size(); i++) {
//  		      SendSMSUtil.SendSms(numbser.get(i),"管理员");
// 		 }
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.add(Calendar.MONTH, 0);
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        System.out.println(sdf.format(calendar.getTime()));
 	}

}
