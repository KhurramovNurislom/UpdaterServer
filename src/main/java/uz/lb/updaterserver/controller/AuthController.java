package uz.lb.updaterserver.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import uz.lb.updaterserver.dto.ResultDTO;
import uz.lb.updaterserver.payload.AuthPayload;
import uz.lb.updaterserver.service.UserService;

@CrossOrigin
@Controller
@RequiredArgsConstructor
@RequestMapping("/updater-server")
public class AuthController {

    private final UserService userService;

    @Operation(summary = "Autorizatsiya", description = "Foydalanuvchi uchun yangi JWT token olish uchun API, berilgan token 24 soat amal qiladi")
    @ApiResponse(responseCode = "200", description = "Token muvoffaqqiyatli yaratildi")
    @PostMapping("/auth")
    public ResponseEntity<ResultDTO> authorization(@RequestBody AuthPayload authPayload) {
        return userService.authorization(authPayload);
    }
}
