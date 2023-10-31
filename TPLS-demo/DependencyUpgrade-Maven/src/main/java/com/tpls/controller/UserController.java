package com.tpls.controller;

import com.tpls.pojo.JsonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
@Slf4j
@RestController
public class UserController {

    @CrossOrigin
    @GetMapping("/")
    public JsonResult<Map<String,String>> home(HttpServletRequest request, HttpServletResponse response) {

        HttpSession session = request.getSession();//这就是session的创建
        session.setMaxInactiveInterval(30*60);//以秒为单位，即在没有活动30分钟后，session将失效
        String sessionId = session.getId();

        //添加session到cookie
        Cookie cookie = new Cookie("sessionId", sessionId);
        cookie.setPath(request.getContextPath());
        response.addCookie(cookie);


        Map<String,String> res=new HashMap<>();
        res.put("sessionId",sessionId);

        return new JsonResult<>(res);
    }

    private String getOrCreateUserId(HttpServletRequest request, HttpServletResponse response) {
        // 尝试获取用户标识符 Cookie
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("userId".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }

        // 如果没有用户标识符 Cookie，生成一个新的
        String userId = UUID.randomUUID().toString();

        // 创建一个新的 Cookie 并将其存储在用户浏览器中
        Cookie cookie = new Cookie("userId", userId);
        response.addCookie(cookie);

        return userId;
    }
}
