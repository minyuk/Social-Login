package com.social.oauthlogin.controller;

import com.social.oauthlogin.config.auth.PrincipalDetails;
import com.social.oauthlogin.domain.User;
import com.social.oauthlogin.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class IndexController {

    private final UserService userService;

    @GetMapping({"", "/"})
    public String index() {
        return "index";
    }

    //OAuth 로그인 -> PrincipleDetails
    //일반 로그인 -> PrincipleDetails
    @GetMapping("/user")
    public @ResponseBody String user(@AuthenticationPrincipal PrincipalDetails principalDetails) {
        System.out.println("getUsername: " + principalDetails.getUser().getUsername());
        System.out.println("getPassword: " + principalDetails.getUser().getPassword());
        System.out.println("getEmail: " + principalDetails.getUser().getEmail());
        System.out.println("getRole: " + principalDetails.getUser().getRole());
        System.out.println("getProvider: " + principalDetails.getUser().getProvider());
        System.out.println("getProviderId: " + principalDetails.getUser().getProviderId());

        return "user";
    }

    @GetMapping("/admin")
    public @ResponseBody String admin() {
        return "admin";
    }

    @GetMapping("/manager")
    public @ResponseBody String manager() {
        return "manager";
    }

    @GetMapping("/loginForm")
    public String loginForm() {
        return "loginForm";
    }

    @GetMapping("/joinForm")
    public String joinForm() {
        return "joinForm";
    }

    @PostMapping("/join")
    public String join(User user) {
        userService.join(user);

        return "redirect:/loginForm";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/info")
    public @ResponseBody String info() {
        return "개인정보";
    }

    @PreAuthorize("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
    @GetMapping("/data")
    public @ResponseBody String data() {
        return "데이터 정보";
    }

}
