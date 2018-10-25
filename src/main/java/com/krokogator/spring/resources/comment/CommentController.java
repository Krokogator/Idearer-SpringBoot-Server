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

import javax.validation.constraints.Min;
import java.util.List;

@RestController
@Api(tags = "Comments")
@RequestMapping(value = "/comments")
@Validated
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
    public List<Comment> getComments(
            @RequestParam(required = false) @Min(0) Long articleId,
            @RequestParam(required = false) @Min(0) Long userId,
            @RequestParam(required = false) @Min(0) Long parentCommentId,
            @RequestParam(required = false, defaultValue = "1") @Min(1) Integer pageIndex,
            @RequestParam(required = false) @Min(0) Integer pageSize,
            @RequestParam(required = false) Boolean hideSubcomments
    ) {
        return commentService.getComments(articleId, userId, parentCommentId, pageIndex, pageSize, hideSubcomments);
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
