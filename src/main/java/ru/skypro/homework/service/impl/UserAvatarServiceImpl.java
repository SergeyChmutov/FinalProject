package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.exception.AdsAvatarNotFoundException;
import ru.skypro.homework.model.User;
import ru.skypro.homework.model.UserAvatar;
import ru.skypro.homework.repository.UserAvatarRepository;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.UserAvatarService;
import ru.skypro.homework.service.UserService;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class UserAvatarServiceImpl implements UserAvatarService {

    final private UserAvatarRepository userAvatarRepository;
    final private UserService userService;
    private final UserRepository userRepository;

    @Override
    public ResponseEntity<byte[]> getUserAvatar(String username) {
        UserAvatar avatar = userAvatarRepository.findByUser_Email(username)
                .orElseThrow(() -> new AdsAvatarNotFoundException("Image for user with name " + username + " not found"));
        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.parseMediaType(avatar.getMediaType()));
        headers.setContentLength(avatar.getData().length);

        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(avatar.getData());
    }

    @Override
    @Transactional
    public void saveUserAvatar(String username, MultipartFile image) throws IOException {
        User user = userService.findByEmail(username);
        final UserAvatar avatar = userAvatarRepository.findByUser(user)
                .orElseGet(UserAvatar::new);

        avatar.setFileSize(image.getSize());
        avatar.setMediaType(image.getContentType());
        avatar.setData(image.getBytes());
        avatar.setUser(user);

        userAvatarRepository.save(avatar);

        if (user.getImage() == null || user.getImage().isBlank()) {
            user.setImage("/avatar/" + avatar.getId());
            userRepository.save(user);
        }
    }

}
