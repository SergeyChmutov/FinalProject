package ru.skypro.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.skypro.homework.model.Ad;
import ru.skypro.homework.model.AdImage;
import ru.skypro.homework.model.User;

import java.util.Optional;

@Repository
public interface AdImageRepository extends JpaRepository<AdImage, Integer> {

    Optional<AdImage> findById(Integer id);

    Optional<AdImage> findByAd(Ad ad);

}
