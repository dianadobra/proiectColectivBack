package com.webcbg.eppione.security;

import com.webcbg.eppione.companysettings.model.User;
import com.webcbg.eppione.companysettings.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Spring Security success handler, specialized for Ajax requests.
 */
@Component
public class AjaxAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final Logger log = LoggerFactory.getLogger(AjaxAuthenticationSuccessHandler.class);

    @Autowired
    private UserRepository userRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication)
            throws IOException, ServletException {
        SecurityUtils.sendResponse(response, HttpServletResponse.SC_OK, getUserFromAuth(authentication));
    }

    private User getUserFromAuth(Authentication authentication) {
        return userRepository.findOneByUsername(authentication.getName()).get();
    }
}
