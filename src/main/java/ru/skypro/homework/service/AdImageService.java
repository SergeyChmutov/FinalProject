package ru.skypro.homework.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.model.Ad;
import ru.skypro.homework.model.AdImage;
import ru.skypro.homework.model.User;

import java.io.IOException;

@Service
public interface AdImageService {

    AdImage createAdImage(Ad ad, MultipartFile image) throws IOException;

    AdImage saveAdImage(AdImage image);

}
