package com.krokogator.spring.resources.comment;

import com.krokogator.spring.error.client.ClientErrorException;
import com.krokogator.spring.resources.article.Article;
import com.krokogator.spring.resources.comment.dto.PostCommentDTO;
import com.krokogator.spring.resources.comment.projection.RequestBodyComment;
import com.krokogator.spring.resources.comment.validationgroup.PatchCommentValidation;
import com.krokogator.spring.resources.comment.validationgroup.PostCommentValidation;
import com.krokogator.spring.resources.user.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.groups.Default;
import java.util.List;
import java.util.Optional;

@RestController
@Api(tags = "Comments")
@RequestMapping(value = "/comments")
public class CommentController {

    @Autowired
    CommentService commentService;

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 404, message = "Not found")
    })
    public Comment addComment(@RequestBody @Validated({PostCommentValidation.class, Default.class}) PostCommentDTO dto) throws ClientErrorException {
        Comment comment = new Comment();
        comment.setContent(dto.content);
        comment.setArticle(new Article(dto.getArticle().getId()));
        return commentService.addComment(comment);
    }

    @GetMapping
    public List<Comment> getCommentsByArticleId(@RequestParam Long articleId){
        return commentService.getCommentsByArticleId(articleId);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 404, message = "Not found")
    })
    public void deleteComment(@PathVariable Long id) throws ClientErrorException {
        commentService.deleteComment(id);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 404, message = "Not found")
    })
    public void updateComment(@PathVariable Long id, @RequestBody @Validated({PatchCommentValidation.class, Default.class}) PostCommentDTO dto) throws ClientErrorException {
        commentService.updateComment(id, dto);
    }

}
