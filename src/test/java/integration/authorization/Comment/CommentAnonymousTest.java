package integration.authorization.Comment;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.context.junit4.SpringRunner;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
public class CommentAnonymousTest extends CommentAuthorizationTest{

    @Test
    @WithAnonymousUser
    public void givenAnonymous_getCommentsByArticleId_thenOk() throws Exception {
        getCommentsByArticleId().andExpect(
                status().isOk()
        );
    }

    @Test
    @WithAnonymousUser
    public void givenAnonymous_createComment_thenUnauthorized() throws Exception {
        createComment().andExpect(
                status().isUnauthorized()
        );
    }

    @Test
    @WithAnonymousUser
    public void givenAnonymous_updateComment_thenUnauthorized() throws Exception {
        updateComment(1).andExpect(
                status().isUnauthorized()
        );
    }

    @Test
    @WithAnonymousUser
    public void givenAnonymous_deleteComment_thenUnauthorized() throws Exception {
        deleteComment(1).andExpect(
                status().isUnauthorized()
        );
    }
}
