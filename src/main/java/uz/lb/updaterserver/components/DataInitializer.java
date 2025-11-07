package uz.lb.updaterserver.components;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import uz.lb.updaterserver.entity.*;
import uz.lb.updaterserver.enums.RoleEnum;
import uz.lb.updaterserver.repository.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Value(value = "${user.default.name}")
    String username;

    @Value(value = "${user.default.password}")
    String password;

    @Override
    @Transactional
    public void run(String... args) {
        User user = userRepository.findUserByLogin(username);
        if (user == null) {
            userRepository.save(User.builder()
                    .login(username)
                    .password(passwordEncoder.encode(password))
                    .role(RoleEnum.ROLE_ADMIN)
                    .build());
            log.info("\n\nSuper admin created: login: {}; password: {}; \n" +
                            "To ensure security, you are required to change your password to use the application's features.\n",
                    username, password);
        } else {
            log.info("User already exists -> {}", user.getLogin());
        }
    }
}
