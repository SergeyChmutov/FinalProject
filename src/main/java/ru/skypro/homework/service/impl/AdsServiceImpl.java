package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.AdDTO;
import ru.skypro.homework.dto.CreateOrUpdateAdDTO;
import ru.skypro.homework.dto.ExtendedAdDTO;
import ru.skypro.homework.enums.Role;
import ru.skypro.homework.exception.AdsAdNotFoundException;
import ru.skypro.homework.mapper.AdMapper;
import ru.skypro.homework.model.Ad;
import ru.skypro.homework.model.User;
import ru.skypro.homework.repository.AdRepository;
import ru.skypro.homework.service.AdImageService;
import ru.skypro.homework.service.AdsService;
import ru.skypro.homework.service.UserService;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdsServiceImpl implements AdsService {

    private final AdRepository adRepository;
    private final AdMapper adMapper;
    private final UserService userService;
    private final AdImageService adImageService;

    @Override
    @Transactional
    public ResponseEntity<AdDTO> addAd(CreateOrUpdateAdDTO ad, String username, MultipartFile image) throws IOException {
        Ad adFromAdDTO = adMapper.createOrUpdateAdDTOToAd(ad);
        User user = userService.findUserByEmail(username);

        adFromAdDTO.setUser(user);

        Ad createdAd = adRepository.save(adFromAdDTO);

        adImageService.createAdImage(createdAd, image);

        return ResponseEntity.status(HttpStatus.CREATED).body(adMapper.adToAdDTO(createdAd));
    }

    @Override
    public List<Ad> getAllAds() {
        return adRepository.findAll();
    }

    @Override
    public List<Ad> getUsersAds(String username) {
        User user = userService.findUserByEmail(username);
        return adRepository.findAllByUser(user);
    }

    @Override
    public ResponseEntity<ExtendedAdDTO> getExtendedAdInfo(Integer id) {
        Optional<Ad> ad = adRepository.findById(id);

        if (ad.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        ExtendedAdDTO extendedAd = adMapper.adToExtendedAdDTO(ad.get());

        return ResponseEntity.ok(extendedAd);
    }

    @Override
    public Ad getAdById(Integer id) {
        Ad ad = adRepository.findById(id)
                .orElseThrow(() -> new AdsAdNotFoundException("Ad with id " + id + " not found."));

        return ad;
    }

    @Override
    @Transactional
    public HttpStatus deleteAdById(Integer id, Authentication authentication) {
        try {
            User user = userService.findUserByEmail(authentication.getName());
            Ad ad = getAdById(id);

            Role userRole = user.getRole();
            boolean isAuthorAd = (ad.getUser() == user);
            boolean userHasPermit = isAuthorAd || userRole == Role.ADMIN;

            if (userHasPermit) {
                adRepository.delete(id);
                return HttpStatus.OK;
            } else {
                return HttpStatus.FORBIDDEN;
            }
        } catch (UsernameNotFoundException e) {
            return HttpStatus.NO_CONTENT;
        } catch (AdsAdNotFoundException e) {
            return HttpStatus.NOT_FOUND;
        }
    }

}
