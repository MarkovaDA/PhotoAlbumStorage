package darya.markova.photostorage.service;

import darya.markova.photostorage.document.User;
import darya.markova.photostorage.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    //http://www.baeldung.com/spring-data-mongodb-tutorial - туториал
    //https://spring.io/guides/gs/securing-web/
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    public List<User> getUserList() {
        return userRepository.findAll();
    }

    public User getUser(String login) {
        Optional<User> data = userRepository.findById(login);

        if (data.isPresent()) {
            User user = data.get();
            return user;
        }
        return null;
    }

//    public User getUserByLogin(String login) {
//        User user = mongoTemplate.findOne(Query.query(Criteria.where("login").is(login)), User.class);
//        return user;
//    }

    public void addNewUser(User user) {
        userRepository.save(user);
    }
}
