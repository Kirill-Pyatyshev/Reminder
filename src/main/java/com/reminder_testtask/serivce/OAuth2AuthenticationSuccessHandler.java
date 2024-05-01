package com.reminder_testtask.serivce;

import com.reminder_testtask.entity.User;
import com.reminder_testtask.repository.UserRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;
//?????????????????
@Component
public class OAuth2AuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;
        OAuth2User oauthUser = oauthToken.getPrincipal();

        String user_id = String.valueOf(oauthUser.getAttributes().get("id"));

        if(userRepository.findById(user_id).isEmpty()){
            Map<String, Object> userInfo = oauthUser.getAttributes();

            User user = User.builder()
                    .id(user_id)
                    .name(String.valueOf(userInfo.get("login")))
                    .email(String.valueOf(userInfo.get("email")))
                    .locale(String.valueOf(userInfo.get("location")))
                    .lastVisit(LocalDateTime.now())
                    .telegramId(null)
                    .activeNotificationsTg(false)
                    .build();

            userRepository.save(user);
        }
        response.sendRedirect("/user/" + user_id); // Redirect to user
    }
}