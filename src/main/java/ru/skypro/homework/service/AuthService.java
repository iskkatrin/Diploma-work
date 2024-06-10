package ru.skypro.homework.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.Register;
import ru.skypro.homework.entity.UserEntity;
import ru.skypro.homework.mapper.UserMapper;


@Service
@RequiredArgsConstructor
public class AuthService {

    private static final Logger log = LoggerFactory.getLogger(AuthService.class);
    //    private static final Logger log = LoggerFactory.getLogger(AuthService.class);
    @Autowired
    private UserDetailsManager manager;
    @Autowired
    private final PasswordEncoder encoder;
    @Autowired
    private final AuthenticationManager authenticationManager;
    @Autowired
    private UserMapper userMapper;




    public boolean login(String userName, String password) {
//        if (!manager.userExists(userName)) {
//            return false;
//        }
        log.info("username={}, password={}", userName, password);
//        UserDetails userDetails = manager.loadUserByUsername(userName);
//        return encoder.matches(password, userDetails.getPassword());
        try {
            Authentication authenticationToken = new UsernamePasswordAuthenticationToken(userName, password);
            Authentication authentication = authenticationManager.authenticate(authenticationToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return true;
        } catch (AuthenticationException e) {
            log.error("Не удалось войти в систему для пользователя: {}", userName, e);
            return false;
        }
    }


    public boolean register(Register register) {
        if (manager.userExists(register.getUsername())) {
            return false;
        }
//        manager.createUser(
//                User.builder()
//                        .passwordEncoder(this.encoder::encode)
//                        .password(register.getPassword())
//                        .username(register.getUsername())
//                        .roles(register.getRole())
//                        .build());
        UserEntity userEntity = userMapper.registerToUserEntity(register);
        userEntity.setPassword(encoder.encode(register.getPassword()));
        return true;
    }
}

