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
import uz.lb.updaterserver.dto.AuthResponseDTO;
import uz.lb.updaterserver.dto.ResultDTO;
import uz.lb.updaterserver.entity.User;
import uz.lb.updaterserver.enums.GeneralStatus;
import uz.lb.updaterserver.exception.AppBadRequestException;
import uz.lb.updaterserver.exception.AppItemNotFoundException;
import uz.lb.updaterserver.exception.AppPermissionDeniedException;
import uz.lb.updaterserver.payload.AuthPayload;
import uz.lb.updaterserver.payload.UserPayload;
import uz.lb.updaterserver.repository.UserRepository;
import uz.lb.updaterserver.utils.ConvertEntityToDTO;
import uz.lb.updaterserver.utils.JwtUtil;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;




}