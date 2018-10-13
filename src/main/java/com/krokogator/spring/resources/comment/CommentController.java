package com.krokogator.spring.resources.comment;

import com.krokogator.spring.error.client.ClientErrorException;
import com.krokogator.spring.resources.comment.dto.PatchCommentDTO;
import com.krokogator.spring.resources.comment.dto.PostCommentDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public Comment addComment(@RequestBody @Validated PostCommentDTO dto) throws ClientErrorException {
        return commentService.addComment(dto);
    }

    @GetMapping
    public List<Comment> getCommentsByArticleId(@RequestParam Long articleId){
        return commentService.getCommentsByArticleId(articleId);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('USER')")
    @ApiResponses(value = {
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 404, message = "Not found")
    })
    public void deleteComment(@PathVariable Long id) throws ClientErrorException {
        commentService.deleteComment(id);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('USER')")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 404, message = "Not found")
    })
    public void updateComment(@PathVariable Long id, @RequestBody @Validated PatchCommentDTO dto) throws ClientErrorException {
        commentService.updateComment(id, dto);
    }

}
