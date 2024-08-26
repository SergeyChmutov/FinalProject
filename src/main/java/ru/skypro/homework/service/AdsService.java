package ru.skypro.homework.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.AdDTO;
import ru.skypro.homework.dto.CreateOrUpdateAdDTO;
import ru.skypro.homework.model.Ad;

import java.io.IOException;
import java.util.List;

public interface AdsService {

    ResponseEntity<AdDTO> addAd(CreateOrUpdateAdDTO ad, String username, MultipartFile image) throws IOException;

    List<Ad> getAllAds();

    List<Ad> getUsersAds(String username);
}
