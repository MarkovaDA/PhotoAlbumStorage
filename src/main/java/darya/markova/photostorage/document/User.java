package darya.markova.photostorage.document;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.crypto.password.PasswordEncoder;

@Document
public class User {

//    @Id
//    @Deprecated
//    private Integer id;

    private PasswordEncoder passwordEncoder;

    @Id
    private String login;
    private String hashedPassword; //зашифрованный пароль

    public User(
//            Integer id,
            String login,
            String hashedPassword
    ) {
//        this.id = id;
        this.login = login;
        this.hashedPassword = hashedPassword;
    }

//    public Integer getId() {
//        return id;
//    }
//
//    public void setId(Integer id) {
//        this.id = id;
//    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
}
