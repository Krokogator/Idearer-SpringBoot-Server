package integration.authorization.Reject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WithMockUser(roles = "ADMIN", username = "admin", password = "admin")
@RunWith(SpringRunner.class)
public class ArticleRejectAdminAuthorizationTest extends ArticleRejectBaseTest {

    @Test
    public void givenAdmin_getRejectsByArticleId_thenOk() throws Exception {
        getRejectsByArticleId(1).andExpect(
                status().isOk()
        );
    }

    @Test
    public void givenAdmin_rejectArticle_thenCreated() throws Exception {
        rejectArticle(1).andExpect(
                status().isCreated()
        );
    }
}
