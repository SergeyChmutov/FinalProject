package ru.skypro.homework.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.skypro.homework.exception.AdsAvatarNotFoundException;
import ru.skypro.homework.model.UserAvatar;
import ru.skypro.homework.repository.UserAvatarRepository;
import ru.skypro.homework.service.UserAvatarService;

@Service
@AllArgsConstructor
public class UserAvatarServiceImpl implements UserAvatarService {

    final private UserAvatarRepository userAvatarRepository;

    @Override
    public ResponseEntity<byte[]> getUserAvatar(String username) {
        UserAvatar avatar = userAvatarRepository.findByUser_Email(username)
                .orElseThrow(() -> new AdsAvatarNotFoundException("Image for user with name " + username + " not found"));
        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.parseMediaType(avatar.getMediaType()));
        headers.setContentLength(avatar.getData().length);

        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(avatar.getData());
    }

}
