package pl.kowalczuk.springmvc.service;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class SecurityContextService {

    public UserDetails getPrincipal() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        if (principal instanceof org.springframework.security.core.userdetails.User) {
            return (UserDetails) principal;
        } else
            return null;
    }

    public boolean isCurrentAuthenticated() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        if (principal instanceof org.springframework.security.core.userdetails.User) {
            return ((org.springframework.security.core.userdetails.User) principal).isEnabled();
        } else
            return false;
    }

    public void setAuthentication(UserDetails user) {
        UsernamePasswordAuthenticationToken authReq
                = new UsernamePasswordAuthenticationToken(user,
                null,user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authReq);
    }
}
