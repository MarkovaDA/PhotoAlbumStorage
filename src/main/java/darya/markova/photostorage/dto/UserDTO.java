package darya.markova.photostorage.dto;

public class UserDTO {

    /**
     * Идентификатор пользователя.
     */
    private String login;

    /**
     * Пароль.
     */
    private String password;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
