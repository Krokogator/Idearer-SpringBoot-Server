package com.krokogator.spring.resources.comment;

import com.krokogator.spring.resources.comment.projection.RequestBodyComment;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api(tags = "Comments")
@RequestMapping(value = "/comments")
public class CommentController {

    @Autowired
    CommentService commentService;

    @PostMapping
    public Comment addComment(
            @RequestParam(value = "articleId") Long articleId,
            @RequestParam(value = "commentId", required = false) Long commentId,
            @RequestBody Comment comment){
        return commentService.addComment(comment, articleId, commentId);
    }

    @GetMapping
    public List<Comment> getCommentsByArticleId(@RequestParam Long articleId){
        return commentService.getCommentsByArticleId(articleId);
    }

    @PatchMapping(value = "/{id}/like")
    public void likeComment(@PathVariable Long id){
        commentService.likeArticle(id);
    }

    @PatchMapping(value = "/{id}/dislike")
    public void dislikeComment(@PathVariable Long id) { commentService.dislikeArticle(id); }

    @DeleteMapping("/{id}")
    public void deleteComment(@PathVariable Long id){
        commentService.deleteComment(id);
    }

    @PatchMapping("/{id}")
    public Comment updateComment(@PathVariable Long id, @RequestBody RequestBodyComment comment){
        Comment commentDB = new Comment();
        commentDB.setContent(comment.content);
        return commentService.updateComment(id, commentDB);
    }

}
