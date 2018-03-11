package darya.markova.photostorage.security.service;

import darya.markova.photostorage.document.User;
import darya.markova.photostorage.security.domain.CustomUserDetailsImpl;
import darya.markova.photostorage.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    /**
     * {@link UserService}
     */
    UserService userService;

    /**
     * Конструктор.
     * @param userService
     */
    @Autowired
    public CustomUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        return getUserDetails(userService.getUser(login));
    }

    private UserDetails getUserDetails(User user) {
        return new CustomUserDetailsImpl(user);
    }
}
