package com.krokogator.spring.resources.comment;

import com.krokogator.spring.resources.article.Article;
import com.krokogator.spring.resources.user.CurrentUser;
import com.krokogator.spring.resources.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    @Autowired
    CommentRepository commentRepository;

    public Comment addComment(Comment comment, Long articleId, Long commentId) {
        Comment commentDB = new Comment(comment.getContent(), new Article(articleId), (commentId == null) ? null : new Comment(commentId), new User(CurrentUser.getName()));
        return commentRepository.save(commentDB);
    }

    public List<Comment> getCommentsByArticleId(Long id) {
        return commentRepository.getCommentsByArticleIdAndParentCommentId(id, null);
    }

    public void likeArticle(Long id) {
        Comment comment = commentRepository.getById(id);
        comment.getLikes().add(new User(CurrentUser.getName()));
        commentRepository.save(comment);
    }

    public void dislikeArticle(Long id) {
        Comment comment = commentRepository.getById(id);
        comment.getLikes().removeIf(x -> x.getUsername().equals(CurrentUser.getName()));
        commentRepository.save(comment);
    }


    public void deleteComment(Long id) {
        commentRepository.deleteById(id);
    }

    public Comment updateComment(Long id, Comment comment) {
        Comment commentDB = commentRepository.getById(id);
        if(comment.getContent() != null) commentDB.setContent(comment.getContent());
        return commentRepository.save(commentDB);
    }
}
