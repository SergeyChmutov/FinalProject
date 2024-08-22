package ru.skypro.homework.service;

import ru.skypro.homework.model.User;

public interface UserService {

    User save(User user);

    User findByEmail(String email);

}
