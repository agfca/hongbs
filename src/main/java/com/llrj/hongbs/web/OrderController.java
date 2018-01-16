package com.llrj.hongbs.web;

import com.llrj.hongbs.domain.*;
import com.llrj.hongbs.service.OrderService;
import com.llrj.hongbs.service.UserService;
import com.llrj.hongbs.utils.SendSMSUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.HttpRequestHandler;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {
	@Autowired
	OrderRepository orderRepository;
	@Autowired
	UserService userService;
	@Autowired
	OrderInfoRepository orderInfoRepository;
	@Autowired
	OrderService orderService;

	/* get访问，查询单个oddNum */
	@RequestMapping(value = "/{oddNum}", method = RequestMethod.GET)
	public Order getOrder(@PathVariable String oddNum, HttpSession session) {
		User user = (User) session.getAttribute("user");
		if (user != null) {
			Order order = orderRepository.findByOddNum(oddNum);
			return order;
		} else {
			return null;
		}
	}

	/**
	 * 下单添加单号    修改备注等---xiadan.js
	 * */
	@RequestMapping(value = "/addOrderNum", method = RequestMethod.POST)
	public String addOrderNum(@RequestParam Long orderId,
						   @RequestParam String orderNum,// 单号
						   @RequestParam(required = false) String remark,// 备注
						   @RequestParam String deliveryTime,// 交货时间
						   HttpSession session) {
		User user = (User) session.getAttribute("user");
		if (user == null) {
			return "请登录";
		}
		Order order = orderRepository.findById(orderId);
		if (order == null) {
			return "未找到订单";
		}
		String msg = "";
		//下单时间  +   下单人名字
		Date data = new Date();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String str = df.format(data);

		order.setOddNum(orderNum);
		order.setOrderTime(str);
		order.setFileName(user.getName());
		order.setRemark(remark);
		order.setDeliveryTime(deliveryTime);
		orderRepository.save(order);

		msg = "修改成功";
		return msg;

	}

	/**
	 * 更改订单状态  TODO 短信推送
	 * */
	@RequestMapping(value = "/{orderId}", method = RequestMethod.PUT)
	public String putOrder(@PathVariable Long orderId,
							@RequestParam String orderStatu,// 要修改的订单状态
							HttpSession session) {
		User user = (User) session.getAttribute("user");
		if (user == null) {
			return "请登录";
		}
		Order order = orderRepository.findById(orderId);
		if (order == null) {
			return "未找到订单";
		}
		String msg = "";

		// TODO 合并
		if (orderStatu.equals("8")) {// 生产安排排序，查询处于状态8的排序
			Date data = new Date();
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String str = df.format(data);
			order.setShengcanTime(str);
		}
		order.setOrderStatu(orderStatu);
		orderRepository.save(order);
		msg = "操作成功";
		//短信推送
		try{
			sendMsg(orderStatu);
		}catch (Exception e){
			msg = "操作成功，短信推送失败";
		}
		return msg;
	}

	/**
	 * 短信推送
	 */
	public void sendMsg(String orderStatu) throws Exception{
		if ("2".equals(orderStatu) || "5".equals(orderStatu)) {
			orderStatu = "2_5";
		}// 7_11_12
		if ("7".equals(orderStatu) || "11".equals(orderStatu)
				|| "12".equals(orderStatu)) {
			orderStatu = "7_11_12";
		}
		if ("111".equals(orderStatu) || "131".equals(orderStatu)) {
			orderStatu = "111_131";
		}
		if ("112".equals(orderStatu) || "132".equals(orderStatu)) {
			orderStatu = "112_132";
		}
		if ("113".equals(orderStatu) || "133".equals(orderStatu)) {
			orderStatu = "113_133";
		}

		if ("7".equals(orderStatu) || "11".equals(orderStatu)
				|| "12".equals(orderStatu)) {
			orderStatu = "7_11_12";
		}
		if ("17".equals(orderStatu)) {
			orderStatu = "17_16";
		}
		if ("16".equals(orderStatu)) {
			for (int j = 0; j < 2; j++) {
				if (j == 0)
					orderStatu = "17_16";
				else
					orderStatu = "16";
				List<User> u = userService.findUserByLevel(orderStatu);
				if (u != null && u.size() > 0) {
					for (int i = 0; i < u.size(); i++) {
						SendSMSUtil.SendSms(u.get(i).getCode(), u
								.get(i).getName());
					}
				}
			}

		} else {
			List<User> u = userService.findUserByLevel(orderStatu);
			if (u != null && u.size() > 0) {
				for (int i = 0; i < u.size(); i++) {
					SendSMSUtil.SendSms(u.get(i).getCode(), u.get(i)
							.getName());
				}
			}
		}
	}


	/**
	 * 查看订单名称是否重复
	 * */
	@RequestMapping(params = "method=findOrderByName")
	public String findOrderByName(@RequestParam  String orderName
								,@RequestParam(required = false)  Long orderId
								,HttpSession session) {
		User user = (User) session.getAttribute("user");
		String msg = "";
		if (user != null) {
			List<Order> orders = orderRepository.findByOrderName(orderName);
			if(orderId!=null){//修改订单查询是否重复
				if(orders.size()>0)
					msg = orders.get(0).getId().longValue()!=orderId.longValue()?"1":"0";//查找到的order 不是当前order   说明有名称重复
				else
					msg = "0";
			}else{
				msg = orders.size()>0?"1":"0";//>0已经存在
			}
		}
		return msg;
	}

	@RequestMapping(params = "method=findOrderFile")
	public ModelAndView findOrderFile(
			@RequestParam Long id,// 型号文件
			@RequestParam(value = "modelFile", required = false) MultipartFile modelFile,// 型号文件
			@RequestParam(value = "file2Name", required = false) MultipartFile file2Name,
			@RequestParam(value = "auxFabricFile", required = false) MultipartFile auxFabricFile,
			HttpSession session) {
		User user = (User) session.getAttribute("user");

		ModelAndView mvn = null;

		if (auxFabricFile != null
				&& StringUtils.hasText(auxFabricFile.getOriginalFilename())) {
			mvn = new ModelAndView("caigou");
		} else {
			mvn = new ModelAndView("gongyi");
		}

		String msg = "";

		if (user != null) {
			Order order = orderRepository.findById(id);

			// 文件路径为单号
			String filePath = Cons.filePath + order.getOrderName() + "//";
			File fileDir = new File(filePath);
			if (!fileDir.exists()) { // 如果不存在 则创建
				fileDir.mkdirs();
			}
			if (modelFile != null
					&& StringUtils.hasText(modelFile.getOriginalFilename())) {
				try {
					File modelF = new File(fileDir,
							modelFile.getOriginalFilename());
					modelFile.transferTo(modelF);

				} catch (Exception e) {
					System.out.println(e);
				}
				order.setModelFile(order.getOrderName() + "/"
						+ modelFile.getOriginalFilename());
			}

			if (file2Name != null
					&& StringUtils.hasText(file2Name.getOriginalFilename())) {

				try {
					File modelF = new File(fileDir,
							file2Name.getOriginalFilename());
					file2Name.transferTo(modelF);

				} catch (Exception e) {
					System.out.println(e);
				}
				order.setFile2Name(order.getOrderName() + "/"
						+ file2Name.getOriginalFilename());

			}

			if (auxFabricFile != null
					&& StringUtils.hasText(auxFabricFile.getOriginalFilename())) {

				try {
					File modelF = new File(fileDir,
							auxFabricFile.getOriginalFilename());
					auxFabricFile.transferTo(modelF);
				} catch (Exception e) {
					System.out.println(e);
				}
				order.setAuxFabricFile(order.getOrderName() + "/"
						+ auxFabricFile.getOriginalFilename());

			}

			Order o = orderRepository.save(order);
			if (o != null) {
				msg = "OK";
			} else {
				msg = "NO";
			}

		} else {
			msg = "NO";
		}
		mvn.getModelMap().put("msg", "OK");

		return mvn;
	}

	@RequestMapping(params = "method=findOrderFileZB")
	public ModelAndView findOrderFileZB(
			@RequestParam(value = "id", required = true) Long id,// 型号文件
			@RequestParam(value = "file", required = false) MultipartFile file,// 型号文件
			@RequestParam(value = "modelFile", required = false) MultipartFile modelFile,// 型号文件
			@RequestParam(value = "file2Name", required = false) MultipartFile file2Name,
			HttpSession session) {
		User user = (User) session.getAttribute("user");

		ModelAndView mvn = new ModelAndView("zhiban");

		String msg = "";
		if (user != null) {
			Order order = orderRepository.findById(id);

			// 文件路径为单号
			String filePath = Cons.filePath + order.getOrderName() + "//";
			File fileDir = new File(filePath);
			if (!fileDir.exists()) { // 如果不存在 则创建
				fileDir.mkdirs();
			}
			if (modelFile != null
					&& StringUtils.hasText(modelFile.getOriginalFilename())) {

				try {
					File modelF = new File(fileDir,
							modelFile.getOriginalFilename());
					modelFile.transferTo(modelF);

				} catch (Exception e) {
					System.out.println(e);
				}
				order.setModelFile(order.getOrderName() + "/"
						+ modelFile.getOriginalFilename());
			}
			if (file2Name != null
					&& StringUtils.hasText(file2Name.getOriginalFilename())) {
				{
					try {
						File modelF = new File(fileDir,
								file2Name.getOriginalFilename());
						file2Name.transferTo(modelF);

					} catch (Exception e) {
						System.out.println(e);
					}
					order.setFile2Name(order.getOrderName() + "/"
							+ file2Name.getOriginalFilename());
				}
			}
			if (file != null && StringUtils.hasText(file.getOriginalFilename())) {
				{
					try {
						File modelF = new File(fileDir,
								file.getOriginalFilename());
						file.transferTo(modelF);

					} catch (Exception e) {
						System.out.println(e);
					}
					order.setMainFabricFile(order.getOrderName() + "/"
							+ file.getOriginalFilename());
				}
			}

			Order o = orderRepository.save(order);
			if (o != null) {
				msg = "OK";
			} else {
				msg = "NO";
			}

		} else {
			msg = "NO";
		}
		mvn.getModelMap().put("msg", msg);

		return mvn;
	}
	/**
	 * 根据orderId查询配置单  orderInfo
	 */
	@RequestMapping(value = "/infos/{orderId}",method = RequestMethod.GET)
	public List<OrderInfo> getOrderInfo(@PathVariable Long orderId,HttpSession session){
		List<OrderInfo> orderInfos = null;
		User user = (User)session.getAttribute("user");
		if(user!=null){
			orderInfos = orderInfoRepository.findByOrderId(orderId);
		}
		return orderInfos;
	}

	/**
	 * 销售删除订单
	 */
	@DeleteMapping(value = "/{orderId}")
	public String deleteOrder(@PathVariable Long orderId,HttpSession session){
		User user = (User)session.getAttribute("user");
		String msg = "";
		if(user!=null){
			//支持事务回滚 @Modifying
			orderService.deleteByOrderId(orderId);
			msg = "删除成功";
		}else{
			msg = "请登录";
		}
		return msg;
	}

	/**
	 * 销售人员
	 * 添加订单（只是保存数据，还未提交）
	 * 和修改订单
	 */
	@RequestMapping(value = "/", method = RequestMethod.POST)
	public String postOrder(
			@RequestParam(required = false) Long orderId,
			@RequestParam String orderName,// 订单名称
			@RequestParam String partyA,// 甲方
			@RequestParam String deliveryTime,// 交货时间
			@RequestParam(required = false) String remark,// 备注
			//@RequestParam(required = false) MultipartFile[] modelFile,// 型号文件
			HttpSession session) {
		String msg = "";
		User user = (User) session.getAttribute("user");
		if(user==null){
			return "请登录";
		}
		orderName = orderName.replaceAll(" +", "");
		if (StringUtils.hasText(orderName) && StringUtils.hasText(partyA)
				&& StringUtils.hasText(deliveryTime)) {
			Order order = null;
			if(orderId!=null){//是修改订单
				order = orderRepository.findById(orderId);
				if(order==null){
					return "订单不存在";
				}
			}else{
				order = new Order();
			}

			order.setOrderName(orderName);
			order.setPartyA(partyA);
			order.setDeliveryTime(deliveryTime);//  交货时间
			//order.setOrderTime(String.valueOf(now));// 当前时间戳  下单时间
			order.setRemark(remark);
			order.setUserName(user.getName());//销售名字
			order.setOrderStatu("1");// 销售添加订单
			Order o = orderRepository.save(order);/**保存*/
			if(orderId!=null){
				msg = "修改订单成功";
			}else{
				msg = "添加订单成功";
			}
		}
		return msg;
	}

	/**
	 * 销售人员
	 * 确认下单（提交数据到工艺）  并 发送短信发送到工艺  TODO  短信
	 * 验证订单是否存在，配置是否添加
	 */
	@RequestMapping(value = "/submitOrder", method = RequestMethod.POST)
	public String submitOrder(
			@RequestParam Long orderId,// 订单id
			HttpSession session) {
		User user = (User) session.getAttribute("user");
		if(user==null){
			return "请登录";
		}

		Order order = orderRepository.findById(orderId);
		if(order==null){
			return "订单不存在";
		}
		List<OrderInfo> list = orderInfoRepository.findByOrderId(orderId);
		if(list.size()<1){
			return "请添加订单配置";
		}
		order.setOrderStatu("2");//销售确认订单
		Order o = orderRepository.save(order);/**保存*/
		//  发送短信
		String msg = "";
		try {
			//查找工艺人员
			List<User> u = userService.findUserByLevel("2_5");
			if (u != null && u.size() > 0) {
				for (int i = 0; i < u.size(); i++) {
					SendSMSUtil.SendSms(u.get(i).getCode(), u.get(i).getName());
				}
			}
			msg = "提交订单成功，已推送短信给相关人员";
		} catch (Exception e) {
			msg = "提交订单成功，短信推送失败";
		}
		return msg;
	}


	/* post访问，新建order数据 */
	// TODO time转换
	@RequestMapping(value = "/{orderId}", method = RequestMethod.POST)
	public ModelAndView changeOrder(
			@PathVariable(value = "orderId", required = true) String orderId,// 订单id
			@RequestParam(value = "oddNum", required = true) String oddNum,// 订单名称
			@RequestParam(value = "orderFile", required = false) MultipartFile[] orderFile,// 型号文件
			HttpSession session) {
		User user = (User) session.getAttribute("user");
		ModelAndView mvn = null;
		String msg = "";
		if (StringUtils.hasText(orderId) && StringUtils.hasText(oddNum)) {

			if (user != null) {
				Order order = orderRepository.findById(Long.parseLong(orderId));
				if (order.getOrderStatu().equals("3")) {
					mvn = new ModelAndView("xiadan");
				} else if (order.getOrderStatu().equals("1")) {
					mvn = new ModelAndView("xiaoshou");
				}
				Date now = new Date();
				// 文件路径为单号
				String filePath = Cons.filePath + order.getOrderName() + "//";
				File fileDir = new File(filePath);
				if (!fileDir.exists()) { // 如果不存在 则创建
					fileDir.mkdirs();
				}
				if (orderFile != null && orderFile.length > 1) {

					String file2name = "";
					for (int i = 0; i < orderFile.length; i++) {
						try {
							File modelF = new File(fileDir,
									orderFile[i].getOriginalFilename());
							orderFile[i].transferTo(modelF);
							if (i < orderFile.length - 1)
								file2name += order.getOrderName() + "/"
										+ orderFile[i].getOriginalFilename()
										+ "___";
							else if (i == orderFile.length - 1)
								file2name += order.getOrderName() + "/"
										+ orderFile[i].getOriginalFilename();
						} catch (Exception e) {
							System.out.println(e);
						}
						order.setModelFile(file2name);
					}
				} else {
					try {

						File modelF = new File(fileDir,
								orderFile[0].getOriginalFilename());
						orderFile[0].transferTo(modelF);

					} catch (Exception e) {
						System.out.println(e);
					}
					order.setModelFile(order.getOrderName() + "/"
							+ orderFile[0].getOriginalFilename());
				}

				DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String str = df.format(now);
				order.setOrderTime(str);// 当前时间戳
				order.setFileName(user.getName());
				order.setOddNum(oddNum);

				order.setOrderStatu("4");// 到制版
				List<User> u = userService.findUserByLevel("4");
				if (u != null && u.size() > 0) {
					for (int i = 0; i < u.size(); i++) {
						try {
							SendSMSUtil.SendSms(u.get(i).getCode(), u.get(i)
									.getName());
						}catch (Exception e){
							System.out.println(e);
						}
					}
				}
				Order o = orderRepository.save(order);
				if (o != null) {
					msg = "OK";
				} else {
					msg = "NO";
				}

			} else {
				msg = "NO";
			}

			mvn.getModelMap().put("msg", msg);
		}
		return mvn;
	}


	/**
	 * get 根据orderStatu查询order list  1_5  同时查询多个
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public List<Order> getOrderList(HttpSession session,
					@RequestParam(value = "orderStatu") String orderStatu) {
		User user = (User) session.getAttribute("user");
		if (user != null) {
			List<Order> list = new ArrayList<Order>();
			//  9_1/9_2/9_3.衬衫/礼服/运动
			if ("9_1".equals(orderStatu) || "9_2".equals(orderStatu)|| "9_3".equals(orderStatu)) {
				list = orderRepository.findByOrderStatu(orderStatu);
			} else {
				String[] splits = orderStatu.split("_");
				if (splits.length == 1)
					list = orderRepository.findByOrderStatu(orderStatu);
				else {
					for (int i = 0; i < splits.length; i++) {
						List<Order> list1 = orderRepository.findByOrderStatu(splits[i]);
						if (list1 != null)
							list.addAll(list1);
					}
				}
			}
			return list;
		} else {
			return null;
		}
	}

	@RequestMapping(params = "method=findOrder")
	public ModelAndView findOrder(HttpServletRequest request,
			HttpServletResponse response) {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		String oddNum = request.getParameter("oddNum");
		String partyA = request.getParameter("partyA");
		ModelAndView mvn = new ModelAndView("admin");
		if (user != null) {
			List<Order> list = null;
			if (!StringUtils.hasText(endTime)) {
				Date now = new Date();
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String str = df.format(now);
				endTime = str;
			} else {
				endTime = endTime + " 23:59:59";
			}
			if (StringUtils.hasText(startTime)) {
				startTime = startTime + " 00:00:00";
				list = orderRepository.findOrders(startTime, endTime);
			}
			if (StringUtils.hasText(oddNum)) {
				Order o = orderRepository.findByOddNum(oddNum);
				if (o != null) {
					list = new ArrayList<Order>();
					list.add(o);
				}
			}
			if (StringUtils.hasText(partyA))
				list = orderRepository.findBypartyA(partyA);
			if (!StringUtils.hasText(partyA) && !StringUtils.hasText(oddNum)
					&& !StringUtils.hasText(startTime)) {
				// 本月一号到当前时间
				Calendar calendar = Calendar.getInstance();
				calendar.set(Calendar.DAY_OF_MONTH, 1);
				calendar.add(Calendar.MONTH, 0);
			    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
			    startTime= sdf.format(calendar.getTime());
			    Date now = new Date();
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String str = df.format(now);
				endTime = str;
				list = orderRepository.findOrders(startTime,endTime);
			}
			if (list != null) {
				mvn.getModelMap().put("list", list);
				mvn.getModelMap().put("size", list.size());
			} else {
				mvn.getModelMap().put("size", 0);
			}

			mvn.getModelMap().put("startTime", startTime);
			mvn.getModelMap().put("endTime", endTime);
			mvn.getModelMap().put("oddNum", oddNum);
			mvn.getModelMap().put("partyA", partyA);

			return mvn;
		} else {
			return null;
		}
	}

	@RequestMapping(params = "method=findStatuById")
	@ResponseBody
	public String findStatuById(HttpServletRequest request,
			HttpServletResponse response) {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		String id = request.getParameter("id");
		String statu = null;
		if (user != null) {
			Order order = orderRepository.findById(Long.valueOf(id));
			String username = order.getUserName();
			User u = userService.findUserByName(username);
			statu = u.getTitle();
		}
		return statu;
	}

	/* post 修改订单数据 TODO 单独作为制版上传接口 */
	@RequestMapping(value = "/{id}/file", method = RequestMethod.POST)
	public String postFile(
			@PathVariable(value = "id", required = true) String id,// 订单id
			@RequestParam(value = "orderStatu", required = true) String orderStatu,// 要修改的订单状态
			@RequestParam(value = "file", required = false) MultipartFile file,// 上传的文件（可传可不穿）
			HttpSession session) {
		User user = (User) session.getAttribute("user");
		String msg = "";
		if (StringUtils.hasText(id) && StringUtils.hasText(orderStatu)) {

			if (user != null) {
				Order order = orderRepository.findById(Long.valueOf(id));
				if (order == null) {
					msg = "订单未找到";
					return msg;
				}
				order.setOrderStatu(orderStatu);
				if (file != null && file.isEmpty() && !orderStatu.equals("5")) {// 没有上传文件
																				// orderStatu!=2（制版）
																				// 不是来自制版人，只需修改状态
					orderRepository.save(order);
					msg = "更改成功";
				} else if (orderStatu.equals("5") && !file.isEmpty()) {// 制版上传
					String arr[] = file.getOriginalFilename().split("\\.");
					String suffixName = "";
					if (arr.length > 0) {// 获取文件后缀
						suffixName = arr[arr.length - 1];
					}
					// 当前时间获取
					String prefixName = "";
					SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd_");// 转换格式
					prefixName = sdf.format(new Date());
					String newFileName = prefixName + order.getOddNum() + "_"
							+ order.getPartyA() + "_制版." + suffixName;
					// 将要生成 yyMMdd_单号_甲方_制版.xls
					File file1 = new File(Cons.filePath + newFileName);
					try {
						file.transferTo(file1);
					} catch (Exception e) {
						System.out.println(e);
						return e.toString();
					}
					order.setFile2Name(newFileName);
					orderRepository.save(order);
					msg = "制版上传成功";

				} else {
					msg = "请上传制版文件";
				}
			}
		}
		return msg;
	}

	/* 制版上传制版文件 TODO 有重复的地方  需要删除 */
	@RequestMapping(value = "/addZhibanFile/{orderId}", method = RequestMethod.POST)
	public String addZhibanFile (
			@PathVariable Long orderId,// 订单id
			@RequestParam MultipartFile zhibanFile,// 上传的文件（可传可不穿）
			HttpSession session)throws  Exception {
		User user = (User) session.getAttribute("user");
		if(user==null){
			return "请登录";
		}
		String msg = "";
		Order order = orderRepository.findById(orderId);
		if (order == null) {
			msg = "订单未找到";
			return msg;
		}
		if (zhibanFile == null || zhibanFile.isEmpty()) {// 没有上传文件
			msg = "请添加制版文件";
		} else if ( !zhibanFile.isEmpty()) {// 制版上传
			try{
				//订单id文件夹
				String dirPath = Cons.filePath + String.valueOf(orderId) + "//";
				File fileDir = new File(dirPath);
				if (!fileDir.exists()) { // 如果不存在 则创建
					fileDir.mkdirs();
				}
				String fileName = String.valueOf( new Date().getTime());//当前时间戳 为文件名
				String yName = zhibanFile.getOriginalFilename();
				String ext = yName.substring(yName.lastIndexOf("."));//后缀
				String filePath = fileName+ext;
				File modelF = new File(fileDir,
						filePath);
				zhibanFile.transferTo(modelF);
				order.setMainFabricFile(filePath);//制版文件

				orderRepository.save(order);
				msg = "制版上传成功";
			}catch (Exception e){
				msg = "文件上传失败，请检查文件格式是否正确";
				System.out.println(e.getMessage());
				throw e;
			}

		} else {
			msg = "请上传制版文件";
		}
		return msg;
	}


	/**
	 * 管理员查询订单  GET Mapping
	 * */
	@GetMapping (value = "/adminFindOrder")
	public List<Order> adminFindOrder(@RequestParam(required = false) String startDate,
									   @RequestParam(required = false) String endDate,
									   HttpSession session) {

		List<Order> list = null;
		User user = (User) session.getAttribute("user");
		if(user==null||!"0".equals(user.getLevel())){//用户未登录 或 不是管理员
			return list;
		}
		if (endDate.length()<4) {//粗略判读时间格式
			return list;
		} else {
			endDate = endDate + " 23:59:59";
		}
		if (startDate.length()<4) {//粗略判读时间格式
			return list;
		} else {
			startDate = startDate + " 00:00:00";
		}
		list = orderRepository.findOrders(startDate, endDate);
		return list;
	}
}
