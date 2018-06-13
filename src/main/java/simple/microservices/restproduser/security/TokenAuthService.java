package simple.microservices.restproduser.security;

import org.springframework.security.core.userdetails.User;

public interface TokenAuthService {
    User getUser(String token);
}
