package uz.lb.updaterserver.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
    public ResponseEntity<ResultDTO> getAllUser() {
        return userService.getAllUser();
    }

    @PostMapping("/save")
    public ResponseEntity<ResultDTO> saveUser(@RequestBody UserPayload userPayload) {
        return userService.saveUser(userPayload);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResultDTO> getUserById(@PathVariable(value = "id") Long id) {
        return userService.getUserById(id);
    }

    @GetMapping("/")
    public ResponseEntity<ResultDTO> getUserByLogin(@RequestParam(value = "login") String login) {
        return userService.getUserByLogin(login);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResultDTO> updateUserById(@PathVariable(value = "id") Long id, @RequestBody UserPayload userPayload) {
        return userService.updateUserById(id, userPayload);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResultDTO> deleteUserById(@PathVariable(value = "id") Long id, @RequestBody UserPayload userPayload) {
        return userService.deleteUserById(id, userPayload);
    }

}
