package integration.authorization.Comment;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
public class CommentAdminTest extends CommentAuthorizationTest{

    @Test
    @WithMockUser(roles = "ADMIN", username = "admin", password = "admin")
    public void givenAdmin_getCommentsByArticleId_thenOk() throws Exception {
        getCommentsByArticleId().andExpect(
                status().isOk()
        );
    }

    @Test
    @WithMockUser(roles = "ADMIN", username = "admin", password = "admin")
    public void givenAdmin_createComment_thenCreated() throws Exception {
        createComment().andExpect(
                status().isCreated()
        );
    }

    @Test
    @WithMockUser(roles = "ADMIN", username = "admin", password = "admin")
    public void givenAdmin_updateOwnedComment_thenNoContent() throws Exception {
        updateComment(1).andExpect(
                status().isNoContent()
        );
    }

    @Test
    @WithMockUser(roles = "ADMIN", username = "admin", password = "admin")
    public void givenAdmin_updateNotOwnedComment_thenNoContent() throws Exception {
        updateComment(2).andExpect(
                status().isNoContent()
        );
    }

    @Test
    @WithMockUser(roles = "ADMIN", username = "admin", password = "admin")
    public void givenAdmin_deleteOwnedComment_thenNoContent() throws Exception {
        deleteComment(1).andExpect(
                status().isNoContent()
        );
    }

    @Test
    @WithMockUser(roles = "ADMIN", username = "admin", password = "admin")
    public void givenAdmin_deleteNotOwnedComment_thenNoContent() throws Exception {
        deleteComment(2).andExpect(
                status().isNoContent()
        );
    }
}