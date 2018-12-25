package integration.authorization.Reject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.context.junit4.SpringRunner;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WithAnonymousUser
@RunWith(SpringRunner.class)
public class ArticleRejectAnonymousAuthorizationTest extends ArticleRejectBaseTest {

    @Test
    public void givenAnonymous_getRejectsByArticleId_thenUnauthorized() throws Exception {
        getRejectsByArticleId(1).andExpect(
                status().isUnauthorized()
        );
    }

    @Test
    public void givenAnonymous_rejectArticle_thenUnauthorized() throws Exception {
        rejectArticle(1).andExpect(
                status().isUnauthorized()
        );
    }
}
