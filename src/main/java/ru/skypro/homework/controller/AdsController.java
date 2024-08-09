package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.*;
import ru.skypro.homework.enums.Role;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;

@RestController
@RequestMapping("/ads")
public class AdsController {

    @Operation(tags = "Ads", operationId = "getAllAds", summary = "Receive all ads")
    @GetMapping
    public ResponseEntity<AdsDTO> getAllAds() {
        AdsDTO ads = AdsDTO.builder()
                .count(0)
                .results(List.of())
                .build();

        return ResponseEntity.ok(ads);
    }

    @Operation(tags = "Ads", operationId = "addAd", summary = "Adding an ad")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<AdDTO> addAd(
            @RequestParam MultipartFile image,
            @RequestBody CreateOrUpdateAdDTO ad
    ) throws IOException {
        AdDTO createdAd = AdDTO.builder()
                .author(0)
                .image("")
                .pk(0)
                .price(ad.getPrice())
                .title(ad.getTitle())
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(createdAd);
    }

    @Operation(tags = "Comments", operationId = "getComments", summary = "Receiving comments on the ad")
    @GetMapping("/{id}/comments")
    public ResponseEntity<CommentsDTO> getComments(@PathVariable Integer id) {
        List<CommentDTO> comments = List.of();

        if (comments.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else {
            CommentsDTO result = CommentsDTO.builder()
                    .count(comments.size())
                    .results(comments)
                    .build();

            return ResponseEntity.ok(result);
        }
    }

    @Operation(tags = "Comments", operationId = "addComment", summary = "Adding a comment to an ad")
    @PostMapping("/{id}/comments")
    public ResponseEntity<CommentDTO> addComment(
            @PathVariable Integer id,
            @RequestBody CreateOrUpdateCommentDTO comment
    ) {
        if (id <= 0) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        CommentDTO addedComment = CommentDTO.builder()
                .author(id)
                .authorImage("")
                .authorFirstName("")
                .createdAt(Calendar.getInstance().getTimeInMillis())
                .pk(0)
                .text(comment.getText())
                .build();

        return ResponseEntity.ok(addedComment);
    }

    @Operation(tags = "Ads", operationId = "getAds", summary = "Getting information about the ad")
    @GetMapping("/{id}")
    public ResponseEntity<ExtendedAdDTO> getAds(@PathVariable Integer id) {
        if (id <= 0) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        ExtendedAdDTO foundedAd = ExtendedAdDTO.builder()
                .pk(0)
                .authorFirstName("")
                .authorLastName("")
                .description("")
                .email("")
                .image("")
                .phone("")
                .price(0)
                .title("")
                .build();

        return ResponseEntity.ok(foundedAd);
    }

    @Operation(tags = "Ads", operationId = "removeAd", summary = "Deleting an ad")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> removeAd(@PathVariable Integer id) {
        if (id <= 0) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

        boolean adExist = true;

        if (adExist) {
            Role userRole = Role.ADMIN;
            boolean isAuthorAd = false;
            boolean userHasPermit = isAuthorAd || userRole == Role.ADMIN;

            if (userHasPermit) {
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @Operation(tags = "Ads", operationId = "updateAds", summary = "Updating ad information")
    @PatchMapping("/{id}")
    public ResponseEntity<AdDTO> updateAds(@PathVariable Integer id, @RequestBody CreateOrUpdateAdDTO ad) {
        boolean adExist = true;

        if (adExist) {
            Role userRole = Role.ADMIN;
            boolean isAuthorAd = false;
            boolean userHasPermit = isAuthorAd || userRole == Role.ADMIN;

            if (userHasPermit) {
                AdDTO editedAd = AdDTO.builder()
                        .author(0)
                        .image("")
                        .pk(0)
                        .price(ad.getPrice())
                        .title(ad.getTitle())
                        .build();

                return ResponseEntity.ok(editedAd);
            } else {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @Operation(tags = "Comments", operationId = "deleteComment", summary = "Deleting a comment")
    @DeleteMapping("/{adId}/comments/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable Integer adId, @PathVariable Integer commentId) {
        if (adId <= 0 || commentId <= 0) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        boolean commentExist = true;

        if (commentExist) {
            Role userRole = Role.ADMIN;
            boolean isAuthorComment = false;
            boolean userHasPermit = isAuthorComment || userRole == Role.ADMIN;

            if (userHasPermit) {
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @Operation(tags = "Comments", operationId = "updateComment", summary = "Updating a comment")
    @PatchMapping("/{adId}/comments/{commentId}")
    public ResponseEntity<CommentDTO> updateComment(
            @PathVariable Integer adId,
            @PathVariable Integer commentId,
            @RequestBody CreateOrUpdateCommentDTO comment
    ) {
        if (adId <= 0 || commentId <= 0) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        boolean commentExist = true;

        if (commentExist) {
            Role userRole = Role.ADMIN;
            boolean isAuthorComment = false;
            boolean userHasPermit = isAuthorComment || userRole == Role.ADMIN;

            if (userHasPermit) {
                CommentDTO editedComment = CommentDTO.builder()
                        .author(0)
                        .authorImage("")
                        .authorFirstName("")
                        .createdAt(Calendar.getInstance().getTimeInMillis())
                        .pk(0)
                        .text(comment.getText())
                        .build();

                return ResponseEntity.ok(editedComment);
            } else {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @Operation(tags = "Ads", operationId = "getAdsMe", summary = "Receiving authenticated user`s ads")
    @GetMapping("/me")
    public ResponseEntity<AdsDTO> getAdsMe() {
        List<AdDTO> usersAds = List.of();

        AdsDTO ads = AdsDTO.builder()
                .count(usersAds.size())
                .results(usersAds)
                .build();

        return ResponseEntity.ok(ads);
    }

    @Operation(tags = "Ads", operationId = "updateImage", summary = "Updating the picture of ad")
    @PatchMapping(value = "/{id}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<byte[]> updateUserImage(
            @PathVariable Integer id,
            @RequestParam MultipartFile image
    ) throws IOException {
        boolean adExist = true;

        if (adExist) {
            Role userRole = Role.ADMIN;
            boolean isAuthorAd = false;
            boolean userHasPermit = isAuthorAd || userRole == Role.ADMIN;

            if (userHasPermit) {
                HttpHeaders headers = new HttpHeaders();

                headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
                headers.setContentLength(image.getSize());

                return ResponseEntity.status(HttpStatus.OK).headers(headers).body(image.getBytes());
            } else {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

}