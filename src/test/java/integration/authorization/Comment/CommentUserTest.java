package integration.authorization.Comment;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
public class CommentUserTest extends CommentAuthorizationTest{

    @Test
    @WithMockUser(roles = "USER", username = "user", password = "user")
    public void givenUser_getCommentsByArticleId_thenOk() throws Exception {
        getCommentsByArticleId().andExpect(
                status().isOk()
        );
    }

    @Test
    @WithMockUser(roles = "USER", username = "user", password = "user")
    public void givenUser_createComment_thenCreated() throws Exception {
        createComment().andExpect(
                status().isCreated()
        );
    }

    @Test
    @WithMockUser(roles = "USER", username = "user", password = "user")
    public void givenUser_updateOwnedComment_thenNoContent() throws Exception {
        updateComment(2).andExpect(
                status().isNoContent()
        );
    }

    @Test
    @WithMockUser(roles = "USER", username = "user", password = "user")
    public void givenUser_updateNotOwnedComment_thenForbidden() throws Exception {
        updateComment(1).andExpect(
                status().isForbidden()
        );
    }

    @Test
    @WithMockUser(roles = "USER", username = "user", password = "user")
    public void givenUser_deleteOwnedComment_thenNoContent() throws Exception {
        deleteComment(2).andExpect(
                status().isNoContent()
        );
    }

    @Test
    @WithMockUser(roles = "USER", username = "user", password = "user")
    public void givenUser_deleteNotOwnedComment_thenForbidden() throws Exception {
        deleteComment(1).andExpect(
                status().isForbidden()
        );
    }
}