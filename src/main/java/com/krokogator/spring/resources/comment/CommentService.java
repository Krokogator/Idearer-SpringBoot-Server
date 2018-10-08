package com.krokogator.spring.resources.comment;

import com.krokogator.spring.error.client.ClientErrorException;
import com.krokogator.spring.resources.article.Article;
import com.krokogator.spring.resources.article.ArticleRepository;
import com.krokogator.spring.resources.comment.dto.PatchCommentDTO;
import com.krokogator.spring.resources.comment.dto.PostCommentDTO;
import com.krokogator.spring.resources.user.CurrentUser;
import com.krokogator.spring.resources.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;

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

    public List<Comment> getCommentsByArticleId(Long id) {
        return commentRepository.getCommentsByArticleIdAndParentCommentId(id, null);
    }

    public Comment likeComment(Comment comment) {
        Predicate<User> matcher = p -> p.getId().equals(CurrentUser.getId());

        if(comment.getLikes().stream().anyMatch(matcher)) {
            //Comment already liked by this user, do nothing.
            return comment;
        }

        comment.getLikes().add(new User(CurrentUser.getId()));
        return commentRepository.save(comment);
    }

    public Comment dislikeComment(Comment comment) {
        Predicate<User> matcher = p -> p.getId().equals(CurrentUser.getId());

        if(comment.getLikes().stream().noneMatch(matcher)){
            //Comment already disliked by this user, do nothing.
            return comment;
        }

        comment.getLikes().removeIf(x -> x.getId().equals(CurrentUser.getId()));
        return commentRepository.save(comment);
    }


    public void deleteComment(Long id) throws ClientErrorException {
        if(commentRepository.existsById(id)) {
            commentRepository.deleteById(id);
        } else {
            throw new ClientErrorException(HttpStatus.NOT_FOUND, "Comment '"+id+"' not found.");
        }
    }


    public void updateComment(Long id, PatchCommentDTO dto) throws ClientErrorException {
        //Check if comment exists
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new ClientErrorException(HttpStatus.NOT_FOUND, "Comment '"+id+"' not found."));
        //Update content if provided
        if(dto.content != null) comment.setContent(dto.content);
        //Like or dislike if provided
        if(dto.liked != null) {
            if (dto.liked) comment = likeComment(comment);
            else comment = dislikeComment(comment);
        }

        commentRepository.save(comment);
    }
}
