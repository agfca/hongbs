package com.llrj.hongbs.service;

import com.llrj.hongbs.domain.User;
import com.llrj.hongbs.domain.UserRepository;
import com.llrj.hongbs.utils.Md5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class UserService {
    @Autowired
    UserRepository userRepository;

    /**
     * @param username
     * @param pwd
     * @return User||null
     * 登录 ，md5 加密
     */
    public User login(String username,String pwd){
        //return userRepository.login(username, MD5.encode(pwd));
        return userRepository.login(username, Md5Util.pwdDigest(pwd));
    }

    public User findUserByName(String username){
        return userRepository.findUserByName(username);
    }
    
    public List<User> findUserByLevel(String level){
        return userRepository.findUserByLevel(level);
    }
    
    public List<String> findNumbersByLevel(String level){
        List<User> user = userRepository.findUserByLevel(level);
        List<String> numbers = new ArrayList<String>();
        for (int i = 0; i < user.size(); i++) {
        	 numbers.add(user.get(i).getCode());
		}
        return numbers;
    }

    /**
     * @param name
     * @param pwd
     * @param leave
     * @param code
     * @return
     * 添加用户
     */
    public User create(String name,String pwd,String leave,String code,String title){
        User newUser = new User();
        newUser.setName(name);
        newUser.setPwd(pwd);
        newUser.setLevel(leave);
        newUser.setCode(code);
        newUser.setTitle(title);
        return userRepository.save(newUser);
    }

    public List<User> findAllUser(){
        return userRepository.findAll();
    }
    public User findUserById(Integer id){
        return userRepository.findUserById(id);
    }
    public User save(User user){
        return userRepository.save(user);
    }
    /*根据ids删除多个user*/
    public int deleteUserById(List<Integer> ids){
        int result = 0;
        for (Integer id:ids) {
            userRepository.deleteById(id);
            result ++;
        }
        return result;
    }
}
