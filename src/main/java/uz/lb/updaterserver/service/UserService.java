package uz.lb.updaterserver.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.lb.updaterserver.config.CustomUserDetails;
import uz.lb.updaterserver.dto.AuthResponseDTO;
import uz.lb.updaterserver.dto.ResultDTO;
import uz.lb.updaterserver.entity.User;
import uz.lb.updaterserver.enums.GeneralStatus;
import uz.lb.updaterserver.enums.RoleEnum;
import uz.lb.updaterserver.exception.AppBadRequestException;
import uz.lb.updaterserver.exception.AppForbiddenException;
import uz.lb.updaterserver.exception.AppItemNotFoundException;
import uz.lb.updaterserver.payload.AuthPayload;
import uz.lb.updaterserver.payload.UserPayload;
import uz.lb.updaterserver.repository.UserRepository;
import uz.lb.updaterserver.utils.ConvertEntityToDTO;
import uz.lb.updaterserver.utils.JwtUtil;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    public ResponseEntity<ResultDTO> authorization(AuthPayload authPayload) {

        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authPayload.getLogin(), authPayload.getPassword()));
            if (authentication.isAuthenticated()) {
                CustomUserDetails customUser = (CustomUserDetails) authentication.getPrincipal();
                User user = findUserById(customUser.getId());
                AuthResponseDTO authResponseDTO = new AuthResponseDTO();
                authResponseDTO.setJwt(JwtUtil.encode(customUser.getUsername(), customUser.getRole().toString()));
                authResponseDTO.setUser(ConvertEntityToDTO.UserToUserDTO(user));
                authResponseDTO.setStatus(user.getStatus());

                System.out.println(authResponseDTO.toString());
                return ResponseEntity.ok(new ResultDTO().success(authResponseDTO));
            }
            return ResponseEntity.ok(new ResultDTO().error("Login and password wrong or user not active"));
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ResultDTO().error("Login or password wrong"));
        }
    }


    public ResponseEntity<ResultDTO> registrationUser(UserPayload userPayload) {

        User user = userRepository.findUserByLogin(userPayload.getLogin());

        if (user != null) {
            throw new AppBadRequestException("User with this login = " + userPayload.getLogin() + " exists");
        }

        user = userRepository.save(User.builder()
                .login(userPayload.getLogin())
                .password(passwordEncoder.encode(userPayload.getPassword()))
                .role(userPayload.getRole() == null ? RoleEnum.ROLE_USER : userPayload.getRole())
                .build());

        return ResponseEntity.ok(new ResultDTO().success(ConvertEntityToDTO.UserToUserDTO(user)));

    }

    public ResponseEntity<ResultDTO> getAllUser(CustomUserDetails currentUser) {

        List<User> users = userRepository.findAll();

        if (users.isEmpty()) {
            throw new AppItemNotFoundException("user does not exist");
        }
        return ResponseEntity.ok(new ResultDTO().success(ConvertEntityToDTO.UserListToListDTO(users)));
    }

    public ResponseEntity<ResultDTO> getUserByLogin(CustomUserDetails currentUser, String login) {
        User user = userRepository.findUserByLogin(login);
        if (user == null) {
            throw new AppItemNotFoundException("user with this login = " + login + " was not found");
        }
        return ResponseEntity.ok(new ResultDTO().success(ConvertEntityToDTO.UserToUserDTO(user)));
    }

    public ResponseEntity<ResultDTO> getUsersByLogin(CustomUserDetails currentUser, String login) {
        List<User> users = userRepository.findUsersByLogin(login);
        if (users.isEmpty()) {
            throw new AppItemNotFoundException("users does not exist");
        }
        return ResponseEntity.ok(new ResultDTO().success(ConvertEntityToDTO.UserListToListDTO(users)));
    }

    public ResponseEntity<ResultDTO> getUserById(CustomUserDetails currentUser, String id) {
        return ResponseEntity.ok(new ResultDTO().success(ConvertEntityToDTO.UserToUserDTO(findUserById(id))));
    }

    @Transactional
    public ResponseEntity<ResultDTO> updateUserById(CustomUserDetails currentUser, String id, UserPayload userPayload) {
        User updaterUser = findUserById(currentUser.getId());
        if (updaterUser.getRole().equals(RoleEnum.ROLE_ADMIN) || currentUser.getId() == id) {
            User user = findUserById(id);
            User userTemp = userRepository.findUserByLogin(userPayload.getLogin());
            if (userTemp != null && userTemp.getId() != id) {
                throw new AppBadRequestException("user with this login = " + userPayload.getLogin() + " exists");
            }
            user.setLogin(userPayload.getLogin());
            user.setPassword(passwordEncoder.encode(userPayload.getPassword()));
            user.setRole(userPayload.getRole() == null ? RoleEnum.ROLE_USER : userPayload.getRole());
            user.setVisible(userPayload.getVisible() == null ? Boolean.TRUE : userPayload.getVisible());
            user.setStatus(userPayload.getStatus() == null ? GeneralStatus.ACTIVE : userPayload.getStatus());
            user.setUpdatedByUserId(currentUser.getId());
            user = userRepository.save(user);
            return ResponseEntity.ok(new ResultDTO().success(ConvertEntityToDTO.UserToUserDTO(user)));
        }
        throw new AppForbiddenException("Forbidden...");
    }

    @Transactional
    public ResponseEntity<ResultDTO> deleteUserById(CustomUserDetails currentUser, String id) {
        User deleterUser = findUserById(currentUser.getId());
        if (deleterUser.getRole().equals(RoleEnum.ROLE_ADMIN) || currentUser.getId() == id) {
            User user = findUserById(id);
            user.setStatus(GeneralStatus.DELETED);
            userRepository.save(user);
            return ResponseEntity.ok(new ResultDTO().success("id = " + id + " user deleted."));
        }
        throw new AppForbiddenException("Forbidden...");
    }

    /*********************************************************************************************************************/
    private User findUserById(String userId) {
        return userRepository.findById(userId).orElseThrow(() -> {
            throw new AppItemNotFoundException("user not found with this id = " + userId);
        });
    }
}