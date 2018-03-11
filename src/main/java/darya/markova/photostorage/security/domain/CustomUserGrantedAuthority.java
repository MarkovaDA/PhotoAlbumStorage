package darya.markova.photostorage.security.domain;

import org.springframework.security.core.GrantedAuthority;

public class CustomUserGrantedAuthority implements GrantedAuthority {

    /**
     * Наименование роли пользователя - она единственная в системе.
     */
    private static final String USER_ROLE_NAME = "ROLE_USER";

    private final String role;

    public CustomUserGrantedAuthority(String role) {
        this.role = USER_ROLE_NAME;
    }

    @Override
    public String getAuthority() {
        return this.role;
    }

    public boolean equals(Object obj) {
        return obj instanceof CustomUserGrantedAuthority;
    }

    public int hashCode() {
        return this.role.hashCode();
    }

    public String toString() {
        return this.role;
    }

}
