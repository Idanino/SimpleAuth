package org.startupnationcentral.SimpleAuth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class Controller {

    @Autowired
    InMemoryUserDetailsManager inMemoryUserDetailsManager;

    @PostMapping("/register") public String register(@RequestBody UserDetailsRequestModel userDetailsRequestModel)
    {
        inMemoryUserDetailsManager.createUser(User.withUsername(userDetailsRequestModel.getUsername()).password(userDetailsRequestModel.getPassword()).roles(userDetailsRequestModel.getRole()).build());
        return "User " + userDetailsRequestModel.getUsername() + " is Registered";
    }

    @GetMapping("/loginUser") public String loginUser()
    {
        return "You are Logged in";
    }

    @GetMapping("/logoutUser")
    public String logoutUser(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (null != auth) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
            request.getSession().invalidate();
        }
        return  "You are Logged out";
    }

    @GetMapping("/f1") public String f1()
    {
        return "f1 is allowed to all users";
    }

    @GetMapping("/f2") public String f2()
    {
        return "f2 is allowed only to logged in users";
    }

    @GetMapping("/f3") public String f3()
    {
        return "f3 is allowed only to logged in users with role3 and above";
    }

}
