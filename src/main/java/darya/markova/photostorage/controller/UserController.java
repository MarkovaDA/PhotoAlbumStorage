package darya.markova.photostorage.controller;

import darya.markova.photostorage.document.User;
import darya.markova.photostorage.dto.UserDTO;
import darya.markova.photostorage.security.domain.CustomUserDetailsImpl;
import darya.markova.photostorage.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private UserService userService;

    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity getAll() {
        return new ResponseEntity(userService.getUserList(), HttpStatus.OK);
    }

    @GetMapping(/*value = "/{login}"*/value = "/", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<User> getUserById(/*@PathVariable("login")String login*/) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String login = ((CustomUserDetailsImpl) authentication.getPrincipal()).getUsername();

        User user = userService.getUser(login);

        if  (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    /**
     * Регистрировать нового пользователя.
     */
    @PostMapping(value = "/signup", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public void registUser(@RequestBody UserDTO user) {
        String passwordHashed = passwordEncoder.encode(user.getPassword());
        User newUser = new User(user.getLogin(), passwordHashed);
        userService.addNewUser(newUser);
    }
}
