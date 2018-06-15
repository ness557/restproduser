package simple.microservices.restproduser.security;

import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.lang.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@Qualifier("theTokenService")
public class TokenAuthRestService implements TokenAuthService {

    private RestTemplate restTemplate;

    private Logger logger = LoggerFactory.getLogger(getClass().getName());

    @Value("${auth.host}")
    private String tokenHost;

    @Value("${auth.path.user}")
    private String path;

    public TokenAuthRestService() {
        restTemplate = new RestTemplate();
    }

    @Override
    @Nullable
    public User getUser(String token) {

        String req = tokenHost
                + path
                + "?token="
                + token;
        logger.info("Trying to get user by token: " + token);

        ResponseEntity entity = restTemplate.getForEntity(req, JSONObject.class);

        if (entity.getStatusCode().equals(HttpStatus.OK)) {
            JSONObject userObject = (JSONObject) entity.getBody();

            logger.info("Got user: " + userObject.toJSONString());

            String auths = (String) userObject.get("authorities");

            // remove [ ]
            auths = auths.substring(1, auths.length() - 1);

            List<GrantedAuthority> authList = new ArrayList();
            for (String str : StringUtils.split(auths, ", ")) {

                authList.add(
                        new SimpleGrantedAuthority(str));
            }

            return new User((String) userObject.get("username"),
                    (String) userObject.get("password"),
                    true, true, true, true,
                    authList);
        }
        return null;
    }
}
