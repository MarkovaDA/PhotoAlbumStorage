package darya.markova.photostorage.service;

import darya.markova.photostorage.document.User;
import darya.markova.photostorage.repository.UserRepository;
import darya.markova.photostorage.security.domain.CustomUserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    public List<User> getUserList() {
        return userRepository.findAll();
    }

    public User getUser(String login) {
        //Optional<User> data = userRepository.findById(login);
        //if (data.isPresent()) {
        //  User user = data.get();
        //  return user;
        // }
        User user = userRepository.findOne(login);
        return user;
    }


    public void addNewUser(User user) {
        userRepository.save(user);
    }

    public User getCurrentAuthUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String login = ((CustomUserDetailsImpl) authentication.getPrincipal()).getUsername();
        return this.getUser(login);
    }
}
