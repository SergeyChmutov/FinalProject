package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.RegisterDTO;
import ru.skypro.homework.mapper.UserMapper;
import ru.skypro.homework.model.User;
import ru.skypro.homework.service.AuthService;
import ru.skypro.homework.service.UserService;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserDetailsManager manager;
    private final PasswordEncoder encoder;
    private final UserService userService;
    private final UserMapper userMapper;

    @Override
    public boolean login(String userName, String password) {
        final String usernameLowerCase = userName.toLowerCase();

        if (!manager.userExists(usernameLowerCase)) {
            return false;
        }
        UserDetails userDetails = manager.loadUserByUsername(usernameLowerCase);

        return encoder.matches(password, userDetails.getPassword());
    }

    @Override
    public boolean register(RegisterDTO register) {
        final User user = userMapper.registerDTOToUser(register);

        if (manager.userExists(user.getEmail())) {
            return false;
        }

        user.setPassword(encoder.encode(user.getPassword()));

        userService.saveUser(user);

        return true;
    }

}
