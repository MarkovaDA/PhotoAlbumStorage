package darya.markova.photostorage.controller;

import darya.markova.photostorage.dto.UserDTO;
import darya.markova.photostorage.exception.UserUnathorizedException;
import darya.markova.photostorage.security.JwtTokenUtil;
import darya.markova.photostorage.security.domain.TokenObject;
import darya.markova.photostorage.security.service.CustomUserDetailsService;
import darya.markova.photostorage.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api")
public class AuthenticationController {

    UserDetailsService userDetailsService;

    UserService userService;

    PasswordEncoder passwordEncoder;

    JwtTokenUtil tokenUtil;

    @Autowired
    public AuthenticationController(
            CustomUserDetailsService userDetailsService,
            UserService userService,
            PasswordEncoder passwordEncoder
    ) {
        this.userDetailsService = userDetailsService;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.tokenUtil = new JwtTokenUtil();
    }

    @PostMapping(value = "/signin",
                 consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
                 produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<TokenObject> signIn(@RequestBody UserDTO user) throws UserUnathorizedException {

        authenticate(user);

        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getLogin());
        String token = this.tokenUtil.generateToken(userDetails);
        return new ResponseEntity<>(new TokenObject(token), HttpStatus.OK);
    }

    private void authenticate (UserDTO user) throws UserUnathorizedException {
        String hashedPassword = userService.getUser(user.getLogin()).getHashedPassword();
        if (!passwordEncoder.matches(user.getPassword(), hashedPassword)) {
            throw new UserUnathorizedException();
        }
    }
}
