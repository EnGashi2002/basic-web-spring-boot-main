package com.edon.basic.web.controller;

import com.edon.basic.web.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class MainController {
    @GetMapping("/test")
    public String test(){
        return "Test Spring Boot! <b>Shuma e numrave 2 dhe 2.5 eshte:</b> " + sum(2,2.5);

    }

    @GetMapping("/user/list")
    public String userList() {
        String content = "<ul>";
        for(User user: createUsers()){
            content += "<li>"+user+"</li>";
        }
        content += "</ul>";
        return content;
    }

    private double sum(double a,double b) {
        return a+b;
    }

   private List<User> createUsers(){
     List users = new ArrayList<User>();
     users.add(new User("Rion", "Hasolli", 20));
     users.add(new User("Filane", "Fisteku", 18));
     users.add(new User("Filan", "Fisteku", 22));
     return users;
    }

}
