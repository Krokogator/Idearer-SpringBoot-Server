package com.krokogator.spring.resources.comment;

import com.krokogator.spring.error.client.ClientErrorException;
import com.krokogator.spring.resources.article.Article;
import com.krokogator.spring.resources.article.ArticleRepository;
import com.krokogator.spring.resources.comment.dto.PostCommentDTO;
import com.krokogator.spring.resources.user.CurrentUser;
import com.krokogator.spring.resources.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentService {

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    ArticleRepository articleRepository;

    public Comment addComment(Comment comment) throws ClientErrorException {
        //Verify if article exists
        Long articleId = comment.getArticle().getId();
        articleRepository.findById(articleId).orElseThrow(() -> new ClientErrorException(HttpStatus.NOT_FOUND, "Article '"+articleId+"' not found."));

        //Set logged in User as comment author
        comment.setUser(new User(CurrentUser.getId()));

        return commentRepository.save(comment);
    }

    public List<Comment> getCommentsByArticleId(Long id) {
        return commentRepository.getCommentsByArticleIdAndParentCommentId(id, null);
    }

    public Comment likeComment(Comment comment) {
        comment.getLikes().add(new User(CurrentUser.getId()));
        return commentRepository.save(comment);
    }

    public Comment dislikeComment(Comment comment) {
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


    public void updateComment(Long id, PostCommentDTO dto) throws ClientErrorException {
        //Check if comment exists
        Comment commentDB = commentRepository.findById(id).orElseThrow(() -> new ClientErrorException(HttpStatus.NOT_FOUND, "Comment '"+id+"' not found."));
        //Update content if provided
        if(dto.content != null) commentDB.setContent(dto.content);

        if(dto.liked != null) {
            if (dto.liked) commentDB = likeComment(commentDB);
            else commentDB = dislikeComment(commentDB);
        }

        commentRepository.save(commentDB);
    }
}
