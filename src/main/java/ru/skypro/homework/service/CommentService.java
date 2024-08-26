package ru.skypro.homework.service;

import ru.skypro.homework.dto.CreateOrUpdateCommentDTO;
import ru.skypro.homework.model.Comment;

import java.util.List;

public interface CommentService {

    List<Comment> getAllCommentsByAdId(Integer id);

    Comment addCommentToAdByItsId(Integer id, String username, CreateOrUpdateCommentDTO commentDTO);

}
