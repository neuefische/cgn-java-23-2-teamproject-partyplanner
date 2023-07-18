package org.partypets.backend.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user")
public class MongoUserController {

    private final MongoUserDetailService mongoUserDetailService;

    @GetMapping
    public UserWithoutPassword getUserWithoutPassword() {
        return this.mongoUserDetailService.getUserWithoutPassword(SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName());
    }

    @GetMapping("/me")
    public String getMe() {
        return SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();
    }

    @PostMapping("/login")
    public String login() {
        return SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();
    }

    @PostMapping("/logout")
    public void logout(Authentication authentication, HttpServletRequest request, HttpServletResponse response) {
        SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();
        logoutHandler.logout(request, response, authentication);
    }

    @PostMapping("/register")
    public void register(@RequestBody UserWithoutId userWithoutId){
       this.mongoUserDetailService.registerNewUser(userWithoutId);
    }

}
