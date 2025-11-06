package uz.lb.updaterserver.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.lb.updaterserver.dto.ResultDTO;
import uz.lb.updaterserver.entity.User;
import uz.lb.updaterserver.exception.AppItemNotFoundException;
import uz.lb.updaterserver.payload.UserPayload;
import uz.lb.updaterserver.repository.UserRepository;
import uz.lb.updaterserver.utils.ConvertEntityToDTO;

import java.util.Collections;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;


    public ResponseEntity<ResultDTO> saveUser(UserPayload userPayload) {

        User user = userRepository.save(User.builder()
                .login(userPayload.getLogin())
                .password(userPayload.getPassword())
                .build());

        return ResponseEntity.ok(new ResultDTO().success(ConvertEntityToDTO.UserToUserDTO(user)));

    }

    public ResponseEntity<ResultDTO> getAllUser() {
        List<User> users = userRepository.findAll();
        if (users.isEmpty()) {
            throw new AppItemNotFoundException("user does not exist");
        }
        return ResponseEntity.ok(new ResultDTO().success(ConvertEntityToDTO.ListToListDTO(Collections.singletonList(users))));
    }

    public ResponseEntity<ResultDTO> getUserByLogin(String login) {
        User user = userRepository.findUserByLogin(login);
        if (user == null) {
            throw new AppItemNotFoundException("user with this login = " + login + " was not found");
        }
        return ResponseEntity.ok(new ResultDTO().success(ConvertEntityToDTO.UserToUserDTO(user)));
    }

    public ResponseEntity<ResultDTO> getUserById(Long id) {
        return ResponseEntity.ok(new ResultDTO().success(ConvertEntityToDTO.UserToUserDTO(findUserById(id))));
    }

    @Transactional
    public ResponseEntity<ResultDTO> updateUserById(Long id, UserPayload userPayload) {
        return null;
    }

    @Transactional
    public ResponseEntity<ResultDTO> deleteUserById(Long id, UserPayload userPayload) {
        return null;
    }

    private User findUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> {
            throw new AppItemNotFoundException("user not found with this id = " + userId);
        });
    }
}