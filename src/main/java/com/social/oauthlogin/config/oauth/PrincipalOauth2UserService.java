package com.social.oauthlogin.config.oauth;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {

    //구글로부터 받은 userRequest 데이터에 대한 후처리되는 함수
    // username = "google_103741740660424319563"
    // password = "암호화(겟인데어)"
    // email = "kmhyeok0816@gmail.com"
    // role = "ROLE_USER"
    // provider = "google"
    // providerId = 103741740660424319563

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        //구글로그인 버튼 클릭 -> 구글 로그인 창 -> 로그인 완료 -> code return(OAuth-client) -> AccessToken 요청
        //userRequest 정보 -> loadUser 함수 -> 회원정보 수집

        OAuth2User oAuth2User = super.loadUser(userRequest);

        return super.loadUser(userRequest);
    }
}
