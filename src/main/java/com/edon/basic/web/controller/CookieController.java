package com.edon.basic.web.controller;

import com.edon.basic.web.model.LoginModel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.util.Random;

@Controller
public class CookieController {
    @GetMapping("/login")
    public String getLogin(Model model, HttpServletRequest request, HttpServletResponse response) throws IOException {
        if(isUserLoggedIn(request.getCookies(), request.getRemoteAddr())){
            response.sendRedirect("/profile");
            return null;
        }
        model.addAttribute("loginModel", new LoginModel());
        model.addAttribute("loginAction", "/login");
        return "login";
    }

    @PostMapping("/login")
    public String postLogin(@ModelAttribute LoginModel loginModel, Model model,
                            HttpServletResponse response, HttpServletRequest request){
        String username = "admin";
        String password = "123456";

        if(username.equals(loginModel.getUsername()) &&
                password.equals(loginModel.getPassword())){
            String sessionId = this.generateRandomString(15);
            try {
                this.writeToFile(sessionId, request.getRemoteAddr());
            } catch (IOException e) {
                e.printStackTrace();
            }
            // create cookie
            response.addCookie(new Cookie("logged_in", "true"));
            response.addCookie(new Cookie("session_id", sessionId));
            // redirect /profile
            try {
                response.sendRedirect("/profile");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        loginModel.setPassword("");
        model.addAttribute("loginAction", "/login");
        model.addAttribute("loginModel", loginModel);
        model.addAttribute("error", "Username or Password is incorrect!");
        return "login";
    }

    @GetMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response){
        if(this.isUserLoggedIn(request.getCookies(), request.getRemoteAddr())){
            // delete cookies
            Cookie logoutCookie = new Cookie("logged_in", "false");
            logoutCookie.setMaxAge(0);
            response.addCookie(logoutCookie);

            Cookie endSessionCookie = new Cookie("session_id", "false");
            endSessionCookie.setMaxAge(0);
            response.addCookie(endSessionCookie);

            //delete session file
            String sessionId = getSessionIdFromRequest(request);
            deleteSessionFile(sessionId, request.getRemoteAddr());
        }

        try {
            response.sendRedirect("/login");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void deleteSessionFile(String sessionId, String ipAddr) {
        File sessionFile = new File("/Users/edoncct/appsessions/" + sessionId + "_"+ ipAddr + ".txt");

        sessionFile.delete();
    }

    private String getSessionIdFromRequest(HttpServletRequest request) {
        for (Cookie cookie : request.getCookies()){
            if(cookie.getName().equals("session_id"))
                return cookie.getValue();
        }

        return null;
    }

    @GetMapping("/profile")
    public String getProfile(HttpServletRequest request){
        if(this.isUserLoggedIn(request.getCookies(), request.getRemoteAddr())){
            return "profile";
        }
        return "login-error";
    }

    private boolean isUserLoggedIn(Cookie[] cookies, String ipAddr){
        if(cookies == null){
            return false;
        }

        boolean isLoggedInSet = false;
        boolean isSessionIdCorrect = false;

        for(Cookie cookie : cookies){
            if(cookie.getName().equals("logged_in") &&
                cookie.getValue().equals("true")){
                isLoggedInSet = true;
            }

            if(cookie.getName().equals("session_id") && this.doesSessionFileExist(cookie.getValue(), ipAddr)){
                isSessionIdCorrect = true;
            }
        }
        return isLoggedInSet && isSessionIdCorrect;
    }

    private void writeToFile(String sessionId, String ipAddr) throws IOException {
        String str = "";
        BufferedWriter writer = new BufferedWriter(new FileWriter("/Users/edoncct/appsessions/"+sessionId+"_"+ipAddr+".txt"));
        writer.write(str);

        writer.close();
    }

    private String generateRandomString(int length){
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        Random random = new Random();

        String generatedString = random.ints(leftLimit, rightLimit + 1)
                .limit(length)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();

        return generatedString;
    }

    private boolean doesSessionFileExist(String sessionId, String ipAddr){
        File sessionFile =  new File("/Users/edoncct/appsessions/"+sessionId+"_"+ipAddr+".txt");
        return sessionFile.exists();

        /**
         * In case we want to add expiration to session
                if(!sessionFile.exists()){
                    return false;
                }

                // check if the file is expired
                FileTime creationDate =  this.getFileCreationDate(sessionFile.getAbsolutePath());
                FileTime now = FileTime.fromMillis(System.currentTimeMillis());
                FileTime expirationDate = FileTime.fromMillis(creationDate.toMillis()+60000);

                int diff = now.compareTo(expirationDate);

                return diff <= 0;
         */
    }

    private FileTime getFileCreationDate(String path){
        try {
            BasicFileAttributes attr = Files.readAttributes(Path.of(path), BasicFileAttributes.class);
            return attr.creationTime();
        } catch (IOException ex) {
            // handle exception
        }
        return null;
    }
}
