package uz.lb.updaterserver.config;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import uz.lb.updaterserver.entity.User;
import uz.lb.updaterserver.exception.AppItemNotFoundException;
import uz.lb.updaterserver.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {

        User user = userRepository.findByLogin(username)
                .orElseThrow(() -> new AppItemNotFoundException("User with " + username + " login not found"));

        return new CustomUserDetails(user);
    }
}
