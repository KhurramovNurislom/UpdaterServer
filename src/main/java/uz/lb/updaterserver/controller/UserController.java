package uz.lb.updaterserver.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import uz.lb.updaterserver.config.CustomUserDetails;
import uz.lb.updaterserver.dto.ResultDTO;
import uz.lb.updaterserver.payload.UserPayload;
import uz.lb.updaterserver.service.UserService;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/all")
    public ResponseEntity<ResultDTO> getAllUser(@AuthenticationPrincipal CustomUserDetails currentUser) {
        return userService.getAllUser(currentUser);
    }

    @PostMapping("/reg")
    public ResponseEntity<ResultDTO> saveUser(@RequestBody UserPayload userPayload) {
        return userService.registrationUser(userPayload);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResultDTO> getUserById(@AuthenticationPrincipal CustomUserDetails currentUser, @PathVariable(value = "id") Long id) {
        return userService.getUserById(currentUser, id);
    }

    @GetMapping("/login")
    public ResponseEntity<ResultDTO> getUserByLogin(@AuthenticationPrincipal CustomUserDetails currentUser, @RequestParam(value = "login") String login) {
        return userService.getUserByLogin(currentUser, login);
    }
   @GetMapping("/users-by-login")
    public ResponseEntity<ResultDTO> getUsersByLogin(@AuthenticationPrincipal CustomUserDetails currentUser, @RequestParam(value = "login") String login) {
        return userService.getUsersByLogin(currentUser, login);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResultDTO> updateUserById(@AuthenticationPrincipal CustomUserDetails currentUser, @PathVariable(value = "id") Long id, @RequestBody UserPayload userPayload) {
        return userService.updateUserById(currentUser, id, userPayload);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResultDTO> deleteUserById(@AuthenticationPrincipal CustomUserDetails currentUser, @PathVariable(value = "id") Long id) {
        return userService.deleteUserById(currentUser, id);
    }

}
