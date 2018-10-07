package com.krokogator.spring.resources.comment;

import com.krokogator.spring.error.client.ClientErrorException;
import com.krokogator.spring.resources.article.Article;
import com.krokogator.spring.resources.article.ArticleRepository;
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

    public void likeArticle(Long id) {
        Comment comment = commentRepository.getById(id);
        comment.getLikes().add(new User(CurrentUser.getId()));
        commentRepository.save(comment);
    }

    public void dislikeArticle(Long id) {
        Comment comment = commentRepository.getById(id);
        comment.getLikes().removeIf(x -> x.getId().equals(CurrentUser.getId()));
        commentRepository.save(comment);
    }


    public void deleteComment(Long id) throws ClientErrorException {
        if(commentRepository.existsById(id)) {
            commentRepository.deleteById(id);
        } else {
            throw new ClientErrorException(HttpStatus.NOT_FOUND, "Comment '"+id+"' not found.");
        }
    }

    public Comment updateComment(Long id, Comment dto) throws ClientErrorException {
        //Check if comment exists
        Comment commentDB = commentRepository.findById(id).orElseThrow(() -> new ClientErrorException(HttpStatus.NOT_FOUND, "Comment '"+id+"' not found."));
        //Update content if provided
        if(dto.getContent() != null) commentDB.setContent(dto.getContent());

        return commentRepository.save(commentDB);
    }
}
