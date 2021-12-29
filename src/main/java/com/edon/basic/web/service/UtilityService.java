package com.edon.basic.web.service;

import com.edon.basic.web.model.UserEntity;
import com.edon.basic.web.model.UserEntitySession;
import com.edon.basic.web.repository.UserEntitySessionRepository;
import com.edon.basic.web.repository.UserRepository;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import java.util.Arrays;
import java.util.Optional;
import java.util.Random;

@Service
public class UtilityService {

//    private UserEntitySessionRepository userEntitySessionRepository;
//    private UserRepository userRepository;
//
//    public UserEntitySession getUserSessionByToken(String token){
//        return userEntitySessionRepository.findBySessionHashCode(token);
//    }
//
//    public UtilityService(UserEntitySessionRepository userEntitySessionRepository, UserRepository userRepository){
//        this.userEntitySessionRepository = userEntitySessionRepository;
//        this.userRepository = userRepository;
//    }

    public boolean isUserLoggedIn(Cookie[] cookies){
        return this.getLoggedInUser(cookies) != null;
    }

//    public UserEntity getLoggedInUser(Cookie[] cookies) {
//        if (cookies == null) {
//            return null;
//        }
//
//        Optional<Cookie> cookieOptional = Arrays.stream(cookies)
//                .filter(c -> c.getName().equals("logged_in")
//                        && c.getValue().equals("true"))
//                .findFirst();
//
//        if(!cookieOptional.isPresent()){
//            return null;
//        }
//
//        Optional<Cookie> sessionCookieOptional = Arrays.stream(cookies)
//                .filter(c -> c.getName().equals("session_id"))
//                .findFirst();
//
//        if(!sessionCookieOptional.isPresent()){
//            return null;
//        }
//
//        UserEntitySession userEntitySession =
//                this.userEntitySessionRepository
//                        .findBySessionHashCode(sessionCookieOptional.get().getValue());
//
//        if(userEntitySession == null){
//            return null;
//        }
//
//        return userEntitySession.getUserEntity();
//    }

    public String generateRandomString(int length) {
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        Random random = new Random();

        String generatedString = random.ints(leftLimit, rightLimit + 1)
                .limit(length)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();

        return generatedString;
    }
}
