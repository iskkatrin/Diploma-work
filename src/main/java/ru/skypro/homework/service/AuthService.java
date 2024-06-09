package ru.skypro.homework.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.Register;


@Service
public class AuthService {

    @Autowired
    private UserDetailsManager manager;
    @Autowired
    private PasswordEncoder encoder;

    //Проверяет учетные данные пользователя при входе.
    public boolean login(String userName, String password) {
        if (!manager.userExists(userName)) { // Проверяет, существует ли пользователь с указанным именем.
            return false;
        }
        UserDetails userDetails = manager.loadUserByUsername(userName); // Получает информацию о пользователе по имени.
        return encoder.matches(password, userDetails.getPassword()); // Сравнивает введенный пароль с хешированным паролем из базы данных.
    }

    // Регистрирует нового пользователя.
   public boolean register(Register register) {
       if (manager.userExists(register.getUsername())) { // Проверяет, существует ли пользователь с указанным именем.
           return false;
       }
       manager.createUser(
               User.builder()
                       .passwordEncoder(this.encoder::encode) // Задает хеширование пароля перед сохранением.
                       .password(register.getPassword())
                       .username(register.getUsername())
                       .roles(register.getRole())
                       .build());
       return true;
   }
}


