package darya.markova.photostorage.security.domain;

public class TokenObject {

    /**
     * Токен.
     */
    private String token;

    /**
     * Конструктор.
     * @param token
     */
    public TokenObject(String token) {
        this.token = token;
    }

    /**
     * Возвращает {@link #token}.
     * @return
     */
    public String getToken() {
        return token;
    }

    /**
     * Устанавливает новое значение для токена {@link #token}.
     * @param token
     */
    public void setToken(String token) {
        this.token = token;
    }
}
