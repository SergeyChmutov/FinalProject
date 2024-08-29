package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.skypro.homework.dto.CommentDTO;
import ru.skypro.homework.dto.CreateOrUpdateCommentDTO;
import ru.skypro.homework.enums.Role;
import ru.skypro.homework.exception.AdsCommentNotFoundException;
import ru.skypro.homework.mapper.CommentMapper;
import ru.skypro.homework.model.Ad;
import ru.skypro.homework.model.Comment;
import ru.skypro.homework.model.User;
import ru.skypro.homework.repository.CommentRepository;
import ru.skypro.homework.service.AdsService;
import ru.skypro.homework.service.CommentService;
import ru.skypro.homework.service.UserService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final AdsService adsService;
    private final UserService userService;
    private final CommentMapper commentMapper;

    @Override
    public List<Comment> getAllCommentsByAdId(Integer id) {
        Ad ad = adsService.getAdById(id);

        return commentRepository.findAllByAd(ad);
    }

    @Override
    @Transactional
    public Comment addCommentToAdById(Integer id, String username, CreateOrUpdateCommentDTO commentDTO) {
        Ad ad = adsService.getAdById(id);
        User user = userService.findUserByEmail(username);

        Comment newComment = commentMapper.createOrUpdateCommentDTOToComment(commentDTO);
        newComment.setUser(user);
        newComment.setAd(ad);

        return commentRepository.save(newComment);
    }

    @Override
    @Transactional
    public HttpStatus deleteAdCommentById(Integer adId, Integer commentId,Authentication authentication) {
        Comment foundComment = getCommentById(commentId);
        Ad ad = foundComment.getAd();

        if (!ad.getPk().equals(adId)) {
            return HttpStatus.NOT_FOUND;
        }

        if (userHasAdminRoleOrIsAuthorComment(foundComment, authentication)) {
            commentRepository.delete(commentId);
            return HttpStatus.OK;
        } else {
            return HttpStatus.FORBIDDEN;
        }
    }

    @Override
    @Transactional
    public ResponseEntity<CommentDTO> updateAdCommentById(
            Integer adId,
            Integer commentId,
            CreateOrUpdateCommentDTO commentDTO,
            Authentication authentication
    ) {
        Comment foundComment = getCommentById(commentId);
        Ad ad = foundComment.getAd();

        if (!ad.getPk().equals(adId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        if (userHasAdminRoleOrIsAuthorComment(foundComment, authentication)) {
            Comment updateComment = commentMapper.createOrUpdateCommentDTOToComment(commentDTO);

            foundComment.setText(updateComment.getText());
            foundComment.setUser(userService.findUserByEmail(authentication.getName().toLowerCase()));
            foundComment.setCreatedAt(updateComment.getCreatedAt());

            Comment savedComment = commentRepository.save(foundComment);

            return ResponseEntity.ok(commentMapper.commentToCommentDTO(savedComment));
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    private Comment getCommentById(Integer id) {
        return commentRepository.findByPk(id)
                .orElseThrow(() -> new AdsCommentNotFoundException("Comment with id " + id + " not found."));
    }

    private boolean userHasAdminRoleOrIsAuthorComment(Comment comment, Authentication authentication) {
        SimpleGrantedAuthority adminGrantedAuthority = new SimpleGrantedAuthority(Role.ADMIN.name());
        boolean userHasPermit = authentication.getAuthorities()
                .contains(adminGrantedAuthority);

        if (!userHasPermit) {
            userHasPermit = comment.getUser()
                    .getEmail().equals(authentication.getName().toLowerCase());
        }

        return userHasPermit;
    }

}
