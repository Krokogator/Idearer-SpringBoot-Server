package integration.authorization.Reject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WithMockUser(roles = "USER", username = "user", password = "user")
@RunWith(SpringRunner.class)
public class ArticleRejectUserAuthorizationTest extends ArticleRejectBaseTest {

    @Test
    public void givenUser_getRejectsByArticleId_thenForbidden() throws Exception {
        getRejectsByArticleId(1).andExpect(
                status().isForbidden()
        );
    }

    @Test
    public void givenUser_rejectArticle_thenForbidden() throws Exception {
        rejectArticle(1).andExpect(
                status().isForbidden()
        );
    }
}
