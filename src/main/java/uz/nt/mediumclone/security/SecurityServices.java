package uz.nt.mediumclone.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import uz.nt.mediumclone.dto.UserDto;
import uz.nt.mediumclone.exeption.UserNotFoundException;
import uz.nt.mediumclone.model.User;

import java.util.Optional;

@Component
public class SecurityServices {

    public User getLoggedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username1 = null;
        User principal = null;
        if (authentication != null && authentication.isAuthenticated()) {
            principal = (User) authentication.getPrincipal();
        }

        if (principal == null) {
            throw new UserNotFoundException("Can not get logged user info from SecurityContextHolder!");
        }
        return principal;
    }
}
