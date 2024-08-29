package ru.skypro.homework.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import ru.skypro.homework.dto.CommentDTO;
import ru.skypro.homework.dto.CreateOrUpdateCommentDTO;
import ru.skypro.homework.model.Comment;

import java.util.List;

public interface CommentService {

    List<Comment> getAllCommentsByAdId(Integer id);

    Comment addCommentToAdById(Integer id, String username, CreateOrUpdateCommentDTO commentDTO);

    HttpStatus deleteAdCommentById(Integer adId, Integer commentId, Authentication authentication);

    ResponseEntity<CommentDTO> updateAdCommentById(
            Integer adId,
            Integer commentId,
            CreateOrUpdateCommentDTO commentDTO,
            Authentication authentication
    );

}
