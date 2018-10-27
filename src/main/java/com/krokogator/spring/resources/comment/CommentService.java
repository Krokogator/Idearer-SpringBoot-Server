package com.krokogator.spring.resources.comment;

import com.krokogator.spring.error.client.ClientErrorException;
import com.krokogator.spring.resources.article.Article;
import com.krokogator.spring.resources.article.ArticleRepository;
import com.krokogator.spring.resources.comment.dto.PatchCommentDTO;
import com.krokogator.spring.resources.comment.dto.PostCommentDTO;
import com.krokogator.spring.resources.comment.projection.CommentWithoutChildrenProjection;
import com.krokogator.spring.resources.user.CurrentUser;
import com.krokogator.spring.resources.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import static org.springframework.data.domain.PageRequest.of;

@Service
public class CommentService {

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    ArticleRepository articleRepository;

    public Comment addComment(PostCommentDTO dto) throws ClientErrorException {
        Comment comment = new Comment(
                dto.content,
                new Article(dto.getArticle().getId()),
                //Set parent comment if exists
                (dto.getParentComment() == null) ? null : new Comment(dto.getParentComment().getId()),
                new User(CurrentUser.getId())
        );

        //Verify if article exists
        Long articleId = dto.getArticle().getId();
        articleRepository.findById(articleId).orElseThrow(() -> new ClientErrorException(HttpStatus.NOT_FOUND, "Article '"+articleId+"' not found."));

        return commentRepository.save(comment);
    }

    public List<Comment> getComments(Long articleId, Long userId, Long parentCommentId, Integer pageIndex, Integer pageSize, Boolean hideSubcomments) {


//        if (articleId == null && userId == null) return null;
//        //Get comments with subcomments
//        if (userId == null) return commentRepository.getCommentsByArticleIdAndParentCommentId(articleId, null);
//        if (articleId == null) return commentRepository.findAllByUserId(userId).stream()
//                .map(this::getCommentWithoutSubComments).collect(Collectors.toList());
        pageIndex = (pageIndex == null) ? 1 : pageIndex;
        pageSize = (pageSize == null) ? 1000 : pageSize;
        hideSubcomments = (hideSubcomments == null) ? false : hideSubcomments;
        Pageable page = of(pageIndex, pageSize);

        List<Comment> comments = commentRepository.getCommentsByAdvancedQuery(userId, articleId, parentCommentId, page);
        if (hideSubcomments) {
            for (Comment c : comments) {
                c.setComments(null);
            }
        }
        return comments;
//        return commentRepository.findAllByUserIdAndAndArticleId(userId, articleId).stream()
//                .map(this::getCommentWithoutSubComments).collect(Collectors.toList());
    }

    private Comment getCommentWithoutSubComments(CommentWithoutChildrenProjection x) {
        Comment comment = new Comment(x.getId());
        comment.setUser(x.getUser());
        comment.setContent(x.getContent());
        comment.setCreated(x.getCreated());
        comment.setLikesCount(x.getLikesCount());
        comment.setComments(new ArrayList<>());
        return comment;
    }


    public Comment likeComment(Comment comment) throws ClientErrorException {
        Predicate<User> matcher = p -> p.getId().equals(CurrentUser.getId());

        if(comment.getLikes().stream().anyMatch(matcher)) {
            throw new ClientErrorException(HttpStatus.CONFLICT, "Comment " + comment.getId() + " already liked.");
        }

        comment.getLikes().add(new User(CurrentUser.getId()));
        return commentRepository.save(comment);
    }

    public Comment dislikeComment(Comment comment) throws ClientErrorException {
        Predicate<User> matcher = p -> p.getId().equals(CurrentUser.getId());

        if(comment.getLikes().stream().noneMatch(matcher)){
            throw new ClientErrorException(HttpStatus.CONFLICT, "Comment " + comment.getId() + " already disliked.");
        }

        comment.getLikes().removeIf(x -> x.getId().equals(CurrentUser.getId()));
        return commentRepository.save(comment);
    }

    @PostAuthorize("returnObject.user.id == @CurrentUser.id OR hasRole('ADMIN')")
    public Comment deleteComment(Long id) throws ClientErrorException {
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new ClientErrorException(HttpStatus.NOT_FOUND, "Comment '" + id + "' not found."));
        commentRepository.delete(comment);
        return comment;
    }

    @PostAuthorize("returnObject.user.id == @CurrentUser.id OR hasRole('ADMIN')")
    public Comment updateComment(PatchCommentDTO dto, Long id) throws ClientErrorException {
        //Check if comment exists
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new ClientErrorException(HttpStatus.NOT_FOUND, "Comment '"+id+"' not found."));
        //Update content if provided
        if(dto.content != null) comment.setContent(dto.content);

        return commentRepository.save(comment);
    }

    @PreAuthorize("hasRole('USER')")
    public void likeOrDislikeComment(PatchCommentDTO dto, Long id) throws ClientErrorException {
        //Check if comment exists
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new ClientErrorException(HttpStatus.NOT_FOUND, "Comment '" + id + "' not found."));

        //Like or dislike if provided
        if (dto.liked != null) {
            comment = (dto.liked) ? likeComment(comment) : dislikeComment(comment);
        }

        commentRepository.save(comment);
    }
}
