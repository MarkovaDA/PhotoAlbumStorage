package darya.markova.photostorage.security.domain;

import darya.markova.photostorage.document.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class CustomUserDetailsImpl implements UserDetails {

    /**
     * Дефолтная роль в системе.
     */
    private static final String DEFAULT_ROLE = "default";

    /**
     * Роли пользователя.
     */
    private List<GrantedAuthority> grantedAuthorities;

    /**
     * Имя пользователя - логин.
     */
    private String login;

    /**
     * Пароль - в захешированном виде.
     */
    private String password;

    /**
     * Единственный признак, указывающий, активен ли пользователь.
     */
    private boolean isActive;

    /**
     * @return {@link #grantedAuthorities}
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.grantedAuthorities;
    }

    /**
     * Конструктор без параметров.
     */
    public CustomUserDetailsImpl() {

    }

    /**
     * Конструктор - создаёт экземпляр {@link CustomUserDetailsImpl} из данных {@link User}.
     * @param user
     */
    public CustomUserDetailsImpl(User user) {
        if (user != null) {
            this.isActive = user.getLogin() != null && user.getHashedPassword() != null;
            this.login = user.getLogin();
            this.password = user.getHashedPassword();
            this.grantedAuthorities =
                    isActive ? Arrays.asList(new CustomUserGrantedAuthority(DEFAULT_ROLE)) : Collections.EMPTY_LIST;

        }
    }

    /**
     * @return {@link #password}
     */
    @Override
    public String getPassword() {
        return password;
    }

    /**
     * @return {@link #login}
     */
    @Override
    public String getUsername() {
        return login;
    }

    /**
     * @return {@link #isActive}
     */
    @Override
    public boolean isAccountNonExpired() {
        return isActive;
    }

    /**
     * @return {@link #isActive}
     */
    @Override
    public boolean isAccountNonLocked() {
        return isActive;
    }

    /**
     * @return {@link #isActive}
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return isActive;
    }

    /**
     * @return {@link #isActive}
     */
    @Override
    public boolean isEnabled() {
        return isActive;
    }
}
