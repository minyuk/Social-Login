package com.social.oauthlogin.config.oauth;

import com.social.oauthlogin.config.auth.PrincipalDetails;
import com.social.oauthlogin.config.oauth.provider.GoogleUserInfo;
import com.social.oauthlogin.config.oauth.provider.NaverUserInfo;
import com.social.oauthlogin.config.oauth.provider.OAuth2UserInfo;
import com.social.oauthlogin.domain.User;
import com.social.oauthlogin.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service
@RequiredArgsConstructor
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {

    //구글로부터 받은 userRequest 데이터에 대한 후처리되는 함수
    // username = "google_103741740660424319563"
    // password = "암호화(겟인데어)"
    // email = "kmhyeok0816@gmail.com"
    // role = "ROLE_USER"
    // provider = "google"
    // providerId = 103741740660424319563

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final UserRepository userRepository;

    // 함수 종료 시 @AuthenticationPrincipal 어노테이션이 생성
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        System.out.println("getClientRegistration: " + userRequest.getClientRegistration());
        System.out.println("getAccessToken: " + userRequest.getAccessToken().getTokenValue());

        OAuth2User oAuth2User = super.loadUser(userRequest);
        //구글로그인 버튼 클릭 -> 구글 로그인 창 -> 로그인 완료 -> code return(OAuth-client) -> AccessToken 요청
        //userRequest 정보 -> loadUser 함수 -> 회원정보 수집
        System.out.println("getAttributes: " + super.loadUser(userRequest).getAttributes());

        OAuth2UserInfo oAuth2UserInfo = null;
        if(userRequest.getClientRegistration().getRegistrationId().equals("google")) {
            System.out.println("구글 로그인 요청");
            oAuth2UserInfo = new GoogleUserInfo(oAuth2User.getAttributes());
        } else if(userRequest.getClientRegistration().getRegistrationId().equals("naver")) {
            System.out.println("네이버 로그인 요청");
            oAuth2UserInfo = new NaverUserInfo((Map)oAuth2User.getAttributes().get("response"));
        } else {
            System.out.println("지원하지 않는 소셜입니다.");
        }

        //회원가입을 강제로 진행
        String provider = oAuth2UserInfo.getProvider();
        String providerId = oAuth2UserInfo.getProviderId();
        String username = provider + "_" + providerId;
        String password = bCryptPasswordEncoder.encode("겟인데어");
        String email = oAuth2UserInfo.getEmail();
        String role = "ROLE_USER";

        User userEntity = userRepository.findByUsername(username);
        if (userEntity == null) {
            userRepository.save(User.builder()
                    .username(username)
                    .password(password)
                    .email(email)
                    .role(role)
                    .provider(provider)
                    .providerId(providerId)
                    .build());
        }

        return new PrincipalDetails(userEntity, oAuth2User.getAttributes());
    }
}
