package com.example.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * @author: dj
 * @create: 2020-12-09 09:27
 * @description:
 */
@RestController
@RequestMapping("/test1/")
public class Test1Controller {

    @GetMapping("msg1")
    public String msg1(HttpSession session) {
        session.setAttribute("msg", "Hello SpringSession!");
        return "ok";
    }

    @GetMapping("msg2/{data}")
    public String msg2(HttpSession session, @PathVariable("data") String data) {
        session.setAttribute("data", data);
        return session.getId();
    }

}
