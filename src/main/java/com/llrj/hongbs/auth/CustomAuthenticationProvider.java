package com.llrj.hongbs.auth;


import com.llrj.hongbs.domain.User;
import com.llrj.hongbs.service.UserService;
import com.llrj.hongbs.utils.Md5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.*;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Configuration
@EnableWebSecurity
public class CustomAuthenticationProvider implements AuthenticationProvider {
    @Autowired
    private UserService userService;
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authentication;
        String username = token.getName();
        //从数据库找到的用户
        User user = null;
        if (username != null) {
            user = userService.findUserByName(username);
        }
        //
        if (user == null) {
            throw new UsernameNotFoundException("用户名/密码无效");
        }
        //数据库用户的密码
        String password = user.getPwd();
        String pwdDigest = Md5Util.pwdDigest(token.getCredentials().toString());
        //与authentication里面的credentials相比较
        if (!password.equals(pwdDigest)) {

            throw new BadCredentialsException("用户名/密码无效");
        }
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        session.setAttribute("user",user);
        //授权
        return new UsernamePasswordAuthenticationToken(user, password, user.getAuthorities());
    }

   /* public void config(WebSecurity web) {
        web.ignoring().antMatchers("/js/**", "/css/**", "/fonts/**", "/images/**");
    }*/

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.equals(authentication);
    }
}


