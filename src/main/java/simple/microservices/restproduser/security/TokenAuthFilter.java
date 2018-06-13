package simple.microservices.restproduser.security;

import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class TokenAuthFilter extends GenericFilterBean {

    private Logger logger = LoggerFactory.getLogger(getClass().getName());

    @Autowired
    @Qualifier("theTokenService")
    private TokenAuthService authService;

    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {

        final HttpServletRequest httpRequest = (HttpServletRequest) request;
        final String tokenHeader = httpRequest.getHeader("authorization");

        if (tokenHeader != null) {
            if (!tokenHeader.startsWith("bearer")) {
                logger.info("wrong auth type: " + tokenHeader);
                ((HttpServletResponse) response).sendError(HttpServletResponse.SC_BAD_REQUEST,
                        "wrong auth type");
                return;
            }

            String[] arr = StringUtils.split(tokenHeader, ":");
            if (arr.length < 2) {
                logger.info("empty token");
                ((HttpServletResponse) response).sendError(HttpServletResponse.SC_BAD_REQUEST,
                        "empty token");
                return;
            }

            String token = arr[1];

            User user = authService.getUser(token);

            if (user == null) {
                logger.info("User from token not found");
                ((HttpServletResponse) response).sendError(HttpServletResponse.SC_UNAUTHORIZED,
                        "User from token not found");
                return;
            }

            final UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(user,
                            user,
                            user.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        chain.doFilter(request, response);
    }
}
