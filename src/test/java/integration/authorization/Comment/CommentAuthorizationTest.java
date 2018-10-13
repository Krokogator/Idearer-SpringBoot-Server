package integration.authorization.Comment;

import com.krokogator.spring.resources.IdReferenceDTO;
import com.krokogator.spring.resources.comment.dto.PatchCommentDTO;
import com.krokogator.spring.resources.comment.dto.PostCommentDTO;
import integration.authorization.IntegrationTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        CommentAnonymousTest.class,
        CommentUserTest.class,
        CommentAdminTest.class
})
public abstract class CommentAuthorizationTest extends IntegrationTest {

    private final String endpoint = "/comments";

    public ResultActions getCommentsByArticleId() throws Exception {
        return mockMvc.perform(
                get(endpoint).param("articleId", "1"));
    }

    public ResultActions createComment() throws Exception {
        PostCommentDTO postCommentDTO = new PostCommentDTO();
        postCommentDTO.content = "Comment content";
        postCommentDTO.setArticle(new IdReferenceDTO(1L));
        postCommentDTO.setParentComment(null);

        return mockMvc.perform(
                post(endpoint)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(postCommentDTO))
        );

    }

    public ResultActions updateComment(int id) throws Exception {
        PatchCommentDTO patchCommentDTO = new PatchCommentDTO();
        patchCommentDTO.content = "Updated comment content";

        return mockMvc.perform(
                patch(endpoint + "/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(patchCommentDTO))
        );
    }

    public ResultActions deleteComment(int id) throws Exception {
        return mockMvc.perform(
                delete(endpoint + "/" + id)
        );
    }


}
