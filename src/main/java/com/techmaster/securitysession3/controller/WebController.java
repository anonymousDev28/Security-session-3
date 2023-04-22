package com.techmaster.securitysession3.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class WebController {
    @GetMapping("/")
    public String getHome(Model model){
        model.addAttribute("name","Mạnh Quang");
        List<String> users = List.of("Nguyen van A","Nguyen van B","Nguyen van C");
        model.addAttribute("users",users);
        return "index";
    }
    @GetMapping("user")
    public String getUser(){
        return "User";
    }
    @GetMapping("admin")
    public String getAdmin(){
        return "admin";
    }
    @GetMapping("author")
    public String getAuthor(){
        return "author";
    }
    @GetMapping("login")
    public String getLogin(){
        return "login";
    }
}
