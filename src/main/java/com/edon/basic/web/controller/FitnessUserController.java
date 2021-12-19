package com.edon.basic.web.controller;

import com.edon.basic.web.model.FitnesUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@Controller
public class FitnessUserController {

    ArrayList<FitnesUser> users = new ArrayList<>();


    @GetMapping ("/register-user")
    public String getRegisterUser(Model model){
        model.addAttribute("user",new FitnesUser());
        return "RegisterForm";
    }

    @PostMapping("/register-user")
    public String postUsers(Model model, @ModelAttribute FitnesUser user, HttpServletResponse response){
        users.add(user);
        try {
            response.sendRedirect("/userList");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @GetMapping("/userList")
    public  String getList(Model model){
        model.addAttribute("users",users);
        return "userList";
    }

    @DeleteMapping("/user")
    public String deleteUser(@RequestParam("index") int index){
        users.remove(index);
        return null;
    }




}
