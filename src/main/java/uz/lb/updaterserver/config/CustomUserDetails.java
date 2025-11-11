package uz.lb.updaterserver.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import uz.lb.updaterserver.entity.User;
import uz.lb.updaterserver.enums.GeneralStatus;
import uz.lb.updaterserver.enums.RoleEnum;
import uz.lb.updaterserver.exception.AppForbiddenException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Slf4j
public class CustomUserDetails implements UserDetails {

    private String id;
    private String login;
    private String password;
    private RoleEnum role;
    private Boolean visible;
    private GeneralStatus status;

    public CustomUserDetails(User user) {

        this.id = user.getId();
        this.login = user.getLogin();
        this.password = user.getPassword();
        this.role = user.getRole();
        this.status = user.getStatus();

        this.visible = user.getVisible();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        if (role == null) {
            log.warn("User '{}' has no role assigned.", login);
            return Collections.emptyList();
        }

        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(role.toString()));

        return authorities;
    }

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {

        if (status == GeneralStatus.BLOCK) {
            throw new AppForbiddenException(login + " is blocked");
        }

        if (status == GeneralStatus.DELETED) {
            throw new AppForbiddenException(login + " is deleted");
        }

        if (status == GeneralStatus.INACTIVE) {
            throw new AppForbiddenException(login + " is inactive");
        }

        if (status == GeneralStatus.PAUSED) {
            throw new AppForbiddenException(login + " is paused");
        }

        return status == GeneralStatus.ACTIVE;
    }


    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return status == GeneralStatus.ACTIVE && Boolean.TRUE.equals(visible);
    }

    public String getId() {
        return id;
    }

    public RoleEnum getRole() {
        return role;
    }

    public GeneralStatus getStatus() {
        return status;
    }



}
