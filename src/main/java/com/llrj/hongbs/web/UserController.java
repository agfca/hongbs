package com.llrj.hongbs.web;

import com.llrj.hongbs.domain.Cons;
import com.llrj.hongbs.domain.Order;
import com.llrj.hongbs.domain.User;
import com.llrj.hongbs.service.UserService;
import com.llrj.hongbs.utils.Md5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/users")
// TODO 添加api访问权限
public class UserController {
	@Autowired
	UserService userService;

	/* get 查询所有用户 TODO 添加条件 如level等 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public List<User> getUserList() {
		List<User> users = userService.findAllUser();
		return users;
	}

	/* get 根据id查询用户 */
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public User getUser(@PathVariable Integer id) {
		User user = userService.findUserById(id);
		return user;
	}

	/* post 创建单个 user用户 */
	@RequestMapping(value = "/", method = RequestMethod.POST)
	public User postUser(
			@RequestParam(value = "name", required = true) String name,// 姓名
			@RequestParam(value = "level") String level,// 职位 TODO 为空时默认 为-1
			@RequestParam(value = "code") String code,
			@RequestParam(value = "id") String id,
			@RequestParam(value = "title") String title,
			HttpSession session) {
		// return userService.create(name,
		// MD5.encode(Cons.defaultPwd),level.isEmpty()?Cons.defaultLevel:level,code);
		if(StringUtils.hasText(id)){
			User u = userService.findUserById(Integer.parseInt(id));
			u.setName(name);
			u.setCode(code);
			return userService.save(u);
		}else{
			return userService.create(name, Md5Util.pwdDigest(Cons.defaultPwd),
					level.isEmpty() ? Cons.defaultLevel : level,code,title);
		}
		
	}

	/* put 更新user密码 修改当前用户pwd */
	@RequestMapping(value = "/", method = RequestMethod.PUT)
	public String putUser(
			@RequestParam(value = "newPwd", required = true) String newPwd,// 新密码
			@RequestParam(value = "oldPwd", required = true) String oldPwd,// 旧密码
			HttpSession session) {
		User user = (User) session.getAttribute("user");// 当前登录用户
		String msg = "";
		if (user != null) {
			if (oldPwd.equals(user.getPwd())) {
				user.setPwd(newPwd);
				User newUser = userService.save(user);
				msg = "修改成功";
			} else {
				msg = "密码错误";
			}
		} else {
			msg = "请登录";
		}
		return msg;
	}

	/* put 更新user level 指定用户level */
	@RequestMapping(value = "/level", method = RequestMethod.PUT)
	public String putLevel(
			@RequestParam(value = "userId", required = true) Integer userId,
			@RequestParam(value = "level", required = true) String level) {
		User user = userService.findUserById(userId);
		String msg = "";
		if (user != null) {
			user.setLevel(level);
			userService.save(user);
			msg = "修改成功";
		} else {
			msg = "用户不存在";
		}
		return msg;
	}

	/* delete 删除users */
	@RequestMapping(value = "/{ids}", method = RequestMethod.DELETE)
	public String deleteUser(@PathVariable String ids) {
		String[] arr = ids.split(",");// ids = 1,2,3,4
		List<Integer> idArr = new ArrayList<>();
		for (String id : arr) {
			idArr.add(Integer.valueOf(id));
		}
		String msg = "";
		int result = userService.deleteUserById(idArr);
		if (result > 0) {
			msg = "删除成功";
		} else {
			msg = "删除失败";
		}
		return msg;
	}

	/* delete 删除users */
	@RequestMapping(params = "method=toqx")
	public ModelAndView toqx(HttpServletRequest request,
			HttpServletResponse response) {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		ModelAndView mvn = new ModelAndView("quanxian");
		if (user != null) {
			return mvn;
		} else {
			return null;
		}
	}

	/* delete 删除users */
	@RequestMapping(params = "method=toadmin")
	public ModelAndView toadmin(HttpServletRequest request,
			HttpServletResponse response) {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		ModelAndView mvn = new ModelAndView("admin");
		if (user != null) {
			return mvn;
		} else {
			return null;
		}
	}

	@RequestMapping(params = "method=findAllUser")
	public List<User> findAllUser(HttpServletRequest request,
			HttpServletResponse response) {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		List<User> list = userService.findAllUser();
		if (user != null) {
			return list;
		} else {
			return null;
		}
	}

	@RequestMapping(params = "method=findUserByName")
	public User findUserByName(HttpServletRequest request,
			HttpServletResponse response) {
		HttpSession session = request.getSession();
		String username = request.getParameter("username");
		User user = (User) session.getAttribute("user");
		User user1 = userService.findUserByName(username);
		if (user != null) {
			return user1;
		} else {
			return null;
		}
	}

	@RequestMapping(params = "method=changePass")
	public ModelAndView changePass(HttpServletRequest request,
			HttpServletResponse response) {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		ModelAndView mvn = new ModelAndView("editPwd");
		if (user != null) {
			return mvn;
		} else {
			return null;
		}
	}

	@RequestMapping(params = "method=toPass")
	public String toPass(HttpServletRequest request,
			HttpServletResponse response) {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		String pass = request.getParameter("pass");
 		if (user != null) {
			user.setPwd(Md5Util.pwdDigest(pass));
 			userService.save(user);
			return "OK";
		} else {
			return "NO";
		}
 	}
}
