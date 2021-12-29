package com.edon.basic.web.controller;

import com.edon.basic.web.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping("/auth")
public class ResumeController {

    @GetMapping ("/login")
    public String getLogin (Model model){

       return "Resume-Login";

    }
    @PostMapping("/login")
    public String postLogin(){

        return "Resume-Login";
    }
    @GetMapping("/new-acc")
    public String getNewAcc(Model model, HttpServletRequest request, HttpServletResponse response) throws IOException{

            response.sendRedirect("/homepage");


        model.addAttribute("user", new User());
        return "new-acc";
    }
    @PostMapping("/new-acc")
    public String postNewAcc(){

        return "welcome";
    }


}
